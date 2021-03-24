package com.sepideh.notebook.service;

import com.sepideh.notebook.dto.category.CategoryDto;
import com.sepideh.notebook.mapper.CategoryMapper;
import com.sepideh.notebook.model.Category;
import com.sepideh.notebook.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    // Constructor *****************************************************************************************************
    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    //******************************************************************************************************************
    public CategoryDto createCategory(CategoryDto categoryDto) {
        categoryDto.setId(null);
        Category category = categoryRepository.save(
            categoryMapper.toModel(categoryDto)
        );

        return categoryMapper.toDto(category);
    }

    //******************************************************************************************************************
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = findById(categoryDto.getId());

        categoryRepository.save(
            categoryMapper.update(categoryDto, category)
        );

        return categoryMapper.toDto(category);
    }

    //******************************************************************************************************************
    public Category findById(long id) throws DataAccessException {
        return categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    //******************************************************************************************************************
    public List<CategoryDto> getAllCategory() {
        return categoryMapper.toDto(categoryRepository.findAll());
    }

    //******************************************************************************************************************
    public boolean deleteCategory(long id) throws DataAccessException {
        Category category = findById(id);
        categoryRepository.delete(category);

        return true;
    }

}
