package com.impossibl.api.service;

import com.impossibl.api.dto.PostRequest;
import com.impossibl.api.dto.PostResponse;
import com.impossibl.api.entity.Post;
import com.impossibl.api.entity.Tag;
import com.impossibl.api.enums.PostStatus;
import com.impossibl.api.exception.DuplicateSlugException;
import com.impossibl.api.exception.IncompletePublishException;
import com.impossibl.api.exception.PostNotFoundException;
import com.impossibl.api.repository.PostRepository;
import com.impossibl.api.repository.TagRepository;
import com.impossibl.api.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
	private final PostRepository postRepository;
	private final TagRepository tagRepository;

	public List<PostResponse> findPublished() {
		return postRepository.findByStatus(PostStatus.PUBLISHED)
				.stream()
				.map(PostResponse::from)
				.toList();
	}

	public List<PostResponse> findAll() {
		return postRepository.findAll().stream().map(PostResponse::from).toList();
	}

	public PostResponse findPublishedBySlug(String slug) {
		Post post = postRepository.findBySlugAndStatus(slug, PostStatus.PUBLISHED)
				.orElseThrow(() -> new PostNotFoundException(slug));
		return PostResponse.from(post);
	}

	public PostResponse findAnyBySlug(String slug) {
		Post post = postRepository.findBySlug(slug).orElseThrow(() -> new PostNotFoundException(slug));
		return PostResponse.from(post);
	}

	@Transactional
	public PostResponse create(PostRequest request) {
		validatePublishable(request);

		String slug = SlugUtil.slugify(request.title());
		if (slug.isEmpty()) {
			throw new IncompletePublishException("Title must contain at least one letter or number");
		}
		if (postRepository.existsBySlug(slug)) {
			throw new DuplicateSlugException(slug);
		}

		Post post = new Post();
		post.setTitle(request.title());
		post.setSlug(slug);
		post.setExcerpt(request.excerpt());
		post.setBody(request.body());
		post.setAuthor(request.author());
		post.setStatus(request.status());
		post.setTags(resolveTags(request.tags()));
		post.setFeatured(false);
		post.setViewCount(0);
		post.setPublishedAt(request.status() == PostStatus.PUBLISHED ? Instant.now() : null);
		boolean featured = resolveFeatured(request.isFeatured(), request.status(), null);
		post.setFeatured(featured);

		Post saved = postRepository.save(post);
		return PostResponse.from(saved);
	}

	@Transactional
	public PostResponse update(Long id, PostRequest request) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

		post.setTitle(request.title());
		post.setExcerpt(request.excerpt());
		post.setBody(request.body());
		post.setAuthor(request.author());
		post.setStatus(request.status());
		post.setTags(resolveTags(request.tags()));

		if (post.getPublishedAt() == null && request.status() == PostStatus.PUBLISHED) {
			post.setPublishedAt(Instant.now());
		}

		post.setFeatured(resolveFeatured(request.isFeatured(), request.status(), post.getId()));

		Post saved = postRepository.save(post);
		return PostResponse.from(saved);
	}

	@Transactional
	public void delete(Long id) {
		if (!postRepository.existsById(id)) {
			throw new PostNotFoundException(id);
		}
		postRepository.deleteById(id);
	}


	private Set<Tag> resolveTags(Set<String> tagNames) {
		Set<Tag> tags = new HashSet<>();
		if (tagNames == null) return tags;
		for (String rawName : tagNames) {
			String name = rawName.trim().toLowerCase();
			if (name.isEmpty()) continue;
			Tag tag = tagRepository.findByName(name)
					.orElseGet(() -> tagRepository.save(new Tag(null, name)));
			tags.add(tag);
		}
		return tags;
	}

	private void validatePublishable(PostRequest request) {
		if (request.status() != PostStatus.PUBLISHED) return;

		List<String> missing = new ArrayList<>();
		if (isBlank(request.excerpt())) missing.add("excerpt");
		if (isBlank(request.body())) missing.add("body");
		if (isBlank(request.author())) missing.add("author");

		if (!missing.isEmpty()) {
			throw new IncompletePublishException(
					"Cannot publish: missing required fields - " + String.join(", ", missing));
		}
	}

	private boolean isBlank(String s) {
		return s == null || s.trim().isEmpty();
	}

	public List<PostResponse> findByTag(String tagName) {
		String normalized = tagName.trim().toLowerCase();
		return postRepository.findByTags_NameAndStatus(normalized, PostStatus.PUBLISHED)
				.stream()
				.map(PostResponse::from)
				.toList();
	}

	private boolean resolveFeatured(boolean requestedFeatured, PostStatus status, Long currentPostId) {
		// only published posts can be featured
		if (requestedFeatured && status != PostStatus.PUBLISHED) {
			return false; // silently un-feature if not published
		}

		// if featuring this one, clear the flag on all others
		if (requestedFeatured) {
			List<Post> currentlyFeatured = postRepository.findByIsFeaturedTrue();
			for (Post p : currentlyFeatured) {
				if (!p.getId().equals(currentPostId)) {
					p.setFeatured(false);
					postRepository.save(p);
				}
			}
		}

		return requestedFeatured;
	}
}
