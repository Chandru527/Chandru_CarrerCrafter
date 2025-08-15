package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.UserDto;
import com.hexaware.careercrafter.service.IUserService;
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
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class); // 🔹 Logger

    @Autowired
    private IUserService userService;

    @PreAuthorize("hasAnyRole('employer', 'job_seeker')")
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        logger.info("POST /api/users/create - Request to create user with email: {}", userDto.getEmail());
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @PreAuthorize("hasAnyRole('employer', 'job_seeker')")
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id) {
        logger.info("GET /api/users/getbyid/{} - Fetch user", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PreAuthorize("hasRole('employer')")
    @GetMapping("/getall")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        logger.info("GET /api/users/getall - Fetch all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasAnyRole('employer', 'job_seeker')")
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id, @Valid @RequestBody UserDto userDto) {
        logger.info("PUT /api/users/update/{} - Update user", id);
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @PreAuthorize("hasAnyRole('employer', 'job_seeker')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        logger.info("DELETE /api/users/delete/{} - Delete user", id);
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
