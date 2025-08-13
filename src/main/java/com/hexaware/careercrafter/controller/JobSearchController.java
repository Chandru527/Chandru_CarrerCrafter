package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.JobListingDto;
import com.hexaware.careercrafter.dto.JobSearchDto;
import com.hexaware.careercrafter.service.IJobSearchService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; // 🔹 Logging
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobsearches")
public class JobSearchController {

    private static final Logger logger = LoggerFactory.getLogger(JobSearchController.class); // 🔹 Logger

    @Autowired
    private IJobSearchService jobSearchService;

    @PostMapping("/create")
    public JobSearchDto createSearch(@RequestBody @Valid JobSearchDto dto) {
        logger.info("POST /api/jobsearches/create - Creating search for JobSeekerId: {}", dto.getJobSeekerId());
        return jobSearchService.createSearch(dto);
    }

    @GetMapping("/{id}")
    public JobSearchDto getSearchById(@PathVariable int id) {
        logger.info("GET /api/jobsearches/{} - Fetching search", id);
        return jobSearchService.getSearchById(id);
    }

    @GetMapping("/jobseeker/{jobSeekerId}")
    public List<JobSearchDto> getSearchesByJobSeeker(@PathVariable int jobSeekerId) {
        logger.info("GET /api/jobsearches/jobseeker/{} - Fetching searches", jobSeekerId);
        return jobSearchService.getSearchesByJobSeeker(jobSeekerId);
    }

    @PutMapping("/update")
    public JobSearchDto updateSearch(@RequestBody @Valid JobSearchDto dto) {
        logger.info("PUT /api/jobsearches/update - Updating searchId: {}", dto.getSearchId());
        return jobSearchService.updateSearch(dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSearch(@PathVariable int id) {
        logger.info("DELETE /api/jobsearches/delete/{} - Deleting search", id);
        jobSearchService.deleteSearch(id);
    }

    @PostMapping("/recommend")
    public List<JobListingDto> recommendJobs(@RequestBody @Valid JobSearchDto dto) {
        logger.info("POST /api/jobsearches/recommend - Generating recommendations for keywords: {}", dto.getKeywords());
        return jobSearchService.recommendJobs(dto);
    }
}
