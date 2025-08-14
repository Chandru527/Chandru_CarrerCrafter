package com.hexaware.careercrafter.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

/*
 * Author: Chandru
 * Date: 13-Aug-2025
 */


@Data
public class JobSeekerDto {

    private int jobSeekerId;

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;

    @NotBlank(message = "Education details are required")
    private String education;

    @NotBlank(message = "Experience details are required")
    private String experience;
    
    @NotBlank(message = "Skills are required")
    private String skills;

    @NotNull(message = "User ID is required")
    private Integer userId;
}
