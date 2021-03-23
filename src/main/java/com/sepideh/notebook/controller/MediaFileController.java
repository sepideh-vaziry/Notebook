package com.sepideh.notebook.controller;

import com.sepideh.notebook.model.MediaFile;
import com.sepideh.notebook.service.MediaFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class MediaFileController {

    private final MediaFileService mediaFileService;

    // Constructor *****************************************************************************************************
    @Autowired
    public MediaFileController(MediaFileService mediaFileService) {
        this.mediaFileService = mediaFileService;
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public MediaFile createMediaFile(@RequestParam MultipartFile file) throws IOException {
        MediaFile mediaFile = new MediaFile();
        mediaFile.setFile(file);
        return mediaFileService.createMediaFile(mediaFile);
    }

}
