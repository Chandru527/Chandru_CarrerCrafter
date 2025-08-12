package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.JobSeekerDto;
import com.hexaware.careercrafter.service.IJobSeekerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-seekers")
public class JobSeekerController {

    @Autowired
    private IJobSeekerService jobSeekerService;

    @PostMapping("/create")
    public ResponseEntity<JobSeekerDto> createJobSeeker(@Valid @RequestBody JobSeekerDto jobSeekerDto) {
        return ResponseEntity.ok(jobSeekerService.createJobSeeker(jobSeekerDto));
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<JobSeekerDto> getJobSeekerById(@PathVariable int id) {
        return ResponseEntity.ok(jobSeekerService.getJobSeekerById(id));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<JobSeekerDto>> getAllJobSeekers() {
        return ResponseEntity.ok(jobSeekerService.getAllJobSeekers());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<JobSeekerDto> updateJobSeeker(@PathVariable int id, @Valid @RequestBody JobSeekerDto jobSeekerDto) {
        return ResponseEntity.ok(jobSeekerService.updateJobSeeker(id, jobSeekerDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJobSeeker(@PathVariable int id) {
        jobSeekerService.deleteJobSeeker(id);
        return ResponseEntity.ok("Job seeker deleted successfully");
    }
}
