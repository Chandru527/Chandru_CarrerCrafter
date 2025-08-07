package com.hexaware.careercrafter.dto;

import jakarta.validation.constraints.*;

public class JobSeekerDto {

    private int jobSeekerId;

    @NotBlank(message = "Skills are required")
    @Size(min = 2, max = 255)
    private String skills;

    @NotNull(message = "User ID is required")
    private int userId;

    // Getters & Setters
}
