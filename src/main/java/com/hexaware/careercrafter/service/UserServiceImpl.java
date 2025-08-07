package com.hexaware.careercrafter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careercrafter.entities.User;
import com.hexaware.careercrafter.exception.DuplicateResourceException;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IUserRepo;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepo userRepo;

    @Override
    public User saveUser(User user) {
        // Validate email
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidRequestException("Email is required");
        }

        // Validate password
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new InvalidRequestException("Password is required");
        }

        // Check for duplicate email
        if (userRepo.findByEmail(user.getEmail()) != null) {
            throw new DuplicateResourceException("Email already registered: " + user.getEmail());
        }

        return userRepo.save(user);
    }

    @Override
    public User getUserById(int id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public void deleteUser(int id) {
        if (!userRepo.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepo.deleteById(id);
    }
}
