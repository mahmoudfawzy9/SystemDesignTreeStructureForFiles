package com.mahmoud.stc.controller;

import com.mahmoud.stc.service.Impl.UserApiResponse;
import com.mahmoud.stc.DTO.UserDTOs;
import com.mahmoud.stc.service.Impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping(value = "v2/register", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public UserApiResponse registerUserV2(@RequestBody UserDTOs.UserRegistrationObject userJson) throws RuntimeException {
    return this.userService.registerUser(userJson);
    }

    @PostMapping(value = "avatar", produces =  APPLICATION_JSON_VALUE, consumes = MULTIPART_FORM_DATA_VALUE)
    public UserApiResponse bonusApiToUpdateUserAvatar(@RequestHeader (name = "User-Token") String userToken, @RequestParam Long userId, @RequestPart("file")@Valid MultipartFile file){

    return this.userService.updateUserAvatar(file, userId);

    }
}
