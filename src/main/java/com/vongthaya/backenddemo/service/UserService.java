package com.vongthaya.backenddemo.service;

import com.vongthaya.backenddemo.dto.*;
import com.vongthaya.backenddemo.entity.User;
import com.vongthaya.backenddemo.exception.BaseException;
import com.vongthaya.backenddemo.exception.UserException;
import com.vongthaya.backenddemo.mapper.UserMapper;
import com.vongthaya.backenddemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final TokenService tokenService;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       UserMapper userMapper,
                       TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    public LoginResponseDTO login(LoginRequest loginRequest) throws BaseException {
        // validate request
        if (loginRequest == null) {
            throw UserException.requestNull();
        }

        if (loginRequest.getEmail() == null || loginRequest.getEmail().isBlank()) {
            throw UserException.emailNullOrBlank();
        }

        if (loginRequest.getPassword() == null || loginRequest.getPassword().isBlank()) {
            throw UserException.passwordNullOrBlank();
        }

        // verify email & password
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isEmpty()) {
            throw UserException.loginFail();
        }

        User user = userOptional.get();

        boolean isPasswordCorrect = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());

        if (!isPasswordCorrect) {
            throw UserException.loginFail();
        }

        String token = tokenService.generateToken(user);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setToken(token);

        return loginResponseDTO;
    }

    public UserResponse create(RegisterRequest registerRequest) throws BaseException {
        // validate
        if (registerRequest.getEmail() == null || registerRequest.getEmail().isBlank()) {
            throw UserException.emailNullOrBlank();
        }

        if (registerRequest.getFullname() == null || registerRequest.getFullname().isBlank()) {
            throw UserException.nameNullOrBlank();
        }

        if (registerRequest.getPassword() == null || registerRequest.getPassword().isBlank()) {
            throw UserException.passwordNullOrBlank();
        }

        // verify
        boolean existsEmail = userRepository.existsByEmail(registerRequest.getEmail());
        if (existsEmail) {
            throw UserException.emailDuplicate();
        }

        // save.
        User theUser = new User();
        theUser.setName(registerRequest.getFullname());
        theUser.setEmail(registerRequest.getEmail());
        theUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        this.userRepository.save(theUser);

        return userMapper.toUserResponse(theUser);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserResponse update(UpdateUserDTO updateUserDTO) throws BaseException {
        Optional<User> userOp = userRepository.findById(updateUserDTO.getId());

        if (userOp.isEmpty()) {
            throw UserException.notFound();
        }

        User user = userOp.get();
        user.setName(updateUserDTO.getName());

        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public String refreshToken() throws BaseException {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        int userId = (int) authentication.getPrincipal();

        Optional<User> userOp = userRepository.findById(userId);

        if (userOp.isEmpty()) {
            throw UserException.notFound();
        }

        User user = userOp.get();

        return tokenService.generateToken(user);
    }

}
