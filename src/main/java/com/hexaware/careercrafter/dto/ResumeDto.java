package com.hexaware.careercrafter.dto;

import jakarta.validation.constraints.*;

public class ResumeDto {

    private int resumeId;

    @NotBlank(message = "File path is required")
    private String filePath;

    @NotNull(message = "Job Seeker ID is required")
    private int jobSeekerId;

    // Getters & Setters
}
