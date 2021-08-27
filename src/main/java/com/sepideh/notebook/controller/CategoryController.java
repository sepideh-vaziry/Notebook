package com.sepideh.notebook.controller;

import com.sepideh.notebook.dto.category.CategoryDto;
import com.sepideh.notebook.dto.response.GenericRestResponse;
import com.sepideh.notebook.model.Category;
import com.sepideh.notebook.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    private final CategoryService categoryService;

    // Constructor *****************************************************************************************************
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public GenericRestResponse<CategoryDto> createCategory(
        @RequestBody @Valid CategoryDto category
    ) {
        return new GenericRestResponse<>(
            categoryService.createCategory(category),
            "Create successfully",
            HttpStatus.CREATED.value()
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"", "/"}, method = RequestMethod.PUT)
    public GenericRestResponse<CategoryDto> updateCategory(
        @RequestBody @Valid CategoryDto category
    ) {
        return new GenericRestResponse<>(
            categoryService.updateCategory(category),
            "Update successfully",
            HttpStatus.OK.value()
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public GenericRestResponse<Category> getCategory(
        @PathVariable("id") long id
    ) {
        return new GenericRestResponse<>(
            categoryService.findById(id),
            null,
            HttpStatus.OK.value()
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public GenericRestResponse<List<CategoryDto>> getAllCategory() {
        return new GenericRestResponse<>(
            categoryService.getAllCategory(),
            null,
            HttpStatus.OK.value()
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public GenericRestResponse<Boolean> deleteCategory(@PathVariable("id") long id) {
        return new GenericRestResponse<>(
            categoryService.deleteCategory(id),
            "Delete successfully",
            HttpStatus.OK.value()
        );
    }

}
