package com.example.candidate_test_specification.web.controllers;

import com.example.candidate_test_specification.services.UserService;
import com.example.candidate_test_specification.web.controllers.user.LoginRequest;
import com.example.candidate_test_specification.web.controllers.user.UserDto;
import com.example.candidate_test_specification.web.controllers.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/add")
    public ResponseEntity<String> register(@RequestBody @Valid UserDto userDto) {
        userService.addUser(userDto);
        return new ResponseEntity<>("User was added.",HttpStatus.OK);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<UserResponse> authenticate(@RequestBody @Valid LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.login(loginRequest),HttpStatus.OK);
    }
}
