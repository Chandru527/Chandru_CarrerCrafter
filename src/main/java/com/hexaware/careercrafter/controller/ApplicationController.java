package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.ApplicationDto;
import com.hexaware.careercrafter.service.IApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private IApplicationService applicationService;

    @PostMapping("/create")
    public ResponseEntity<ApplicationDto> createApplication(@Valid @RequestBody ApplicationDto applicationDto) {
        return ResponseEntity.ok(applicationService.createApplication(applicationDto));
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<ApplicationDto> getApplicationById(@PathVariable int id) {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<ApplicationDto>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicationDto> updateApplication(@PathVariable int id, @Valid @RequestBody ApplicationDto applicationDto) {
        return ResponseEntity.ok(applicationService.updateApplication(id, applicationDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable int id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.ok("Application deleted successfully");
    }
}
