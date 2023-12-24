package com.example.candidate_test_specification.web.controllers.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @NotBlank(message = "Username should not be empty!")
    @Size(max = 30)
    private String username;

    @NotBlank(message = "Password should not be empty!")
    @Size(min = 4)
    private String password;
}
