package com.sepideh.notebook.module.mediafile.repository;

import com.sepideh.notebook.module.mediafile.model.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaFileRepository extends JpaRepository<MediaFile, Long> { }
