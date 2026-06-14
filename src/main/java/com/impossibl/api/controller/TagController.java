package com.impossibl.api.controller;

import com.impossibl.api.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

	private final TagService tagService;

	@GetMapping
	public List<String> getPublishedTags() {
		return tagService.findPublishedTagNames();
	}
}