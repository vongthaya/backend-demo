package com.vongthaya.backenddemo.controller;

import com.vongthaya.backenddemo.dto.LoginRequest;
import com.vongthaya.backenddemo.dto.LoginResponseDTO;
import com.vongthaya.backenddemo.dto.RegisterRequest;
import com.vongthaya.backenddemo.dto.UserResponse;
import com.vongthaya.backenddemo.exception.BaseException;
import com.vongthaya.backenddemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequest loginRequest) throws BaseException {
        LoginResponseDTO loginResponseDTO = userService.login(loginRequest);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest registerRequest) throws BaseException {
        UserResponse userResponse = userService.create(registerRequest);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

//    @PostMapping("/upload-profile-picture")
//    public ResponseEntity<String> uploadProfilePicture(@RequestPart MultipartFile file) {
//        String response = userService.uploadProfilePicture(file);
//        return ResponseEntity.ok(response);
//    }

}
