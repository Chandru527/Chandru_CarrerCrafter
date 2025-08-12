package com.hexaware.careercrafter.service;

import java.util.List;
import com.hexaware.careercrafter.dto.ApplicationDto;

public interface IApplicationService {
    ApplicationDto createApplication(ApplicationDto applicationDto);
    ApplicationDto getApplicationById(int id);
    List<ApplicationDto> getAllApplications();
    ApplicationDto updateApplication(int id, ApplicationDto applicationDto);
    void deleteApplication(int id);
}
