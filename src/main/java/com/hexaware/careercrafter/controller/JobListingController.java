package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.JobListingDto;
import com.hexaware.careercrafter.service.IJobListingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-listings")
public class JobListingController {

    @Autowired
    private IJobListingService jobListingService;

    @PostMapping("/create")
    public ResponseEntity<JobListingDto> createJobListing(@Valid @RequestBody JobListingDto jobListingDto) {
        return ResponseEntity.ok(jobListingService.createJobListing(jobListingDto));
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<JobListingDto> getJobListingById(@PathVariable int id) {
        return ResponseEntity.ok(jobListingService.getJobListingById(id));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<JobListingDto>> getAllJobListings() {
        return ResponseEntity.ok(jobListingService.getAllJobListings());
    }

   

    @PutMapping("/update/{id}")
    public ResponseEntity<JobListingDto> updateJobListing(@PathVariable int id, @Valid @RequestBody JobListingDto jobListingDto) {
        return ResponseEntity.ok(jobListingService.updateJobListing(id, jobListingDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJobListing(@PathVariable int id) {
        jobListingService.deleteJobListing(id);
        return ResponseEntity.ok("Job listing deleted successfully");
    }
}
