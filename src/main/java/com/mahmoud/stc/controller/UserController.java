package com.mahmoud.stc.controller;

import com.mahmoud.stc.service.Impl.UserApiResponse;
import com.mahmoud.stc.DTO.UserDTOs;
import com.mahmoud.stc.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
}
