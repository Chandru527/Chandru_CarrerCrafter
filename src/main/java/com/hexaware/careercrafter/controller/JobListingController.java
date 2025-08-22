package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.JobListingDto;
import com.hexaware.careercrafter.service.IJobListingService;
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
@RequestMapping("/api/job-listings")
@Tag(name = "Job Listings", description = "API endpoints for job listings management")
public class JobListingController {

    private static final Logger logger = LoggerFactory.getLogger(JobListingController.class); 

    @Autowired
    private IJobListingService jobListingService;

    @PreAuthorize("hasAnyAuthority('employer')")
    @Operation(summary = "Create a new job listing")
    @PostMapping("/create")
    public ResponseEntity<JobListingDto> createJobListing(@Valid @RequestBody JobListingDto jobListingDto) {
        logger.info("POST /api/job-listings/create - Creating JobListing with title: {}", jobListingDto.getTitle());
        return ResponseEntity.ok(jobListingService.createJobListing(jobListingDto));
    }

    @PreAuthorize("hasAuthority('employer')")
    @Operation(summary = "Get job listing by ID")
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<JobListingDto> getJobListingById(@PathVariable int id) {
        logger.info("GET /api/job-listings/getbyid/{} - Fetch job listing", id);
        return ResponseEntity.ok(jobListingService.getJobListingById(id));
    }

    @PreAuthorize("hasAnyAuthority('employer', 'job_seeker')")
    @Operation(summary = "Get all job listings")
    @GetMapping("/getall")
    public ResponseEntity<List<JobListingDto>> getAllJobListings() {
        logger.info("GET /api/job-listings/getall - Fetch all job listings");
        return ResponseEntity.ok(jobListingService.getAllJobListings());
    }

    @PreAuthorize("hasAuthority('employer')")
    @Operation(summary = "Update job listing by ID")
    @PutMapping("/update/{id}")
    public ResponseEntity<JobListingDto> updateJobListing(@PathVariable int id, @Valid @RequestBody JobListingDto jobListingDto) {
        logger.info("PUT /api/job-listings/update/{} - Updating job listing", id);
        return ResponseEntity.ok(jobListingService.updateJobListing(id, jobListingDto));
    }

    @PreAuthorize("hasAuthority('employer')")
    @Operation(summary = "Delete job listing by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJobListing(@PathVariable int id) {
        logger.info("DELETE /api/job-listings/delete/{} - Deleting job listing", id);
        jobListingService.deleteJobListing(id);
        return ResponseEntity.ok("Job listing deleted successfully");
    }
}
