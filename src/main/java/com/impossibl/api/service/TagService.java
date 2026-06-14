package com.impossibl.api.service;

import com.impossibl.api.entity.Tag;
import com.impossibl.api.enums.PostStatus;
import com.impossibl.api.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

	private final TagRepository tagRepository;

	public List<String> findPublishedTagNames() {
		return tagRepository.findNamesByPostStatus(PostStatus.PUBLISHED);
	}

	public List<String> findAllTagNames() {
		return tagRepository.findAll()
				.stream()
				.map(Tag::getName)
				.sorted()
				.toList();
	}
}