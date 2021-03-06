package com.sepideh.notebook.module.content.repository;

import com.sepideh.notebook.module.content.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> { }
