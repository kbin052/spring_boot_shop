package com.apple.shop.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findPageBy(PageRequest page);

    List<Comment> findAllByParentId(Long parentId);
}