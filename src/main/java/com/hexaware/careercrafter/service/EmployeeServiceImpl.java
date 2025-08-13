package com.hexaware.careercrafter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careercrafter.dto.EmployeeDto;
import com.hexaware.careercrafter.entities.Employee;
import com.hexaware.careercrafter.entities.User;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IEmployeeRepo;
import com.hexaware.careercrafter.repository.IUserRepo;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class); // 🔹 Logger

    @Autowired
    private IEmployeeRepo employeeRepo;

    @Autowired
    private IUserRepo userRepo;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        logger.info("Creating new employee for userId: {}", employeeDto.getUserId());

        if (employeeDto.getCompanyName() == null || employeeDto.getCompanyName().isBlank()) {
            logger.warn("Company name is missing");
            throw new InvalidRequestException("Company name is required");
        }
        if (employeeDto.getCompanyDescription() == null || employeeDto.getCompanyDescription().isBlank()) {
            logger.warn("Company description is missing");
            throw new InvalidRequestException("Company description is required");
        }
        if (employeeDto.getUserId() == null) {
            logger.warn("User ID is missing");
            throw new InvalidRequestException("User ID is required");
        }

        User user = userRepo.findById(employeeDto.getUserId())
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", employeeDto.getUserId());
                    return new ResourceNotFoundException("User not found with id: " + employeeDto.getUserId());
                });

        Employee savedEmployee = employeeRepo.save(mapToEntity(employeeDto, user));
        logger.debug("Employee saved successfully: {}", savedEmployee);
        return mapToDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(int id) {
        logger.info("Fetching employee with id: {}", id);
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                });
        return mapToDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        logger.info("Fetching all employees");
        List<EmployeeDto> employees = employeeRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        logger.debug("Total employees found: {}", employees.size());
        return employees;
    }

    @Override
    public EmployeeDto updateEmployee(int id, EmployeeDto employeeDto) {
        logger.info("Updating employee with id: {}", id);

        Employee existingEmployee = employeeRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                });

        existingEmployee.setCompanyName(employeeDto.getCompanyName());
        existingEmployee.setCompanyDescription(employeeDto.getCompanyDescription());
        existingEmployee.setPosition(employeeDto.getPosition());

        if (employeeDto.getUserId() != null) {
            User user = userRepo.findById(employeeDto.getUserId())
                    .orElseThrow(() -> {
                        logger.error("User not found with id: {}", employeeDto.getUserId());
                        return new ResourceNotFoundException("User not found with id: " + employeeDto.getUserId());
                    });
            existingEmployee.setUser(user);
        }

        Employee updatedEmployee = employeeRepo.save(existingEmployee);
        logger.debug("Employee updated successfully: {}", updatedEmployee);
        return mapToDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(int id) {
        logger.info("Deleting employee with id: {}", id);
        if (!employeeRepo.existsById(id)) {
            logger.error("Employee not found with id: {}", id);
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        employeeRepo.deleteById(id);
        logger.info("Employee deleted successfully with id: {}", id);
    }

    private EmployeeDto mapToDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setCompanyName(employee.getCompanyName());
        dto.setCompanyDescription(employee.getCompanyDescription());
        dto.setPosition(employee.getPosition());
        dto.setUserId(employee.getUser() != null ? employee.getUser().getUserId() : null);
        return dto;
    }

    private Employee mapToEntity(EmployeeDto dto, User user) {
        Employee employee = new Employee();
        employee.setEmployeeId(dto.getEmployeeId());
        employee.setCompanyName(dto.getCompanyName());
        employee.setCompanyDescription(dto.getCompanyDescription());
        employee.setPosition(dto.getPosition());
        employee.setUser(user);
        return employee;
    }
}
