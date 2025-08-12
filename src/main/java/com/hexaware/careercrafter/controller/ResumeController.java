package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.ResumeDto;
import com.hexaware.careercrafter.service.IResumeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    @Autowired
    private IResumeService resumeService;

    @PostMapping("/create")
    public ResponseEntity<ResumeDto> createResume(@Valid @RequestBody ResumeDto resumeDto) {
        return ResponseEntity.ok(resumeService.createResume(resumeDto));
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<ResumeDto> getResumeById(@PathVariable int id) {
        return ResponseEntity.ok(resumeService.getResumeById(id));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<ResumeDto>> getAllResumes() {
        return ResponseEntity.ok(resumeService.getAllResumes());
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ResumeDto> updateResume(@PathVariable int id, @Valid @RequestBody ResumeDto resumeDto) {
        return ResponseEntity.ok(resumeService.updateResume(id, resumeDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteResume(@PathVariable int id) {
        resumeService.deleteResume(id);
        return ResponseEntity.ok("Resume deleted successfully");
    }
}
