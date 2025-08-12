package com.hexaware.careercrafter.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class JobListingDto {

    private int jobListingId;

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100)
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 1000)
    private String description;
    
    @NotBlank(message = "Qualifications are required")
    private String qualifications;


    @NotBlank(message = "Location is required")
    private String location;
    
    @Positive(message = "Salary must be positive")
    private double salary;
    
    @NotNull(message = "Posted date is required")
    private LocalDate postedDate;

    @NotNull(message = "Employee ID is required")
    private Integer employeeId;
}
