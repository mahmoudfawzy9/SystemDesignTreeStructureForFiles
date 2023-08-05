package com.mahmoud.stc.controller;

import com.mahmoud.stc.entity.*;
import com.mahmoud.stc.repository.PermissionGroupRepository;
import com.mahmoud.stc.service.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    private final PermissionGroupRepository permissionGroupRepository;

    private final SpaceService spaceService;

    private final FolderService folderService;


    public ItemController(ItemService itemService, PermissionGroupRepository permissionGroupRepository, SpaceService spaceService, FolderService folderService) {
        this.itemService = itemService;
        this.permissionGroupRepository = permissionGroupRepository;
        this.spaceService = spaceService;
        this.folderService = folderService;
    }

    @PostMapping("/stc-assessments")
    public Space createSpace(@RequestParam String name, @RequestParam Long permissionGroupId, @RequestParam Long userId) {
        PermissionGroup permissionGroup = permissionGroupRepository.findById(permissionGroupId).orElse(null);
        return itemService.createSpace(name, permissionGroup, userId);
    }

    @PostMapping("/stc-assessments/backend")
    public Folder createFolder(@RequestParam String name, @RequestParam Long permissionGroupId, @RequestParam Long parentId, @RequestParam Long userId) {
        PermissionGroup permissionGroup = permissionGroupRepository.findById(permissionGroupId).orElse(null);
        Space parent =  spaceService.getSpaceById(parentId);
        return itemService.createFolder(name, permissionGroup, parent, userId);
    }

    @PostMapping(value="assessment.pdf" , produces =  APPLICATION_JSON_VALUE, consumes = MULTIPART_FORM_DATA_VALUE)
    public File createFile(@RequestParam String name, @RequestParam Long permissionGroupId,  @RequestPart("binaryFile")@Valid MultipartFile binaryFile, @RequestParam Long parentId, @RequestParam Long userId) {
        PermissionGroup permissionGroup = permissionGroupRepository.findById(permissionGroupId).orElse(null);
        Folder parent =  folderService.getFolderById(parentId);
        return itemService.createFile(name, permissionGroup, binaryFile, parent, userId);
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteItemById(@PathVariable Long id) {
        itemService.deleteItemById(id);
    }

}
