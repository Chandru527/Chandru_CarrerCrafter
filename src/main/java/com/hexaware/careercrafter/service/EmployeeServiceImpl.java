package com.hexaware.careercrafter.service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private IEmployeeRepo employeeRepo;

    @Autowired
    private IUserRepo userRepo;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        if (employeeDto.getCompanyName() == null || employeeDto.getCompanyName().isBlank()) {
            throw new InvalidRequestException("Company name is required");
        }
        if (employeeDto.getCompanyDescription() == null || employeeDto.getCompanyDescription().isBlank()) {
            throw new InvalidRequestException("Company description is required");
        }
        if (employeeDto.getUserId() == null) {
            throw new InvalidRequestException("User ID is required");
        }

        
        User user = userRepo.findById(employeeDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + employeeDto.getUserId()));

        Employee savedEmployee = employeeRepo.save(mapToEntity(employeeDto, user));
        return mapToDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(int id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return mapToDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepo.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(int id, EmployeeDto employeeDto) {
        Employee existingEmployee = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        existingEmployee.setCompanyName(employeeDto.getCompanyName());
        existingEmployee.setCompanyDescription(employeeDto.getCompanyDescription());
        existingEmployee.setPosition(employeeDto.getPosition());

       
        if (employeeDto.getUserId() != null) {
            User user = userRepo.findById(employeeDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + employeeDto.getUserId()));
            existingEmployee.setUser(user);
        }

        return mapToDto(employeeRepo.save(existingEmployee));
    }

    @Override
    public void deleteEmployee(int id) {
        if (!employeeRepo.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        employeeRepo.deleteById(id);
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
