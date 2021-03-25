package com.sepideh.notebook.controller;

import com.sepideh.notebook.dto.media.MediaFileDto;
import com.sepideh.notebook.dto.response.GenericRestResponse;
import com.sepideh.notebook.mapper.MediaFileMapper;
import com.sepideh.notebook.model.MediaFile;
import com.sepideh.notebook.service.MediaFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/file")
public class MediaFileController {

    private final MediaFileService mediaFileService;
    private final MediaFileMapper mediaFileMapper;

    // Constructor *****************************************************************************************************
    @Autowired
    public MediaFileController(MediaFileService mediaFileService, MediaFileMapper mediaFileMapper) {
        this.mediaFileService = mediaFileService;
        this.mediaFileMapper = mediaFileMapper;
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public ResponseEntity<GenericRestResponse<MediaFileDto>> createMediaFile(
        @RequestParam MultipartFile file
    ) throws IOException {
        MediaFileDto mediaFile = new MediaFileDto();
        mediaFile.setFile(file);
        return new ResponseEntity<>(
            new GenericRestResponse<>(
                mediaFileService.createMediaFile(mediaFile),
                "Create file successfully",
                HttpStatus.CREATED.value()
            ),
            HttpStatus.CREATED
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"", "/"}, method = RequestMethod.PUT)
    public ResponseEntity<GenericRestResponse<MediaFileDto>> updateMediaFile(
        @RequestParam MultipartFile file,
        @RequestParam long id
    ) throws IOException {
        MediaFileDto mediaFile = new MediaFileDto();
        mediaFile.setId(id);
        mediaFile.setFile(file);
        return new ResponseEntity<>(
            new GenericRestResponse<>(
                mediaFileService.updateMediaFile(mediaFile),
                "Update file successfully",
                HttpStatus.OK.value()
            ),
            HttpStatus.OK
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"/{id}", "/{id}/"}, method = RequestMethod.GET)
    public ResponseEntity<GenericRestResponse<MediaFileDto>> getMediaFile(
        @PathVariable long id
    ) {
        return new ResponseEntity<>(
            new GenericRestResponse<>(
                mediaFileService.findDtoById(id),
                "Update file successfully",
                HttpStatus.OK.value()
            ),
            HttpStatus.OK
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ResponseEntity<GenericRestResponse<List<MediaFileDto>>> getAllMediaFile(
        @RequestParam("pageSize") int pageSize,
        @RequestParam("pageNumber") int pageNumber
    ) {
        Page<MediaFile> page = mediaFileService.getAllMediaFile(PageRequest.of(pageNumber, pageSize));

        return new ResponseEntity<>(
            new GenericRestResponse<>(
                mediaFileMapper.toDto(page.getContent()),
                HttpStatus.OK.value(),
                pageSize,
                pageNumber,
                page.getTotalPages(),
                page.getTotalElements()
            ),
            HttpStatus.OK
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"/{id}", "/{id}/"}, method = RequestMethod.DELETE)
    public ResponseEntity<GenericRestResponse<Boolean>> deleteMediaFile(
        @PathVariable("id") long id
    ) {
        return new ResponseEntity<>(
            new GenericRestResponse<>(
                mediaFileService.deleteMediaFile(id),
                "Delete file successfully",
                HttpStatus.OK.value()
            ),
            HttpStatus.OK
        );
    }

}
