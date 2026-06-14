package com.impossibl.api.controller;

import com.impossibl.api.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/tags")
@RequiredArgsConstructor
public class TagAdminController {

	private final TagService tagService;

	@GetMapping
	public List<String> getAllTags() {
		return tagService.findAllTagNames();
	}
}