package com.hexaware.careercrafter.service;

/*
 * 
 * Author: Chandru
 * Date: 13-Aug-2025
 * 
 * 
 */

import com.hexaware.careercrafter.dto.UserDto;
import com.hexaware.careercrafter.entities.User;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IUserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private IUserRepo userRepo;

    @InjectMocks
    private UserServiceImpl service;

    private User user;
    private UserDto dto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1);
        user.setName("John");
        user.setEmail("john@example.com");

        dto = new UserDto();
        dto.setName("John");
        dto.setEmail("john@example.com");
    }

    @Test
    void testCreateUser_Success() {
        when(userRepo.findByEmail("john@example.com")).thenReturn(null);
        when(userRepo.save(any(User.class))).thenReturn(user);

        UserDto result = service.createUser(dto);

        assertEquals("John", result.getName());
        verify(userRepo).save(any(User.class));
    }

    @Test
    void testGetUserById_Success() {
        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        UserDto result = service.getUserById(1);

        assertEquals("John", result.getName());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getUserById(1));
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepo.existsById(1)).thenReturn(true);

        service.deleteUser(1);

        verify(userRepo).deleteById(1);
    }
}
