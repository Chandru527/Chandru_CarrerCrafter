package com.hexaware.careercrafter.dto;

import jakarta.validation.constraints.*;

import lombok.Data;

/*
 * Author: Chandru
 * Date: 13-Aug-2025
 */

@Data
public class UserDto {

    private int userId;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50)
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(employer|job_seeker)$", message = "Role must be 'employer' or 'job_seeker'")
    private String role;
}
