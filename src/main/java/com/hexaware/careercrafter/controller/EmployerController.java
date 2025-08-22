package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.EmployerDto;
import com.hexaware.careercrafter.service.IEmployerService;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/employers")
@Tag(name = "Employers", description = "API endpoints for employer management")
public class EmployerController {

    private static final Logger logger = LoggerFactory.getLogger(EmployerController.class);

    @Autowired
    private IEmployerService employerService;

    @PreAuthorize("hasAuthority('employer')")
    @Operation(summary = "Create a new employer")
    @PostMapping("/create")
    public ResponseEntity<EmployerDto> createEmployer(@Valid @RequestBody EmployerDto employerDto) {
        logger.info("POST /api/employers/create - Creating employer for userId: {}", employerDto.getUserId());
        return ResponseEntity.ok(employerService.createEmployer(employerDto));
    }

    @PreAuthorize("hasAuthority('employer')")
    @Operation(summary = "Get employer details by ID")
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<EmployerDto> getEmployerById(@PathVariable int id) {
        logger.info("GET /api/employers/getbyid/{} - Fetching employer details", id);
        return ResponseEntity.ok(employerService.getEmployerById(id));
    }

    @PreAuthorize("hasAuthority('employer')")
    @Operation(summary = "Get all employers")
    @GetMapping("/getall")
    public ResponseEntity<List<EmployerDto>> getAllEmployers() {
        logger.info("GET /api/employers/getall - Fetching all employers");
        return ResponseEntity.ok(employerService.getAllEmployers());
    }

    @PreAuthorize("hasAuthority('employer')")
    @Operation(summary = "Update employer details by ID")
    @PutMapping("/update/{id}")
    public ResponseEntity<EmployerDto> updateEmployer(@PathVariable int id, @Valid @RequestBody EmployerDto employerDto) {
        logger.info("PUT /api/employers/update/{} - Updating employer", id);
        return ResponseEntity.ok(employerService.updateEmployer(id, employerDto));
    }

    @PreAuthorize("hasAuthority('employer')")
    @Operation(summary = "Delete employer by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployer(@PathVariable int id) {
        logger.info("DELETE /api/employers/delete/{} - Deleting employer", id);
        employerService.deleteEmployer(id);
        return ResponseEntity.ok("Employer deleted successfully");
    }
}
