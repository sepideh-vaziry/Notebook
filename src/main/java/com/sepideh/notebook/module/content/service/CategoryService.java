package com.sepideh.notebook.module.content.service;

import com.sepideh.notebook.module.content.model.Category;
import com.sepideh.notebook.module.content.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // Constructor *****************************************************************************************************
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //******************************************************************************************************************
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    //******************************************************************************************************************
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

}
