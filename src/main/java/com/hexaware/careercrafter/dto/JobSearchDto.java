package com.hexaware.careercrafter.dto;

import jakarta.validation.constraints.*;
import lombok.*;

/*
 * Author: Chandru
 * Date: 13-Aug-2025
 */


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobSearchDto {

    private int searchId;

    @NotNull(message = "Job Seeker ID is required")
    private Integer jobSeekerId;

    @NotBlank(message = "Keywords are required")
    @Size(min = 2, max = 100, message = "Keywords must be between 2 and 100 characters")
    private String keywords;

    @Size(max = 50, message = "Location cannot exceed 50 characters")
    private String location;

    @Size(max = 50, message = "Industry cannot exceed 50 characters")
    private String industry;

    @Size(max = 1000, message = "Recommended jobs list cannot exceed 1000 characters")
    private String recommendedJobs;

    private String searchDate;
}
