package com.sepideh.notebook.module.content.repository;

import com.sepideh.notebook.module.content.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> { }
