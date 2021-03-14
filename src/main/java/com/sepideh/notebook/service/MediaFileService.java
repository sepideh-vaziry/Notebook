package com.sepideh.notebook.service;

import com.sepideh.notebook.domain.MediaFile;
import com.sepideh.notebook.repository.MediaFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class MediaFileService {

    private final MediaFileRepository mediaFileRepository;

    // Constructor *****************************************************************************************************
    public MediaFileService(MediaFileRepository mediaFileRepository) {
        this.mediaFileRepository = mediaFileRepository;
    }

    //******************************************************************************************************************
    public MediaFile createMediaFile(MediaFile mediaFile) throws IOException {
        int random = new Random().nextInt(900) + 100;
        String randomString = String.valueOf(random);
        String absolutePath = ResourceUtils.getFile("classpath:").getAbsolutePath();

        byte[] bytes = mediaFile.getFile().getBytes();
        String extension = Objects.requireNonNull(mediaFile.getFile().getContentType()).split("/")[1];

        //Create name for file
        String name = UUID.randomUUID().toString().substring(0, 10) + "." + extension;
        String filePath = "files" +
                File.separator + Character.digit(randomString.charAt(0), 10) +
                File.separator + Character.digit(randomString.charAt(1), 10) +
                File.separator + Character.digit(randomString.charAt(2), 10) +
                File.separator + name;
        File file = new File(absolutePath + File.separator + filePath);

        while (file.exists()){
            name = UUID.randomUUID().toString().substring(0, 10) + "." + extension;
            filePath = "files" +
                    File.separator + Character.digit(randomString.charAt(0), 10) +
                    File.separator + Character.digit(randomString.charAt(1), 10) +
                    File.separator + Character.digit(randomString.charAt(2), 10) +
                    File.separator + name;
            file = new File(absolutePath + File.separator + filePath);
        }

        //Create file
        Files.createDirectories(file.getParentFile().toPath());
        Files.write(Paths.get(file.getAbsolutePath()), bytes);
        mediaFile.setPath(filePath);

        return mediaFileRepository.save(mediaFile);
    }

    //******************************************************************************************************************
    public Optional<MediaFile> getMediaFileById(Long id) {
        return mediaFileRepository.findById(id);
    }

    //******************************************************************************************************************
    public List<MediaFile> getAllMediaFile() {
        return mediaFileRepository.findAll();
    }

}
