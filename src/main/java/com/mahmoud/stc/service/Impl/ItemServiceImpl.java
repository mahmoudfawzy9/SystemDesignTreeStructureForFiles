package com.mahmoud.stc.service.Impl;

import com.google.common.net.MediaType;
import com.mahmoud.stc.config.AppConfig;
import com.mahmoud.stc.entity.*;
import com.mahmoud.stc.enums.ItemType;
import com.mahmoud.stc.enums.PermissionLevel;
import com.mahmoud.stc.repository.*;
import com.mahmoud.stc.service.ItemService;
import com.mahmoud.stc.utils.StringUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.util.Collections;
import java.util.*;
import java.util.UUID;

import static com.google.common.io.Files.getNameWithoutExtension;
import static com.mahmoud.stc.utils.StringUtils.isBlankOrNull;
import static java.util.Optional.ofNullable;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private AppConfig appConfig;
    private Path basePath;
    private final ItemRepository itemRepository;
    private final SpaceRepository spaceRepository;
    private final FolderRepository folderRepository;
    private final FileRepository fileRepository;

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    public ItemServiceImpl(ItemRepository itemRepository,
                           SpaceRepository spaceRepository,
                           FolderRepository folderRepository,
                           FileRepository fileRepository,
                           UserRepository userRepository, PermissionRepository permissionRepository) {
        this.itemRepository = itemRepository;
        this.spaceRepository = spaceRepository;
        this.folderRepository = folderRepository;
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    @PostConstruct
    public void setupFileLocation() throws RuntimeException {
        this.basePath = Paths.get(appConfig.getBasePathStr());

        if(!Files.exists(basePath) ) {
            try {
                Files.createDirectories(basePath);
            } catch (Exception ex) {
                throw new RuntimeException("Could not create the Base directory for storing files!");
            }
        }
    }
    
    @Override
    public Space createSpace(String name, PermissionGroup permissionGroup, Long userId) {

        UserEntity user = userRepository.findById(userId).get();

            Space space = new Space();
            space.setName(name);
            space.setType(ItemType.SPACE);
            space.setPermissionGroup(permissionGroup);

            spaceRepository.save(space);

            List<Permission> permissions = new ArrayList<>();
            //TODO Assign the right permissions
            if (user.getPermissionLevel().equals(PermissionLevel.EDIT)){
                for (Permission permission: permissions) {
                    permission.setPermissionGroup(space.getPermissionGroup());
                    permissionRepository.save(permission);
                }
            }

            space.setPermissions(permissions);
            itemRepository.save(space);
            return space;   
    }

  @Override
    public Folder createFolder(String name, PermissionGroup permissionGroup, Space parent, Long userId) {

        List<Permission> permissions = new ArrayList<>();
        Folder folder = new Folder();
        folder.setName(name);
        folder.setType(ItemType.FOLDER);
        folder.setParent(parent);
        folder.setPermissionGroup(permissionGroup);
        folderRepository.save(folder);


        UserEntity user = userRepository.findById(userId).get();
        //TODO would handle the VIEW and other permissions
        if (user.getPermissionLevel().equals(PermissionLevel.EDIT)){
            for (Permission permission : permissions) {
                permission.setPermissionGroup(folder.getPermissionGroup());
                permissionRepository.save(permission);
            }
        }

        folder.setPermissions(permissions);
        itemRepository.save(folder);
        return folder;
    }

    @Override
    public File createFile(String name, PermissionGroup permissionGroup, MultipartFile binaryFile, Folder parent, Long userId) {
        //TODO later would support more file types
        validateMimeType(binaryFile);

        File file = new File();
        file.setName(name);
        file.setType(ItemType.FILE);
        file.setParent(parent);
        file.setPermissionGroup(permissionGroup);
        fileRepository.save(file);
        String fileUrl = getUrlForUser(file.getOriginalFileName(), userId);
        Path fileRelativeLocation = getRelativeLocationForUser(binaryFile.getOriginalFilename(), userId);

        List<Permission> permissions = new ArrayList<>();
        // TODO revise the business to make the right permissions
        for (Permission permission: permissions) {
            permission.setPermissionGroup(file.getPermissionGroup());
            permissionRepository.save(permission);
        }

        file.setPermissions(permissions);
        itemRepository.save(file);
        saveFileForUser(binaryFile, userId);
        saveToDatabaseForUser(binaryFile.getOriginalFilename(),fileRelativeLocation, fileUrl, userId);

        return file;
    }

    public String saveFileForUser(MultipartFile file, Long userId) {

        validateMimeType(file);

        if(userId != null && !userRepository.existsById(userId)){
            throw new RuntimeException("User with the specified id is not found!");
        }
        if(isBlankOrNull(file.getOriginalFilename()) ) {
            throw new RuntimeException("File name is not valid");
        }

        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = getUniqueName(originalFileName, userId);
        String fileUrl = getUrlForUser(originalFileName, userId);
        Path fileRelativeLocation = getRelativeLocationForUser(uniqueFileName, userId);

        saveFileForUser(file, uniqueFileName ,userId );

        saveToDatabaseForUser(originalFileName, fileRelativeLocation , fileUrl, userId);

        return fileUrl;
    }
    private void createDirIfNotExists(Path saveDir) {
        if(!Files.exists(saveDir)) {
            try {
                Files.createDirectories(saveDir);
                /** It gives the required permissions for WINDOWS OS to make dirs **/
                AclFileAttributeView aclView = Files.getFileAttributeView(saveDir, AclFileAttributeView.class);
                if (aclView != null) {
                    AclEntry entry = AclEntry.newBuilder()
                            .setType(AclEntryType.ALLOW)
                            .setPrincipal(aclView.getOwner())
                            .setPermissions(AclEntryPermission.READ_DATA, AclEntryPermission.WRITE_DATA, AclEntryPermission.APPEND_DATA, AclEntryPermission.READ_ATTRIBUTES, AclEntryPermission.WRITE_ATTRIBUTES, AclEntryPermission.READ_NAMED_ATTRS, AclEntryPermission.WRITE_NAMED_ATTRS, AclEntryPermission.READ_ACL, AclEntryPermission.WRITE_ACL, AclEntryPermission.SYNCHRONIZE)
                            .build();
                    aclView.setAcl(Collections.singletonList(entry));
                }
            } catch (IOException e) {
              e.printStackTrace();
            throw new RuntimeException("Failed to create directory at location");
            }
        }
    }
    private void saveFileForUser(MultipartFile file, String uniqueFileName, Long userId) {
        if (file == null || file.isEmpty())
            throw new IllegalArgumentException("Invalid file");

        Path saveDir = getSaveDirForUser(userId);
        createDirIfNotExists(saveDir);
        Path targetLocation = saveDir.resolve(uniqueFileName);

        try {
            file.transferTo(targetLocation);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save file Organization directory at location");
        }
    }
    private String getMimeType(Path file) {
        String mimeType = MediaType.OCTET_STREAM.toString();

        Tika tika = new Tika();
        try {
            mimeType = tika.detect(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse MIME type for the file:");
        }

        return mimeType;
    }
    private void saveToDatabaseForUser(String originalName, Path location, String url, Long userId){

      if(userId == null){
          throw new RuntimeException("No user found for the specified id");
      }
        UserEntity user = userRepository.findById(userId).get();

        String mimeType = getMimeType(basePath.resolve(location));

        File fileEntity = new File();
        fileEntity.setLocation( location.toString().replace("\\", "/") );
        fileEntity.setOriginalFileName(originalName);
        fileEntity.setFileUrl(url);
        fileEntity.setMimetype(mimeType);
        fileEntity.setUserId(user);

        fileEntity = fileRepository.save(fileEntity);
    }

    private void validateMimeType(MultipartFile file) {
        String mimeType = file.getContentType();
        if (!mimeType.startsWith("image") || !mimeType.startsWith("image") )
            throw new RuntimeException("Select a valid Image or video");

        }

    private String getUrlForUser(String originalName, Long userId) {
        return ofNullable(userId)
                .map(id -> String.format("Space/%d/%s", id, originalName))// prefix Space/userId/filename
                .orElse("customers/"+originalName);
    }

    private Path getSaveDirForUser(Long userId) {
        return ofNullable(userId)
                .map(Object::toString)
                .map(s -> "Space/" + s) // add Space/ prefix
                .map(basePath::resolve)
                .orElse(basePath);
    }

    private Path getRelativeLocationForUser(String originalName, Long userId) {
        return basePath
                .relativize( getSaveDirForUser(userId) )
                .resolve(originalName);
    }

    private String sanitize(String name) {
        return StringUtils.getFileNameSanitized(name);

    }

    private String getUniqueName(String origName, Long userId) {
        Optional<String> opt = Optional.of(origName)
                .map(this::sanitize)
                .filter(name -> notUniqueFileName(name, userId))
                .map(this::getUniqueRandomName);
        if (opt.isPresent()) {
            return opt.get();
        }
        return Optional.of(origName)
                .map(this::sanitize)
                .get();
    }

    private String getUniqueRandomName(String origName) {
        String ext = com.google.common.io.Files.getFileExtension(origName);
        String origNameNoExtension = getNameWithoutExtension(origName);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return String.format("%s-%s.%s", origNameNoExtension, uuid , ext);
    }
    private boolean notUniqueFileName(String origName, Long userId) {
        String url = getUrlForUser(origName, userId);
        Path location = getRelativeLocationForUser(origName, userId);

        return  fileRepository.existsByFileUrl(url)
                || fileRepository.existsByLocation(location.toString())
                || Files.exists(location) ;
    }


    @Override
    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteItemById(Long id) {
        itemRepository.deleteById(id);
    }

    private void assignPermissions(Item item, List<Permission> permissions) {
        for (Permission permission : permissions) {
            permission.setPermissionGroup(item.getPermissionGroup());
            permissionRepository.save(permission);
        }
        item.setPermissions(permissions);
        itemRepository.save(item);
    }
}

