package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.JobListingDto;
import com.hexaware.careercrafter.dto.JobSearchDto;
import com.hexaware.careercrafter.service.IJobSearchService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/jobsearches")
@Tag(name = "Job Searches", description = "API endpoints for job search management and recommendations")
public class JobSearchController {

    private static final Logger logger = LoggerFactory.getLogger(JobSearchController.class); // 🔹 Logger

    @Autowired
    private IJobSearchService jobSearchService;
    
    @PreAuthorize("hasAnyAuthority('employer', 'job_seeker')")
    @Operation(summary = "Create a new job search entry")
    @PostMapping("/create")
    public JobSearchDto createSearch(@RequestBody @Valid JobSearchDto dto) {
        logger.info("POST /api/jobsearches/create - Creating search for JobSeekerId: {}", dto.getJobSeekerId());
        return jobSearchService.createSearch(dto);
    }
    
    @PreAuthorize("hasAnyAuthority('employer', 'job_seeker')")
    @Operation(summary = "Fetch a job search by its ID")
    @GetMapping("/{id}")
    public JobSearchDto getSearchById(@PathVariable int id) {
        logger.info("GET /api/jobsearches/{} - Fetching search", id);
        return jobSearchService.getSearchById(id);
    }
    
    @PreAuthorize("hasAnyAuthority('employer', 'job_seeker')")
    @Operation(summary = "Fetch job searches by Job Seeker ID")
    @GetMapping("/jobseeker/{jobSeekerId}")
    public List<JobSearchDto> getSearchesByJobSeeker(@PathVariable int jobSeekerId) {
        logger.info("GET /api/jobsearches/jobseeker/{} - Fetching searches", jobSeekerId);
        return jobSearchService.getSearchesByJobSeeker(jobSeekerId);
    }

    @PreAuthorize("hasAnyAuthority('employer', 'job_seeker')")
    @Operation(summary = "Update an existing job search entry")
    @PutMapping("/update")
    public JobSearchDto updateSearch(@RequestBody @Valid JobSearchDto dto) {
        logger.info("PUT /api/jobsearches/update - Updating searchId: {}", dto.getSearchId());
        return jobSearchService.updateSearch(dto);
    }
    
    @PreAuthorize("hasAuthority('employer')")
    @Operation(summary = "Delete a job search by its ID")
    @DeleteMapping("/delete/{id}")
    public void deleteSearch(@PathVariable int id) {
        logger.info("DELETE /api/jobsearches/delete/{} - Deleting search", id);
        jobSearchService.deleteSearch(id);
    }

    @PreAuthorize("hasAnyAuthority('employer', 'job_seeker')")
    @Operation(summary = "Get recommended jobs based on job search keywords")
    @PostMapping("/recommend")
    public List<JobListingDto> recommendJobs(@RequestBody @Valid JobSearchDto dto) {
        logger.info("POST /api/jobsearches/recommend - Generating recommendations for keywords: {}", dto.getKeywords());
        return jobSearchService.recommendJobs(dto);
    }
}
