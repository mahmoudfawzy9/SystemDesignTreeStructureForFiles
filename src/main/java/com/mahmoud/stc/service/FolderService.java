package com.mahmoud.stc.service;

import com.mahmoud.stc.entity.Folder;

import java.util.List;

public interface FolderService {

    public List<Folder> getAllFolders();

    public Folder getFolderById(Long id);

    public Folder createFolder(Folder folder);

    public Folder updateFolder(Long id, Folder folder);

    public void deleteFolder(Long id);
}
