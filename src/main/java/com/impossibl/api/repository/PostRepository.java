package com.impossibl.api.repository;

import com.impossibl.api.entity.Post;
import com.impossibl.api.enums.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
	Optional<Post> findBySlug(String slug);

	List<Post> findByStatus(PostStatus status);

	Optional<Post> findBySlugAndStatus(String slug, PostStatus status);

	boolean existsBySlug(String slug);

	List<Post> findByTags_NameAndStatus(String name, PostStatus status);

	List<Post> findByIsFeaturedTrue();
}
