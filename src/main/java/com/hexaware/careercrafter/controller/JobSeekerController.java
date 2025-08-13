package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.JobSeekerDto;
import com.hexaware.careercrafter.service.IJobSeekerService;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-seekers")
public class JobSeekerController {

    private static final Logger logger = LoggerFactory.getLogger(JobSeekerController.class); // 🔹 Logger

    @Autowired
    private IJobSeekerService jobSeekerService;

    @PostMapping("/create")
    public ResponseEntity<JobSeekerDto> createJobSeeker(@Valid @RequestBody JobSeekerDto jobSeekerDto) {
        logger.info("POST /api/job-seekers/create - Request to create JobSeeker with name: {}", jobSeekerDto.getFullName());
        return ResponseEntity.ok(jobSeekerService.createJobSeeker(jobSeekerDto));
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<JobSeekerDto> getJobSeekerById(@PathVariable int id) {
        logger.info("GET /api/job-seekers/getbyid/{} - Fetch JobSeeker details", id);
        return ResponseEntity.ok(jobSeekerService.getJobSeekerById(id));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<JobSeekerDto>> getAllJobSeekers() {
        logger.info("GET /api/job-seekers/getall - Fetch all JobSeekers");
        return ResponseEntity.ok(jobSeekerService.getAllJobSeekers());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<JobSeekerDto> updateJobSeeker(@PathVariable int id, @Valid @RequestBody JobSeekerDto jobSeekerDto) {
        logger.info("PUT /api/job-seekers/update/{} - Update JobSeeker", id);
        return ResponseEntity.ok(jobSeekerService.updateJobSeeker(id, jobSeekerDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJobSeeker(@PathVariable int id) {
        logger.info("DELETE /api/job-seekers/delete/{} - Delete JobSeeker", id);
        jobSeekerService.deleteJobSeeker(id);
        return ResponseEntity.ok("Job seeker deleted successfully");
    }
}
