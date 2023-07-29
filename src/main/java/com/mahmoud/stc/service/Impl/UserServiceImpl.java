package com.mahmoud.stc.service.Impl;

import com.mahmoud.stc.DTO.UserDTOs;
import com.mahmoud.stc.entity.UserEntity;
import com.mahmoud.stc.enums.PermissionLevel;
import com.mahmoud.stc.enums.ResponseStatus;
import com.mahmoud.stc.repository.UserRepository;
import com.mahmoud.stc.service.ItemService;
import com.mahmoud.stc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.mahmoud.stc.enums.ResponseStatus.ACTIVATION_SENT;
import static com.mahmoud.stc.enums.ResponseStatus.NEED_ACTIVATION;
import static com.mahmoud.stc.utils.StringUtils.isNotBlankOrNull;
import static java.util.Arrays.asList;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


//    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final  ItemService itemService;

    private UserEntity createNewUserEntity(UserDTOs.UserRegistrationObject userJson) {
        UserEntity user = new UserEntity();
        user.setName(userJson.getName());
        user.setEmail(userJson.getEmail());
        user.setPermissionLevel(PermissionLevel.valueOf(userJson.permissionLevel));
        //TODO Add password encryption
//        user.setEncryptedPassword(passwordEncoder.encode(userJson.password));
        user.setPhoneNumber(userJson.getPhoneNumber());
        user.setAvatar(userJson.getAvatar());
        return user;
    }

    public UserApiResponse updateUserAvatar(MultipartFile file, Long userId) {

 //       TODO Add permissions security to get current user by security service
        if(userId == null){
            throw new RuntimeException("No user found for the specified id");
        }
        UserEntity userEntity = userRepository.findById(userId).get();

        List<ResponseStatus> successResponseStatusList = new ArrayList<>();

        String imageUrl = itemService.saveFileForUser(file,userId);
        if (isNotBlankOrNull(imageUrl)) {

            //First, set the new image url
            userEntity.setAvatar(imageUrl);
        }

        if (successResponseStatusList.isEmpty()) {
            successResponseStatusList.add(ResponseStatus.ACTIVATED);
        }
        //display  user Id, url of image
        return new UserApiResponse(userId, imageUrl, successResponseStatusList);
    }

    public UserApiResponse registerUser(UserDTOs.UserRegistrationObject userJson) {

        UserEntity user = createNewUserEntity(userJson);

        user = userRepository.saveAndFlush(user);


        return new UserApiResponse(user.getId(), asList(NEED_ACTIVATION, ACTIVATION_SENT));
    }
}
