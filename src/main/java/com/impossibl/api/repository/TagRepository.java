package com.impossibl.api.repository;

import com.impossibl.api.entity.Tag;
import com.impossibl.api.enums.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
	Optional<Tag> findByName(String name);
	
	@Query("SELECT DISTINCT t.name FROM Post p JOIN p.tags t WHERE p.status = :status ORDER BY t.name")
	List<String> findNamesByPostStatus(@Param("status") PostStatus status);
}
