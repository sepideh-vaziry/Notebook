package com.sepideh.notebook.controller;

import com.sepideh.notebook.dto.category.CategoryDto;
import com.sepideh.notebook.dto.response.GenericRestResponse;
import com.sepideh.notebook.model.Category;
import com.sepideh.notebook.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<GenericRestResponse<CategoryDto>> createCategory(
        @RequestBody @Valid CategoryDto category
    ) {
        return new ResponseEntity<>(
            new GenericRestResponse<>(
                categoryService.createCategory(category),
                "Create successfully",
                HttpStatus.CREATED.value()
            ),
            HttpStatus.CREATED
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"", "/"}, method = RequestMethod.PUT)
    public ResponseEntity<GenericRestResponse<CategoryDto>> updateCategory(
        @RequestBody @Valid CategoryDto category
    ) {
        return new ResponseEntity<>(
            new GenericRestResponse<>(
                categoryService.updateCategory(category),
                "Update successfully",
                HttpStatus.OK.value()
            ),
            HttpStatus.OK
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<GenericRestResponse<Category>> getCategory(
        @PathVariable("id") long id
    ) {
        return new ResponseEntity<>(
            new GenericRestResponse<>(
                categoryService.findById(id),
                null,
                HttpStatus.OK.value()
            ),
            HttpStatus.OK
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ResponseEntity<GenericRestResponse<List<CategoryDto>>> getAllCategory() {
        return new ResponseEntity<>(
            new GenericRestResponse<>(
                categoryService.getAllCategory(),
                null,
                HttpStatus.OK.value()
            )
            , HttpStatus.OK
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<GenericRestResponse<Boolean>> deleteCategory(@PathVariable("id") long id) {
        return new ResponseEntity<>(
            new GenericRestResponse<>(
                categoryService.deleteCategory(id),
                "Delete successfully",
                HttpStatus.OK.value()
            ),
            HttpStatus.OK
        );
    }

}
