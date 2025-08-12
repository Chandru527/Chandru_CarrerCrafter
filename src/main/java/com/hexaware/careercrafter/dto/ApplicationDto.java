package com.hexaware.careercrafter.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ApplicationDto {

    private int applicationId;

    @NotBlank(message = "Status is required")
    private String status;
    
    @NotNull(message = "Application date is required")
    private LocalDate applicationDate;

    @NotNull(message = "Job Seeker ID is required")
    private Integer jobSeekerId;
    
    @Size(max = 500, message = "File path must be less than 500 characters")
    private String filePath;


    @NotNull(message = "Job Listing ID is required")
    private Integer jobListingId;
}
