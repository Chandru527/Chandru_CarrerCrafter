package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.JobListingDto;
import com.hexaware.careercrafter.dto.JobSearchDto;
import com.hexaware.careercrafter.service.IJobSearchService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobsearches")
public class JobSearchController {

    @Autowired
    private IJobSearchService jobSearchService;

    
    @PostMapping("/create")
    public JobSearchDto createSearch(@RequestBody @Valid JobSearchDto dto) {
        return jobSearchService.createSearch(dto);
    }

    
    @GetMapping("/{id}")
    public JobSearchDto getSearchById(@PathVariable int id) {
        return jobSearchService.getSearchById(id);
    }

    
    @GetMapping("/jobseeker/{jobSeekerId}")
    public List<JobSearchDto> getSearchesByJobSeeker(@PathVariable int jobSeekerId) {
        return jobSearchService.getSearchesByJobSeeker(jobSeekerId);
    }

   
    @PutMapping("/update")
    public JobSearchDto updateSearch(@RequestBody @Valid JobSearchDto dto) {
        return jobSearchService.updateSearch(dto);
    }

    
    @DeleteMapping("/delete/{id}")
    public void deleteSearch(@PathVariable int id) {
        jobSearchService.deleteSearch(id);
    }

   
    @PostMapping("/recommend")
    public List<JobListingDto> recommendJobs(@RequestBody @Valid JobSearchDto dto) {
        return jobSearchService.recommendJobs(dto);
    }
}
