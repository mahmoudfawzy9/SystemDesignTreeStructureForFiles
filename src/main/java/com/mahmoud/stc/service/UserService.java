package com.mahmoud.stc.service;

import com.mahmoud.stc.service.Impl.UserApiResponse;
import com.mahmoud.stc.DTO.UserDTOs;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    public UserApiResponse updateUserAvatar(MultipartFile file, Long userId);

    public UserApiResponse registerUser(UserDTOs.UserRegistrationObject userJson);
}
