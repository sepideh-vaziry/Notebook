package com.sepideh.notebook.controller;

import com.sepideh.notebook.dto.content.ContentDto;
import com.sepideh.notebook.dto.content.SimpleContentDto;
import com.sepideh.notebook.dto.response.GenericRestResponse;
import com.sepideh.notebook.mapper.ContentMapper;
import com.sepideh.notebook.model.Content;
import com.sepideh.notebook.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/content")
public class ContentController {

    private final ContentService contentService;
    private final ContentMapper contentMapper;

    // Constructor *****************************************************************************************************
    @Autowired
    public ContentController(ContentService contentService, ContentMapper contentMapper) {
        this.contentService = contentService;
        this.contentMapper = contentMapper;
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public GenericRestResponse<Content> createContent(
        @RequestBody @Valid ContentDto contentDto
    ) {
        return new GenericRestResponse<>(
            contentService.createContent(contentDto),
            "Create successfully",
            HttpStatus.CREATED.value()
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"", "/"}, method = RequestMethod.PUT)
    public GenericRestResponse<Content> updateContent(
        @RequestBody @Valid ContentDto contentDto
    ) {
        return new GenericRestResponse<>(
            contentService.updateContent(contentDto),
            "Update successfully",
            HttpStatus.OK.value()
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public GenericRestResponse<Content> updateContent(@PathVariable("id") long id) {
        return new GenericRestResponse<>(
            contentService.findById(id),
            "Update successfully",
            HttpStatus.OK.value()
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public GenericRestResponse<List<SimpleContentDto>> getAllContent(
        @RequestParam("pageSize") int pageSize,
        @RequestParam("pageNumber") int pageNumber
    ) {
        Page<Content> page = contentService.getAllContent(PageRequest.of(pageNumber, pageSize));

        return new GenericRestResponse<>(
            contentMapper.toDto(page.getContent()),
            HttpStatus.OK.value(),
            pageSize,
            pageNumber,
            page.getTotalPages(),
            page.getTotalElements()
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public GenericRestResponse<Boolean> delete(
        @PathVariable("id") long id
    ) {
        return new GenericRestResponse<>(
            contentService.deleteContent(id),
            "Delete successfully",
            HttpStatus.OK.value()
        );
    }

}
