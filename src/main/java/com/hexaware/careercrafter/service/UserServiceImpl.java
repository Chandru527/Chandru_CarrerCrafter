package com.hexaware.careercrafter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careercrafter.dto.UserDto;
import com.hexaware.careercrafter.entities.User;
import com.hexaware.careercrafter.exception.DuplicateResourceException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IUserRepo;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class); // 🔹 Logger

    @Autowired
    private IUserRepo userRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        logger.info("Creating new user with email: {}", userDto.getEmail());
        if (userRepo.findByEmail(userDto.getEmail()) != null) {
            logger.warn("Duplicate email detected: {}", userDto.getEmail());
            throw new DuplicateResourceException("Email already registered: " + userDto.getEmail());
        }
        User savedUser = userRepo.save(mapToEntity(userDto));
        logger.debug("User saved: {}", savedUser);
        return mapToDto(savedUser);
    }

    @Override
    public UserDto getUserById(int id) {
        logger.info("Fetching user by id: {}", id);
        User user = userRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", id);
                    return new ResourceNotFoundException("User not found with id: " + id);
                });
        return mapToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        logger.info("Fetching all users");
        List<UserDto> users = userRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        logger.debug("Found {} users", users.size());
        return users;
    }

    @Override
    public UserDto updateUser(int id, UserDto userDto) {
        logger.info("Updating user with id: {}", id);
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", id);
                    return new ResourceNotFoundException("User not found with id: " + id);
                });

        if (!existingUser.getEmail().equals(userDto.getEmail()) &&
            userRepo.findByEmail(userDto.getEmail()) != null) {
            logger.warn("Duplicate email during update: {}", userDto.getEmail());
            throw new DuplicateResourceException("Email already registered: " + userDto.getEmail());
        }

        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPassword(userDto.getPassword());
        existingUser.setRole(userDto.getRole());

        User updatedUser = userRepo.save(existingUser);
        logger.debug("User updated successfully: {}", updatedUser);
        return mapToDto(updatedUser);
    }

    @Override
    public void deleteUser(int id) {
        logger.info("Deleting user with id: {}", id);
        if (!userRepo.existsById(id)) {
            logger.error("User not found with id: {}", id);
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepo.deleteById(id);
        logger.info("User deleted successfully with id: {}", id);
    }

    private UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        return dto;
    }

    private User mapToEntity(UserDto dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        return user;
    }
}
