package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.entities.Application;
import java.util.List;

public interface IApplicationService {
    Application saveApplication(Application application);
    Application getApplicationById(int id);
    List<Application> getAllApplications();
    void deleteApplication(int id);
}
