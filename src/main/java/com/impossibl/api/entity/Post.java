package com.impossibl.api.entity;

import com.impossibl.api.enums.PostStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false, unique = true, updatable = false)
	private String slug;

	@Column(columnDefinition = "text")
	private String excerpt;

	@Column(columnDefinition = "text")
	private String body;

	private String author;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PostStatus status;

	@Column(nullable = false)
	private boolean isFeatured;

	@Column(nullable = false)
	private long viewCount;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "post_tags",
			joinColumns = @JoinColumn(name = "post_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id")
	)
	@Builder.Default
	private Set<Tag> tags = new HashSet<>();

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	@UpdateTimestamp
	@Column(nullable = false)
	private Instant updatedAt;

	private String createdBy;
	private String updatedBy;

	private Instant publishedAt;
}