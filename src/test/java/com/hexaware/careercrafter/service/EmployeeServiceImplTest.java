package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.EmployeeDto;
import com.hexaware.careercrafter.entities.Employee;
import com.hexaware.careercrafter.entities.User;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IEmployeeRepo;
import com.hexaware.careercrafter.repository.IUserRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private IEmployeeRepo employeeRepo;
    @Mock
    private IUserRepo userRepo;

    @InjectMocks
    private EmployeeServiceImpl service;

    private User user;
    private Employee employee;
    private EmployeeDto dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1);

        dto = new EmployeeDto();
        dto.setCompanyName("ABC Corp");
        dto.setCompanyDescription("Software Company");
        dto.setUserId(1);

        employee = new Employee();
        employee.setEmployeeId(1);
        employee.setCompanyName("ABC Corp");
        employee.setUser(user);
    }

    @Test
    void testCreateEmployee_Success() {
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(employeeRepo.save(any(Employee.class))).thenReturn(employee);
        EmployeeDto result = service.createEmployee(dto);
        assertEquals("ABC Corp", result.getCompanyName());
    }

    @Test
    void testCreateEmployee_MissingCompanyName_ThrowsException() {
        dto.setCompanyName("");
        assertThrows(InvalidRequestException.class, () -> service.createEmployee(dto));
    }

    @Test
    void testGetEmployeeById_Success() {
        when(employeeRepo.findById(1)).thenReturn(Optional.of(employee));
        EmployeeDto result = service.getEmployeeById(1);
        assertEquals("ABC Corp", result.getCompanyName());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getEmployeeById(1));
    }

    @Test
    void testGetAllEmployees() {
        when(employeeRepo.findAll()).thenReturn(Arrays.asList(employee));
        List<EmployeeDto> result = service.getAllEmployees();
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateEmployee_Success() {
        when(employeeRepo.findById(1)).thenReturn(Optional.of(employee));
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(employeeRepo.save(any(Employee.class))).thenReturn(employee);

        dto.setPosition("Manager");
        EmployeeDto result = service.updateEmployee(1, dto);
        assertEquals("Manager", dto.getPosition());
    }

    @Test
    void testDeleteEmployee_Success() {
        when(employeeRepo.existsById(1)).thenReturn(true);
        service.deleteEmployee(1);
        verify(employeeRepo).deleteById(1);
    }

    @Test
    void testDeleteEmployee_NotFound() {
        when(employeeRepo.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.deleteEmployee(1));
    }
}
