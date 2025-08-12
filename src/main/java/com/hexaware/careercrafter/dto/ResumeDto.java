package com.hexaware.careercrafter.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResumeDto {

    private Integer resumeId;

    @NotBlank(message = "File path is required")
    private String filePath;

    @NotNull(message = "Upload date is required")
    private LocalDate uploadDate;

    @NotNull(message = "Job seeker ID is required")
    private Integer jobSeekerId;
}
