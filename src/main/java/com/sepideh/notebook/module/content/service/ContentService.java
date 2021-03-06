package com.sepideh.notebook.module.content.service;

import com.sepideh.notebook.module.content.model.Content;
import com.sepideh.notebook.module.content.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ContentService {

    private final ContentRepository contentRepository;

    // Constructor *****************************************************************************************************
    @Autowired
    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    //******************************************************************************************************************
    @Transactional
    public Content createContent(Content content) {
        return contentRepository.save(content);
    }

    //******************************************************************************************************************
    public List<Content> getAllContent() {
        return contentRepository.findAll();
    }

}
