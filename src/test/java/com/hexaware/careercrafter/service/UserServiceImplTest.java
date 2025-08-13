package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.UserDto;
import com.hexaware.careercrafter.entities.User;
import com.hexaware.careercrafter.exception.DuplicateResourceException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IUserRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock private IUserRepo userRepo;
    @InjectMocks private UserServiceImpl service;

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
    void createUser_success() {
        when(userRepo.findByEmail("john@example.com")).thenReturn(null);
        when(userRepo.save(any(User.class))).thenReturn(user);
        assertEquals("John", service.createUser(dto).getName());
    }

    @Test
    void createUser_duplicateEmail() {
        when(userRepo.findByEmail("john@example.com")).thenReturn(user);
        assertThrows(DuplicateResourceException.class, () -> service.createUser(dto));
    }

    @Test
    void getUserById_success() {
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        assertEquals("John", service.getUserById(1).getName());
    }

    @Test
    void getUserById_notFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getUserById(1));
    }

    @Test
    void getAllUsers() {
        when(userRepo.findAll()).thenReturn(Arrays.asList(user));
        assertEquals(1, service.getAllUsers().size());
    }

    @Test
    void updateUser_success() {
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(userRepo.findByEmail("john@example.com")).thenReturn(null);
        when(userRepo.save(any(User.class))).thenReturn(user);

        dto.setEmail("john@example.com");
        assertEquals("John", service.updateUser(1, dto).getName());
    }

    @Test
    void deleteUser_success() {
        when(userRepo.existsById(1)).thenReturn(true);
        service.deleteUser(1);
        verify(userRepo).deleteById(1);
    }

    @Test
    void deleteUser_notFound() {
        when(userRepo.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.deleteUser(1));
    }
}
