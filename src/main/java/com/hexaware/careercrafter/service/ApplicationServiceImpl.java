package com.hexaware.careercrafter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careercrafter.entities.Application;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IApplicationRepo;

@Service
public class ApplicationServiceImpl implements IApplicationService {

    @Autowired
    private IApplicationRepo applicationRepo;

    @Override
    public Application saveApplication(Application application) {
        if (application.getStatus() == null || application.getStatus().isBlank()) {
            throw new InvalidRequestException("Application status is required");
        }
        return applicationRepo.save(application);
    }

    @Override
    public Application getApplicationById(int id) {
        return applicationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
    }

    @Override
    public List<Application> getAllApplications() {
        return applicationRepo.findAll();
    }

    @Override
    public void deleteApplication(int id) {
        if (!applicationRepo.existsById(id)) {
            throw new ResourceNotFoundException("Application not found with id: " + id);
        }
        applicationRepo.deleteById(id);
    }
}
