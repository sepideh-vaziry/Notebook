package com.sepideh.notebook.service;

import com.sepideh.notebook.DomainEventPublisher;
import com.sepideh.notebook.dto.content.ContentDto;
import com.sepideh.notebook.events.ContentCreatedEvent;
import com.sepideh.notebook.mapper.ContentMapper;
import com.sepideh.notebook.model.Content;
import com.sepideh.notebook.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ContentService {

    private final static String CONTENT_CACHE_VALUE = "ContentCache";

    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;

    private final DomainEventPublisher domainEventPublisher;

    // Constructor *****************************************************************************************************
    @Autowired
    public ContentService(
        ContentRepository contentRepository,
        ContentMapper contentMapper,
        DomainEventPublisher domainEventPublisher
    ) {
        this.contentRepository = contentRepository;
        this.contentMapper = contentMapper;
        this.domainEventPublisher = domainEventPublisher;
    }

    //******************************************************************************************************************
    @Transactional
    public Content createContent(ContentDto contentDto) {
        contentDto.setId(null);
        Content content = contentRepository.save(contentMapper.toModel(contentDto));

        ContentCreatedEvent event = new ContentCreatedEvent(content);
        domainEventPublisher.publish(event);

        return content;
    }

    //******************************************************************************************************************
    @Transactional
    @CachePut(value = CONTENT_CACHE_VALUE, key = "'ContentId='.concat(#contentDto.id)")
    public Content updateContent(ContentDto contentDto) {
        Content content = findById(contentDto.getId());

        return contentRepository.save(contentMapper.update(contentDto, content));
    }

    //******************************************************************************************************************
    @Cacheable(value = CONTENT_CACHE_VALUE, key = "'ContentId='.concat(#id)")
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
    @CacheEvict(value = CONTENT_CACHE_VALUE, key = "'ContentId='.concat(#id)")
    public boolean deleteContent(long id) {
        Content content = findById(id);
        contentRepository.delete(content);

        return true;
    }

}
