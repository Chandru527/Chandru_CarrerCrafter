package com.hexaware.careercrafter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
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

/*
 * 
 * Author: Chandru
 * Date: 13-Aug-2025
 * 
 * 
 */


@Service
public class ApplicationServiceImpl implements IApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class); // 🔹 Logger

    @Autowired
    private IApplicationRepo applicationRepo;

    @Autowired
    private IJobSeekerRepo jobSeekerRepo;

    @Autowired
    private IJobListingRepo jobListingRepo;

    @Override
    public ApplicationDto createApplication(ApplicationDto dto) {
        logger.info("Creating Application for JobSeekerId: {} and JobListingId: {}",
                dto.getJobSeekerId(), dto.getJobListingId());
        validateApplicationDto(dto);

        Application application = mapToEntity(dto);
        Application saved = applicationRepo.save(application);

        logger.debug("Application saved successfully: {}", saved);
        return mapToDto(saved);
    }

    @Override
    public ApplicationDto getApplicationById(int id) {
        logger.info("Fetching Application by id: {}", id);
        Application application = applicationRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Application not found with id: {}", id);
                    return new ResourceNotFoundException("Application not found with id: " + id);
                });
        return mapToDto(application);
    }

    @Override
    public List<ApplicationDto> getAllApplications() {
        logger.info("Fetching all Applications");
        List<ApplicationDto> apps = applicationRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        logger.debug("Total applications found: {}", apps.size());
        return apps;
    }

    @Override
    public ApplicationDto updateApplication(int id, ApplicationDto dto) {
        logger.info("Updating Application with id: {}", id);
        Application existing = applicationRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Application not found with id: {}", id);
                    return new ResourceNotFoundException("Application not found with id: " + id);
                });

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
                    .orElseThrow(() -> {
                        logger.error("JobSeeker not found with id: {}", dto.getJobSeekerId());
                        return new ResourceNotFoundException("Job seeker not found");
                    });
            existing.setJobSeeker(jobSeeker);
        }
        if (dto.getJobListingId() != null) {
            JobListing jobListing = jobListingRepo.findById(dto.getJobListingId())
                    .orElseThrow(() -> {
                        logger.error("JobListing not found with id: {}", dto.getJobListingId());
                        return new ResourceNotFoundException("Job listing not found");
                    });
            existing.setJobListing(jobListing);
        }

        Application updated = applicationRepo.save(existing);
        logger.debug("Application updated successfully: {}", updated);
        return mapToDto(updated);
    }

    @Override
    public void deleteApplication(int id) {
        logger.info("Deleting Application with id: {}", id);
        if (!applicationRepo.existsById(id)) {
            logger.error("Application not found with id: {}", id);
            throw new ResourceNotFoundException("Application not found with id: " + id);
        }
        applicationRepo.deleteById(id);
        logger.info("Application deleted successfully with id: {}", id);
    }

    
    @Override
    public List<ApplicationDto> getApplicationsByJobSeekerId(int jobSeekerId) {
        logger.info("Fetching applications for JobSeekerId: {}", jobSeekerId);
        return applicationRepo.findByJobSeeker_JobSeekerId(jobSeekerId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
    private void validateApplicationDto(ApplicationDto dto) {
        if (dto.getStatus() == null || dto.getStatus().isBlank()) {
            logger.warn("Validation failed: Application status missing");
            throw new InvalidRequestException("Application status is required");
        }
        if (dto.getApplicationDate() == null) {
            logger.warn("Validation failed: Application date missing");
            throw new InvalidRequestException("Application date is required");
        }
        if (dto.getJobSeekerId() == null) {
            logger.warn("Validation failed: Job seeker ID missing");
            throw new InvalidRequestException("Job Seeker ID is required");
        }
        if (dto.getJobListingId() == null) {
            logger.warn("Validation failed: Job listing ID missing");
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
                .orElseThrow(() -> {
                    logger.error("JobSeeker not found with id: {}", dto.getJobSeekerId());
                    return new ResourceNotFoundException("Job seeker not found");
                });
        application.setJobSeeker(jobSeeker);

        JobListing jobListing = jobListingRepo.findById(dto.getJobListingId())
                .orElseThrow(() -> {
                    logger.error("JobListing not found with id: {}", dto.getJobListingId());
                    return new ResourceNotFoundException("Job listing not found");
                });
        application.setJobListing(jobListing);

        return application;
    }
}
