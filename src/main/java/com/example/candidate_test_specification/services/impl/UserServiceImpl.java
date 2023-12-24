package com.example.candidate_test_specification.services.impl;

import com.example.candidate_test_specification.config.UserAuthenticationProvider;
import com.example.candidate_test_specification.domain.User;
import com.example.candidate_test_specification.domain.exceptions.BadRequestException;
import com.example.candidate_test_specification.domain.exceptions.UnauthorizedException;
import com.example.candidate_test_specification.domain.exceptions.UserNotFoundException;
import com.example.candidate_test_specification.repository.UserRepository;
import com.example.candidate_test_specification.services.UserService;
import com.example.candidate_test_specification.web.controllers.user.LoginRequest;
import com.example.candidate_test_specification.web.controllers.user.UserDto;
import com.example.candidate_test_specification.web.controllers.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthenticationProvider userAuthenticationProvider;
    @Override
    public Optional<User> getUserByUsername(String email) {
        return userRepository.findByUsername(email);
    }
    @Override
    public User getUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow(UserNotFoundException::new);
    }
    @Override
    public void addUser(UserDto userDto){
        Optional<User> userExist = getUserByUsername(userDto.getUsername());
        if(userExist.isPresent()) {
            throw new BadRequestException("Username has already used");
        }
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
        save(user);


    }
    @Override
    public void save(User user) {
        if (user != null) {
            userRepository.save(user);
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public UserResponse login(LoginRequest request) {
        Optional<User> userOptional = validUsernameAndPassword(request.getUsername(), request.getPassword());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserResponse(userAuthenticationProvider.createToken(user.getUsername()));
        } else {
            throw new UnauthorizedException();
        }
    }

    @Override
    public Optional<User> validUsernameAndPassword(String username, String password) {
        return getUserByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

}
