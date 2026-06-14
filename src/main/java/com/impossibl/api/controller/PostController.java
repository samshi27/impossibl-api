package com.impossibl.api.controller;

import com.impossibl.api.dto.PostResponse;
import com.impossibl.api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;

	@GetMapping
	public List<PostResponse> getPublished() {
		return postService.findPublished();
	}

	@GetMapping("/{slug}")
	public PostResponse getBySlug(@PathVariable String slug) {
		return postService.findPublishedBySlug(slug);
	}

	@GetMapping("/tag/{name}")
	public List<PostResponse> getByTag(@PathVariable String name) {
		return postService.findByTag(name);
	}
}