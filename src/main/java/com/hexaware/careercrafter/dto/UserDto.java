package com.hexaware.careercrafter.dto;

import jakarta.validation.constraints.*;

public class UserDto {

    private int userId;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50)
    private String name;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(employee|job_seeker)$", message = "Role must be 'employee' or 'job_seeker'")
    private String role;

    // Getters & Setters
}
