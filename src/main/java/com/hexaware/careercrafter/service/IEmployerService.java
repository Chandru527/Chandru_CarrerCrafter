package com.hexaware.careercrafter.service;

import java.util.List;
import com.hexaware.careercrafter.dto.EmployerDto;

public interface IEmployerService {
    EmployerDto createEmployer(EmployerDto employerDto);
    EmployerDto getEmployerById(int id);
    List<EmployerDto> getAllEmployers();
    EmployerDto updateEmployer(int id, EmployerDto employerDto);
    void deleteEmployer(int id);
}
