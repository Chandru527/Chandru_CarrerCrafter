package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.ApplicationDto;
import com.hexaware.careercrafter.service.IApplicationService;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/*
 * Author: Chandru
 * Date: 13-Aug-2025
 */

@RestController
@RequestMapping("/api/applications")
@Tag(name = "Applications", description = "API endpoints for job applications")
public class ApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class); // 🔹 Logger

    @Autowired
    private IApplicationService applicationService;

    @PreAuthorize("hasAnyAuthority('job_seeker')")
    @Operation(summary = "Create an application for a job listing")
    @PostMapping("/apply")
    public ResponseEntity<ApplicationDto> createApplication(@Valid @RequestBody ApplicationDto applicationDto) {
        logger.info("POST /api/applications/create - Creating application for JobSeekerId: {} and JobListingId: {}",
                applicationDto.getJobSeekerId(), applicationDto.getJobListingId());
        return ResponseEntity.ok(applicationService.createApplication(applicationDto));
    }

    @PreAuthorize("hasAnyAuthority('employer', 'job_seeker')")
    @Operation(summary = "Get an application by its ID")
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<ApplicationDto> getApplicationById(@PathVariable int id) {
        logger.info("GET /api/applications/getbyid/{} - Fetching application", id);
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @PreAuthorize("hasAuthority('employer')")
    @Operation(summary = "Get all applications")
    @GetMapping("/getall")
    public ResponseEntity<List<ApplicationDto>> getAllApplications() {
        logger.info("GET /api/applications/getall - Fetching all applications");
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @PreAuthorize("hasAnyAuthority('employer', 'job_seeker')")
    @Operation(summary = "Update an application by its ID")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicationDto> updateApplication(@PathVariable int id,
                                                             @Valid @RequestBody ApplicationDto applicationDto) {
        logger.info("PUT /api/applications/update/{} - Updating application", id);
        return ResponseEntity.ok(applicationService.updateApplication(id, applicationDto));
    }

    @PreAuthorize("hasAuthority('employer')")
    @Operation(summary = "Delete an application by its ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable int id) {
        logger.info("DELETE /api/applications/delete/{} - Deleting application", id);
        applicationService.deleteApplication(id);
        return ResponseEntity.ok("Application deleted successfully");
    }

    @PreAuthorize("hasAnyAuthority('employer', 'job_seeker')")
    @Operation(summary = "Get applications by job seeker ID")
    @GetMapping("/seeker/{jobSeekerId}")
    public ResponseEntity<List<ApplicationDto>> getApplicationsByJobSeekerId(@PathVariable int jobSeekerId) {
        logger.info("GET /api/applications/seeker/{} - Fetching applications", jobSeekerId);
        return ResponseEntity.ok(applicationService.getApplicationsByJobSeekerId(jobSeekerId));
    }
}
