package com.example.candidate_test_specification.web.controllers;

import com.example.candidate_test_specification.domain.exceptions.UnauthorizedException;
import com.example.candidate_test_specification.services.UserService;
import com.example.candidate_test_specification.web.controllers.user.LoginRequest;
import com.example.candidate_test_specification.web.controllers.user.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testRegister_success() throws Exception {

        UserDto userDto = UserDto.builder()
                .username("vadim")
                .password("1111")
                .build();

        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User was added."));
    }
    @Test
    public void testRegister_fail_empty_password() throws Exception {
        UserDto userDto = UserDto.builder()
                .username("vadim")
                .password("")
                .build();

        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testRegister_fail_username_is_too_long() throws Exception {
        UserDto userDto = UserDto.builder()
                .username("vvvvvvvvvvvvvvvvaaaaaaaaaaaaaaaaaaaaaaaaaaaddddddddddddddddiiiiiiiiiiiiiiiiiiiiimmmmmmmmmmmmmmm")
                .password("11111")
                .build();

        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testRegister_fail_password_is_too_short() throws Exception {
        UserDto userDto = UserDto.builder()
                .username("vadim")
                .password("11")
                .build();

        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testAuthenticate_success() throws Exception {
        LoginRequest loginRequest=LoginRequest.builder()
                .username("vadim")
                .password("1111")
                .build();

        mockMvc.perform(post("/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginRequest)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testAuthenticate_fail_wrong_password() throws Exception {
        LoginRequest loginRequest=LoginRequest.builder()
                .username("vadim")
                .password("1111UYUHD")
                .build();
        when(userService.login(loginRequest)).thenThrow(new UnauthorizedException());
        mockMvc.perform(post("/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginRequest)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
    @Test
    public void testAuthenticate_fail_empty_username() throws Exception {
        LoginRequest loginRequest=LoginRequest.builder()
                .username("")
                .password("1111")
                .build();
        mockMvc.perform(post("/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginRequest)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
