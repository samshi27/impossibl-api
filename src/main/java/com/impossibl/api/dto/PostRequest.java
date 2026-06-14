package com.impossibl.api.dto;

import com.impossibl.api.enums.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record PostRequest(
		@NotBlank(message = "Title is required")
		@Size(max = 200, message = "Title must be at most 200 characters")
		String title,

		@Size(max = 300, message = "Excerpt must be at most 300 characters")
		String excerpt,

		@Size(max = 50000, message = "Body is too long")
		String body,

		@Size(max = 100, message = "Author name must be at most 100 characters")
		String author,

		@NotNull(message = "Status is required")
		PostStatus status,

		@Size(max = 10, message = "A post can have at most 10 tags")
		Set<@Size(max = 30, message = "Each tag must be at most 30 characters") String> tags
) {
}