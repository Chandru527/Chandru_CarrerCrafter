package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.entities.User;
import com.hexaware.careercrafter.repository.IUserRepo;
import com.hexaware.careercrafter.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginData) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginData.getEmail(), loginData.getPassword())
            );
            String token = jwtUtil.generateToken(loginData.getEmail());
            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userRepo.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already registered!");
        }
        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

}
