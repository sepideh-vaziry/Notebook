package com.sepideh.notebook.service;

import com.sepideh.notebook.dto.content.ContentDto;
import com.sepideh.notebook.mapper.ContentMapper;
import com.sepideh.notebook.model.Content;
import com.sepideh.notebook.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ContentService {

    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;

    // Constructor *****************************************************************************************************
    @Autowired
    public ContentService(ContentRepository contentRepository, ContentMapper contentMapper) {
        this.contentRepository = contentRepository;
        this.contentMapper = contentMapper;
    }

    //******************************************************************************************************************
    @Transactional
    public Content createContent(ContentDto contentDto) {
        contentDto.setId(null);
        return contentRepository.save(contentMapper.toModel(contentDto));
    }

    //******************************************************************************************************************
    @Transactional
    public Content updateContent(ContentDto contentDto) {
        Content content = findById(contentDto.getId());

        return contentRepository.save(contentMapper.update(contentDto, content));
    }

    //******************************************************************************************************************
    public Content findById(long id) throws DataAccessException {
        return contentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    //******************************************************************************************************************
    public List<Content> getAllContent() {
        return contentRepository.findAll();
    }

    //******************************************************************************************************************
    public Page<Content> getAllContent(Pageable pageable) {
        return contentRepository.findAll(pageable);
    }

    //******************************************************************************************************************
    public boolean deleteContent(long id) {
        Content content = findById(id);
        contentRepository.delete(content);

        return true;
    }

}
