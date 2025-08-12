package com.hexaware.careercrafter.service;

import java.util.List;
import com.hexaware.careercrafter.dto.EmployeeDto;

public interface IEmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    EmployeeDto getEmployeeById(int id);
    List<EmployeeDto> getAllEmployees();
    EmployeeDto updateEmployee(int id, EmployeeDto employeeDto);
    void deleteEmployee(int id);
}
