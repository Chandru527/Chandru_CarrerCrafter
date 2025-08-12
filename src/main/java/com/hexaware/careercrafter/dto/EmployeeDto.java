package com.hexaware.careercrafter.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EmployeeDto {

    private int employeeId;

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
    private String companyName;
    
    @NotBlank(message = "Company description is required")
    private String companyDescription;

    @NotBlank(message = "Position is required")
    private String position;

    @NotNull(message = "User ID is required")
    private Integer userId;
}
