package com.hexaware.careercrafter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careercrafter.dto.ApplicationDto;
import com.hexaware.careercrafter.entities.Application;
import com.hexaware.careercrafter.entities.JobListing;
import com.hexaware.careercrafter.entities.JobSeeker;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IApplicationRepo;
import com.hexaware.careercrafter.repository.IJobListingRepo;
import com.hexaware.careercrafter.repository.IJobSeekerRepo;

@Service
public class ApplicationServiceImpl implements IApplicationService {

    @Autowired
    private IApplicationRepo applicationRepo;

    @Autowired
    private IJobSeekerRepo jobSeekerRepo;

    @Autowired
    private IJobListingRepo jobListingRepo;

    @Override
    public ApplicationDto createApplication(ApplicationDto dto) {
        validateApplicationDto(dto);

        Application application = mapToEntity(dto);
        Application saved = applicationRepo.save(application);

        return mapToDto(saved);
    }

    @Override
    public ApplicationDto getApplicationById(int id) {
        Application application = applicationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
        return mapToDto(application);
    }

    @Override
    public List<ApplicationDto> getAllApplications() {
        return applicationRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationDto updateApplication(int id, ApplicationDto dto) {
        Application existing = applicationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));

        if (dto.getStatus() != null && !dto.getStatus().isBlank()) {
            existing.setStatus(dto.getStatus());
        }
        if (dto.getApplicationDate() != null) {
            existing.setApplicationDate(dto.getApplicationDate());
        }
        if (dto.getFilePath() != null) {
            existing.setFilePath(dto.getFilePath());
        }
        if (dto.getJobSeekerId() != null) {
            JobSeeker jobSeeker = jobSeekerRepo.findById(dto.getJobSeekerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Job seeker not found"));
            existing.setJobSeeker(jobSeeker);
        }
        if (dto.getJobListingId() != null) {
            JobListing jobListing = jobListingRepo.findById(dto.getJobListingId())
                    .orElseThrow(() -> new ResourceNotFoundException("Job listing not found"));
            existing.setJobListing(jobListing);
        }

        return mapToDto(applicationRepo.save(existing));
    }

    @Override
    public void deleteApplication(int id) {
        if (!applicationRepo.existsById(id)) {
            throw new ResourceNotFoundException("Application not found with id: " + id);
        }
        applicationRepo.deleteById(id);
    }

   

    private void validateApplicationDto(ApplicationDto dto) {
        if (dto.getStatus() == null || dto.getStatus().isBlank()) {
            throw new InvalidRequestException("Application status is required");
        }
        if (dto.getApplicationDate() == null) {
            throw new InvalidRequestException("Application date is required");
        }
        if (dto.getJobSeekerId() == null) {
            throw new InvalidRequestException("Job Seeker ID is required");
        }
        if (dto.getJobListingId() == null) {
            throw new InvalidRequestException("Job Listing ID is required");
        }
    }

    private ApplicationDto mapToDto(Application application) {
        ApplicationDto dto = new ApplicationDto();
        dto.setApplicationId(application.getApplicationId());
        dto.setStatus(application.getStatus());
        dto.setApplicationDate(application.getApplicationDate());
        dto.setFilePath(application.getFilePath());
        dto.setJobSeekerId(application.getJobSeeker() != null ? application.getJobSeeker().getJobSeekerId() : null);
        dto.setJobListingId(application.getJobListing() != null ? application.getJobListing().getJobListingId() : null);
        return dto;
    }

    private Application mapToEntity(ApplicationDto dto) {
        Application application = new Application();
        application.setApplicationId(dto.getApplicationId());
        application.setStatus(dto.getStatus());
        application.setApplicationDate(dto.getApplicationDate());
        application.setFilePath(dto.getFilePath());

        JobSeeker jobSeeker = jobSeekerRepo.findById(dto.getJobSeekerId())
                .orElseThrow(() -> new ResourceNotFoundException("Job seeker not found"));
        application.setJobSeeker(jobSeeker);

        JobListing jobListing = jobListingRepo.findById(dto.getJobListingId())
                .orElseThrow(() -> new ResourceNotFoundException("Job listing not found"));
        application.setJobListing(jobListing);

        return application;
    }
}
