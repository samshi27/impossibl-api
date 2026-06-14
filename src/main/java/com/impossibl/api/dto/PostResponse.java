package com.impossibl.api.dto;

import com.impossibl.api.entity.Post;
import com.impossibl.api.enums.PostStatus;

import java.time.Instant;
import java.util.List;

public record PostResponse(
		Long id,
		String title,
		String slug,
		String excerpt,
		String body,
		String author,
		PostStatus status,
		boolean isFeatured,
		long viewCount,
		List<String> tags,
		Instant publishedAt
) {
	public static PostResponse from(Post post) {
		return new PostResponse(
				post.getId(),
				post.getTitle(),
				post.getSlug(),
				post.getExcerpt(),
				post.getBody(),
				post.getAuthor(),
				post.getStatus(),
				post.isFeatured(),
				post.getViewCount(),
				post.getTags().stream().map(tag -> tag.getName()).toList(),
				post.getPublishedAt()
		);
	}
}