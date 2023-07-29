package com.mahmoud.stc.controller;

import com.mahmoud.stc.entity.*;
import com.mahmoud.stc.enums.PermissionLevel;
import com.mahmoud.stc.repository.PermissionGroupRepository;
import com.mahmoud.stc.service.Impl.UserApiResponse;
import com.mahmoud.stc.service.ItemService;
import com.mahmoud.stc.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    private final PermissionGroupRepository permissionGroupRepository;

    private final UserService userService;


    public ItemController(ItemService itemService, PermissionGroupRepository permissionGroupRepository, UserService userService) {
        this.itemService = itemService;
        this.permissionGroupRepository = permissionGroupRepository;
        this.userService = userService;
    }

    @PostMapping("/stc-assessments")
    public Space createSpace(@RequestParam String name, @RequestParam Long permissionGroupId, @RequestParam Long userId) {
        PermissionGroup permissionGroup = permissionGroupRepository.findById(permissionGroupId).orElse(null);
        return itemService.createSpace(name, permissionGroup, userId);
    }

    @PostMapping("/stc-assessments/backend")
    public Folder createFolder(@RequestParam String name, @RequestParam Long permissionGroupId, @RequestParam Long parentId, @RequestBody List<Permission> permissions) {
        PermissionGroup permissionGroup = permissionGroupRepository.findById(permissionGroupId).orElse(null);
        Folder parent = (Folder) itemService.getItemById(parentId);
        return itemService.createFolder(name, permissionGroup, parent, permissions);
    }

    @PostMapping("/assessment.pdf")
    public File createFile(@RequestParam String name, @RequestParam Long permissionGroupId, @RequestPart MultipartFile binaryFile, @RequestParam Long parentId, @RequestParam Long userId) {
        PermissionGroup permissionGroup = permissionGroupRepository.findById(permissionGroupId).orElse(null);
        Folder parent = (Folder) itemService.getItemById(parentId);
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

    @PostMapping(value = "avatar", produces =  APPLICATION_JSON_VALUE, consumes = MULTIPART_FORM_DATA_VALUE)
    public UserApiResponse bonusApiToUpdateUserAvatar(@RequestParam Long userId, @RequestPart("file")@Valid MultipartFile file){

        return this.userService.updateUserAvatar(file, userId);

    }
}