package com.sepideh.notebook.service;

import com.sepideh.notebook.dto.media.MediaFileDto;
import com.sepideh.notebook.mapper.MediaFileMapper;
import com.sepideh.notebook.model.MediaFile;
import com.sepideh.notebook.repository.MediaFileRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class MediaFileService {

    private static final String FILE_URL_PREFIX = "files";

    private final MediaFileRepository mediaFileRepository;
    private final MediaFileMapper mediaFileMapper;

    // Constructor *****************************************************************************************************
    public MediaFileService(MediaFileRepository mediaFileRepository, MediaFileMapper mediaFileMapper) {
        this.mediaFileRepository = mediaFileRepository;
        this.mediaFileMapper = mediaFileMapper;
    }

    //******************************************************************************************************************
    public MediaFileDto createMediaFile(MediaFileDto mediaFileDto) throws IOException {
        String filePath = createFile(mediaFileDto.getFile());
        mediaFileDto.setPath(filePath);

        MediaFile mediaFile = mediaFileRepository.save(
            mediaFileMapper.toModel(mediaFileDto)
        );

        return mediaFileMapper.toDto(mediaFile);
    }

    //******************************************************************************************************************
    public MediaFileDto updateMediaFile(MediaFileDto mediaFileDto) throws IOException {
        MediaFile mediaFile = findById(mediaFileDto.getId());

        if (removeFile(mediaFile.getPath())) {
            String filePath = createFile(mediaFileDto.getFile());
            mediaFileDto.setPath(filePath);

            mediaFileRepository.save(
                mediaFileMapper.update(mediaFileDto, mediaFile)
            );

            return mediaFileMapper.toDto(mediaFile);
        }
        else {
            return mediaFileDto;
        }

    }

    //******************************************************************************************************************
    public MediaFile findById(Long id) throws DataAccessException {
        return mediaFileRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public MediaFileDto findDtoById(Long id) {
        return mediaFileMapper.toDto(findById(id));
    }

    //******************************************************************************************************************
    public Page<MediaFile> getAllMediaFile(Pageable pageable) {
        return mediaFileRepository.findAll(pageable);
    }

    //******************************************************************************************************************
    public boolean deleteMediaFile(long id) {
        MediaFile mediaFile = findById(id);

        if (removeFile(mediaFile.getPath())) {
            mediaFileRepository.delete(mediaFile);

            return true;
        }
        else {
            return false;
        }
    }

    //******************************************************************************************************************
    private String createFile(MultipartFile multipartFile) throws IOException {
        String absolutePath = ResourceUtils.getFile("classpath:public/").getAbsolutePath();

        //Get file extension
        byte[] bytes = multipartFile.getBytes();
        String extension = Objects.requireNonNull(multipartFile.getContentType()).split("/")[1];

        //Create name for file
        String filePath = createFilePath(extension);
        File file = new File(absolutePath + File.separator + filePath);

        while (file.exists()){
            filePath = createFilePath(extension);
            file = new File(absolutePath + File.separator + filePath);
        }

        //Create file
        Files.createDirectories(file.getParentFile().toPath());
        Files.write(Paths.get(file.getAbsolutePath()), bytes);

        System.out.println(file.getAbsolutePath());

        return filePath;
    }

    private String createFilePath(String fileExtension) {
        int random = new Random().nextInt(900) + 100;
        String randomString = String.valueOf(random);

        String name = UUID.randomUUID()
            .toString()
            .substring(0, 10)
            .concat(".")
            .concat(fileExtension);

        return FILE_URL_PREFIX
            .concat(File.separator)
            .concat(String.valueOf(Character.digit(randomString.charAt(0), 10)))
            .concat(File.separator)
            .concat(String.valueOf(Character.digit(randomString.charAt(1), 10)))
            .concat(File.separator)
            .concat(String.valueOf(Character.digit(randomString.charAt(2), 10)))
            .concat(File.separator)
            .concat(name);
    }

    //******************************************************************************************************************
    private boolean removeFile(String path) {
        try {
            String absolutePath = ResourceUtils.getFile("classpath:public/").getAbsolutePath();
            String filePath = absolutePath
                .concat(File.separator)
                .concat(path);

            System.out.println(filePath);
            Files.delete(Paths.get(filePath));
            return true;
        }
        catch (IOException e) {
            System.out.println(e);

            return false;
        }
    }

}
