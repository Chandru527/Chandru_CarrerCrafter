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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/resumes")
@Tag(name = "Resumes", description = "APIs for resume upload, update, retrieval, and deletion")
public class ResumeController {

    private static final Logger logger = LoggerFactory.getLogger(ResumeController.class);

    @Autowired
    private IResumeService resumeService;

    @PreAuthorize("hasAuthority('job_seeker')")
    @Operation(summary = "Upload a resume file for a job seeker")
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<ResumeDto> uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("jobSeekerId") Integer jobSeekerId) {
        logger.info("Uploading resume file for JobSeekerId: {}", jobSeekerId);
        return ResponseEntity.ok(resumeService.uploadResume(file, jobSeekerId));
    }
    
    @PreAuthorize("hasAnyAuthority('employer', 'job_seeker')")
    @Operation(summary = "Get resume metadata by resume ID")
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<ResumeDto> getResumeById(@PathVariable int id) {
        logger.info("GET /api/resumes/getbyid/{} - Fetching resume", id);
        return ResponseEntity.ok(resumeService.getResumeById(id));
    }

    @PreAuthorize("hasAuthority('employer')")
    @Operation(summary = "Get all resumes")
    @GetMapping("/getall")
    public ResponseEntity<List<ResumeDto>> getAllResumes() {
        logger.info("GET /api/resumes/getall - Fetching all resumes");
        return ResponseEntity.ok(resumeService.getAllResumes());
    }

    @PreAuthorize("hasAuthority('job_seeker')")
    @Operation(summary = "Update a resume by ID")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResumeDto> updateResume(@PathVariable int id, @Valid @RequestBody ResumeDto resumeDto) {
        logger.info("PUT /api/resumes/update/{} - Updating resume", id);
        return ResponseEntity.ok(resumeService.updateResume(id, resumeDto));
    }

    @PreAuthorize("hasAuthority('job_seeker')")
    @Operation(summary = "Delete a resume by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteResume(@PathVariable int id) {
        logger.info("DELETE /api/resumes/delete/{} - Deleting resume", id);
        resumeService.deleteResume(id);
        return ResponseEntity.ok("Resume deleted successfully");
    }
}
