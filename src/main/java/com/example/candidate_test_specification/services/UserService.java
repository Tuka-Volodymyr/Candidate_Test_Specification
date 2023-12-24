package com.example.candidate_test_specification.services;

import com.example.candidate_test_specification.domain.User;
import com.example.candidate_test_specification.web.controllers.user.LoginRequest;
import com.example.candidate_test_specification.web.controllers.user.UserDto;
import com.example.candidate_test_specification.web.controllers.user.UserResponse;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByUsername(String email);

    User getUser();

    void addUser(UserDto userDto);
    void save(User user);

    UserResponse login(LoginRequest loginRequest);
    public Optional<User> validUsernameAndPassword(String username, String password);
}
