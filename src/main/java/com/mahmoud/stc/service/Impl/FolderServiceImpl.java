package com.mahmoud.stc.service.Impl;

import com.mahmoud.stc.entity.Folder;
import com.mahmoud.stc.exception.FolderNotFoundException;
import com.mahmoud.stc.helper.message.BusinessLogMessage;
import com.mahmoud.stc.helper.message.BusinessMessage;
import com.mahmoud.stc.repository.FolderRepository;
import com.mahmoud.stc.service.FolderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
@Slf4j
@Service
@Transactional
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;

    public FolderServiceImpl(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    public List<Folder> getAllFolders() {
        return folderRepository.findAll();
    }

    public Folder getFolderById(Long id) {
        return folderRepository.findById(id)
                .orElseThrow(() -> new FolderNotFoundException("Folder not found with id " + id));
    }

    public Folder createFolder(Folder folder) {
        if (folder.getParent() == null) {
            log.info(BusinessMessage.INVALID_REQUEST_EXCEPTION);
        }
        log.info(BusinessLogMessage.Folder.FOLDER_CREATED);
        return folderRepository.save(folder);
    }

    public Folder updateFolder(Long id, Folder folder) {
        Folder existingFolder = getFolderById(id);
        existingFolder.setName(folder.getName());
        existingFolder.setParent(folder.getParent());
        existingFolder.setPermissionGroup(folder.getPermissionGroup());
        return folderRepository.save(existingFolder);
    }

    public void deleteFolder(Long id) {
        Folder folder = getFolderById(id);
        folderRepository.delete(folder);
    }
}
