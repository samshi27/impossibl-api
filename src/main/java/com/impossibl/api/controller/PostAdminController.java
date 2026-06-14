package com.impossibl.api.controller;

import com.impossibl.api.dto.PostRequest;
import com.impossibl.api.dto.PostResponse;
import com.impossibl.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/posts")
@RequiredArgsConstructor
public class PostAdminController {
	private final PostService postService;

	@GetMapping
	public List<PostResponse> getAll() {
		return postService.findAll();
	}

	@GetMapping("/{slug}")
	public PostResponse getBySlug(@PathVariable String slug) {
		return postService.findAnyBySlug(slug);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PostResponse create(@Valid @RequestBody PostRequest request) {
		return postService.create(request);
	}

	@PutMapping("/{id}")
	public PostResponse update(@PathVariable Long id, @Valid @RequestBody PostRequest request) {
		return postService.update(id, request);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		postService.delete(id);
	}
}