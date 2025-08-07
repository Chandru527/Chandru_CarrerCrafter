package com.hexaware.careercrafter.dto;

import jakarta.validation.constraints.*;

public class ApplicationDto {

    private int applicationId;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "Job Seeker ID is required")
    private int jobSeekerId;

    @NotNull(message = "Job Listing ID is required")
    private int jobListingId;

    // Getters & Setters
}
