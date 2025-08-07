package com.hexaware.careercrafter.dto;

import jakarta.validation.constraints.*;

public class JobListingDto {

    private int jobListingId;

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100)
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 1000)
    private String description;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Employee ID is required")
    private int employeeId;

    // Getters & Setters
}
