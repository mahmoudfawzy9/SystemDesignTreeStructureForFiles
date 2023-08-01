package com.mahmoud.stc.service;

import com.mahmoud.stc.entity.*;
import org.springframework.web.multipart.MultipartFile;


public interface ItemService {

    void setupFileLocation() throws RuntimeException;

    Space createSpace(String name, PermissionGroup permissionGroup, Long userId);

    Folder createFolder(String name, PermissionGroup permissionGroup, Space parent, Long userId);

    public String saveFileForUser(MultipartFile file, Long userId);

    File createFile(String name, PermissionGroup permissionGroup, MultipartFile binaryFile, Folder parent, Long userId);

    Item getItemById(Long id);

    void deleteItemById(Long id);
}
