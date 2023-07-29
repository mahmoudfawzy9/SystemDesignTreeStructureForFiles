package com.mahmoud.stc.service;

import com.mahmoud.stc.entity.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    public File createFile(MultipartFile multipartFile, Long folderId);

    public byte[] getFileById(Long id);


    public void deleteFile(Long id) ;

    }
