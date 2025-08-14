package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.EmployerDto;
import com.hexaware.careercrafter.entities.Employer;
import com.hexaware.careercrafter.entities.User;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IEmployerRepo;
import com.hexaware.careercrafter.repository.IUserRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EmployerServiceImplTest {

    @Mock
    private IEmployerRepo employerRepo;

    @Mock
    private IUserRepo userRepo;

    @InjectMocks
    private EmployerServiceImpl service;

    private User user;
    private Employer employer;
    private EmployerDto dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        user = new User();
        user.setUserId(1);

        dto = new EmployerDto();
        dto.setCompanyName("ABC Corp");
        dto.setUserId(1);

        employer = new Employer();
        employer.setEmployerId(1);
        employer.setCompanyName("ABC Corp");
        employer.setUser(user);
    }

    @Test
    void testGetEmployerById_Success() {
        when(employerRepo.findById(1)).thenReturn(Optional.of(employer));

        EmployerDto result = service.getEmployerById(1);

        assertEquals("ABC Corp", result.getCompanyName());
    }

    @Test
    void testGetEmployerById_NotFound() {
        when(employerRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getEmployerById(1));
    }

    @Test
    void testDeleteEmployer_Success() {
        when(employerRepo.existsById(1)).thenReturn(true);

        service.deleteEmployer(1);

        verify(employerRepo).deleteById(1);
    }
}
