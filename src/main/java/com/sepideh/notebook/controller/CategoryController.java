package com.sepideh.notebook.controller;

import com.sepideh.notebook.model.Category;
import com.sepideh.notebook.service.CategoryService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    // Constructor *****************************************************************************************************
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }

}
