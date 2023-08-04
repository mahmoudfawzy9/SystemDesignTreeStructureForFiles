package com.mahmoud.stc.bootstrap;

import com.mahmoud.stc.entity.*;
import com.mahmoud.stc.enums.ItemType;
import com.mahmoud.stc.enums.Role;
import com.mahmoud.stc.repository.ItemRepository;
import com.mahmoud.stc.repository.PermissionGroupRepository;
import com.mahmoud.stc.repository.PermissionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final PermissionGroupRepository permissionGroupRepository;
    private final ItemRepository itemRepository;
    private final PermissionRepository permissionRepository;
    public DataLoader(PermissionGroupRepository permissionGroupRepository, ItemRepository itemRepository, PermissionRepository permissionRepository) {
        this.permissionGroupRepository = permissionGroupRepository;
        this.itemRepository = itemRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void run(String... args) throws Exception {


        PermissionGroup group1 = new PermissionGroup();
        group1.setGroupName("Group 1");

        PermissionGroup group2 = new PermissionGroup();
        group2.setGroupName("Group 2");

        permissionGroupRepository.saveAll(Arrays.asList(group1, group2));

        Permission permission1 = new Permission();
        permission1.setUserEmail("mohamed@gmail.com");
        permission1.setPermissionLevel(Role.EDIT);
        permission1.setPermissionGroup(group1);

        Permission permission2 = new Permission();
        permission2.setUserEmail("mahmoud@gmail.com");
        permission2.setPermissionLevel(Role.VIEW);
        permission2.setPermissionGroup(group2);

        permissionRepository.saveAll(Arrays.asList(permission1, permission2));

        Space space = new Space();
        space.setName("My Space");
        space.setType(ItemType.SPACE);
        space.setPermissionGroup(group1);

        Folder folder1 = new Folder();
        folder1.setName("Folder 1");
        folder1.setType(ItemType.FOLDER);
        folder1.setParent(space);

        Folder folder2 = new Folder();
        folder2.setName("Folder 2");
        folder2.setType(ItemType.FOLDER);
        folder2.setParent(folder1);

        File file1 = new File();
        file1.setName("File 1");
        file1.setType(ItemType.FILE);
        file1.setParent(folder2);
        file1.setMimetype("Image.jpg");
        file1.setOriginalFileName("Image.jpg");
        file1.setFileUrl("Space/Folder 2/Image.jpg");
        file1.setLocation("{project-base-path}/src/test/resources/test_imgs_save_dir/Space/Folder 2/Image.jpg");

        itemRepository.saveAll(Arrays.asList(space, folder1, folder2, file1));
    }
}
