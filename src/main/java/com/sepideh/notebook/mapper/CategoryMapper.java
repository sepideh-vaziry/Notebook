package com.sepideh.notebook.mapper;

import com.sepideh.notebook.dto.category.CategoryDto;
import com.sepideh.notebook.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {

    //******************************************************************************************************************
    public abstract CategoryDto toDto(Category category);

    public abstract List<CategoryDto> toDto(List<Category> categories);

    //******************************************************************************************************************
    public abstract Category toModel(CategoryDto categoryDto);

    //******************************************************************************************************************
    public abstract Category update(CategoryDto categoryDto, @MappingTarget Category category);

    //******************************************************************************************************************
    protected Long categoryToId(Category category) {
        return category.getId();
    }

    protected Category toCategory(long id) {
        return new Category(id);
    }

}
