package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.ResumeDto;
import com.hexaware.careercrafter.service.IResumeService;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Author: Chandru
 * Date: 13-Aug-2025
 */


@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private static final Logger logger = LoggerFactory.getLogger(ResumeController.class); // 🔹 Logger

    @Autowired
    private IResumeService resumeService;

    @PreAuthorize("hasRole('job_seeker')")
    @PostMapping("/create")
    public ResponseEntity<ResumeDto> createResume(@Valid @RequestBody ResumeDto resumeDto) {
        logger.info("POST /api/resumes/create - Creating resume for JobSeekerId: {}", resumeDto.getJobSeekerId());
        return ResponseEntity.ok(resumeService.createResume(resumeDto));
    }

    @PreAuthorize("hasAnyRole('employer','job_seeker')")
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<ResumeDto> getResumeById(@PathVariable int id) {
        logger.info("GET /api/resumes/getbyid/{} - Fetching resume", id);
        return ResponseEntity.ok(resumeService.getResumeById(id));
    }

    @PreAuthorize("hasRole('employer')")
    @GetMapping("/getall")
    public ResponseEntity<List<ResumeDto>> getAllResumes() {
        logger.info("GET /api/resumes/getall - Fetching all resumes");
        return ResponseEntity.ok(resumeService.getAllResumes());
    }

    @PreAuthorize("hasRole('employer')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResumeDto> updateResume(@PathVariable int id, @Valid @RequestBody ResumeDto resumeDto) {
        logger.info("PUT /api/resumes/update/{} - Updating resume", id);
        return ResponseEntity.ok(resumeService.updateResume(id, resumeDto));
    }

    @PreAuthorize("hasRole('employer')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteResume(@PathVariable int id) {
        logger.info("DELETE /api/resumes/delete/{} - Deleting resume", id);
        resumeService.deleteResume(id);
        return ResponseEntity.ok("Resume deleted successfully");
    }
}
