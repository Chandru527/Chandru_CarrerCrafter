package com.hexaware.careercrafter.service;

/*
 * 
 * Author: Chandru
 * Date: 13-Aug-2025
 * 
 * 
 */

import java.util.List;
import com.hexaware.careercrafter.dto.EmployerDto;

public interface IEmployerService {
    EmployerDto createEmployer(EmployerDto employerDto);
    EmployerDto getEmployerById(int id);
    List<EmployerDto> getAllEmployers();
    EmployerDto updateEmployer(int id, EmployerDto employerDto);
    void deleteEmployer(int id);
}
