package com.hexaware.careercrafter.service;

/*
 * 
 * Author: Chandru
 * Date: 13-Aug-2025
 * 
 * 
 */

import java.util.List;
import com.hexaware.careercrafter.dto.ApplicationDto;

public interface IApplicationService {
    ApplicationDto createApplication(ApplicationDto applicationDto);
    ApplicationDto getApplicationById(int id);
    List<ApplicationDto> getAllApplications();
    ApplicationDto updateApplication(int id, ApplicationDto applicationDto);
    void deleteApplication(int id);
    
    List<ApplicationDto> getApplicationsByJobSeekerId(int jobSeekerId);
}
