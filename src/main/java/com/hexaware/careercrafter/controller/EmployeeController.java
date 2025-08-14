package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.EmployeeDto;
import com.hexaware.careercrafter.service.IEmployeeService;
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
@RequestMapping("/api/employees")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class); // 🔹 Logger

    @Autowired
    private IEmployeeService employeeService;

   // @PreAuthorize("hasRole('employee')")
    @PostMapping("/create")
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        logger.info("POST /api/employees/create - Creating employee for userId: {}", employeeDto.getUserId());
        return ResponseEntity.ok(employeeService.createEmployee(employeeDto));
    }

   // @PreAuthorize("hasRole('employee')")
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable int id) {
        logger.info("GET /api/employees/getbyid/{} - Fetching employee details", id);
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

   // @PreAuthorize("hasRole('employee')")
    @GetMapping("/getall")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        logger.info("GET /api/employees/getall - Fetching all employees");
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

   // @PreAuthorize("hasRole('employee')")
    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable int id, @Valid @RequestBody EmployeeDto employeeDto) {
        logger.info("PUT /api/employees/update/{} - Updating employee", id);
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDto));
    }

   // @PreAuthorize("hasRole('employee')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        logger.info("DELETE /api/employees/delete/{} - Deleting employee", id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}
