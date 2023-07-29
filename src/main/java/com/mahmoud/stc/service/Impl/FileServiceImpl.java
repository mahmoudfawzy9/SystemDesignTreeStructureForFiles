package com.mahmoud.stc.service.Impl;

import com.mahmoud.stc.entity.File;
import com.mahmoud.stc.entity.Folder;
import com.mahmoud.stc.exception.FileNotFoundException;
import com.mahmoud.stc.exception.FolderNotFoundException;
import com.mahmoud.stc.repository.FileRepository;
import com.mahmoud.stc.repository.FolderRepository;
import com.mahmoud.stc.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;

    public FileServiceImpl(FileRepository fileRepository, FolderRepository folderRepository) {
        this.fileRepository = fileRepository;
        this.folderRepository = folderRepository;
    }

    public byte[] getFileById(Long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
//        return file.getBinaryFile().getBinary();
        return null;
    }

    public File createFile(MultipartFile multipartFile, Long folderId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new FolderNotFoundException("Folder not found with id " + folderId));
        File file = new File();
        file.setName(multipartFile.getOriginalFilename());
        file.setParent(folder);
        file.setPermissionGroup(folder.getPermissionGroup());
//        BinaryFile binaryFile = new BinaryFile();
//        try {
//            binaryFile.setBinary(multipartFile.getBytes());
//        } catch (IOException e) {
//            throw new RuntimeException("Could not read file data", e);
//        }
//        binaryFile.setFile(file);
//        file.setBinaryFile(binaryFile);
        return fileRepository.save(file);
    }

    @Override
    public void deleteFile(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + id));
        fileRepository.delete(file);
    }
}
