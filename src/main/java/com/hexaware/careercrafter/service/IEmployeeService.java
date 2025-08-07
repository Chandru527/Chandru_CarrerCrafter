package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.entities.Employee;
import java.util.List;

public interface IEmployeeService {
    Employee saveEmployee(Employee employee);
    Employee getEmployeeById(int id);
    List<Employee> getAllEmployees();
    void deleteEmployee(int id);
}
