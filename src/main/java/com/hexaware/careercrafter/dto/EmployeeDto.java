package com.hexaware.careercrafter.dto;

import jakarta.validation.constraints.*;

public class EmployeeDto {

    private int employeeId;

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 100)
    private String companyName;

    @NotBlank(message = "Position is required")
    private String position;

    @NotNull(message = "User ID is required")
    private int userId;

    // Getters & Setters
}
