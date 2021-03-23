package com.sepideh.notebook.controller;

import com.sepideh.notebook.model.Content;
import com.sepideh.notebook.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    private final ContentService contentService;

    // Constructor *****************************************************************************************************
    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public Content createContent(@RequestBody Content content) {
        return contentService.createContent(content);
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public List<Content> getAllContent() {
        return contentService.getAllContent();
    }

}
