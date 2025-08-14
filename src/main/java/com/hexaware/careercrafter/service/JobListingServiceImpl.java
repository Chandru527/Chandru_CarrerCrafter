package com.hexaware.careercrafter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careercrafter.dto.JobListingDto;
import com.hexaware.careercrafter.entities.Employer;
import com.hexaware.careercrafter.entities.JobListing;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IEmployerRepo;
import com.hexaware.careercrafter.repository.IJobListingRepo;

/*
 * Author: Chandru
 * Date: 13-Aug-2025
 */

@Service
public class JobListingServiceImpl implements IJobListingService {

    private static final Logger logger = LoggerFactory.getLogger(JobListingServiceImpl.class); // 🔹 Logger

    @Autowired
    private IJobListingRepo jobListingRepo;

    @Autowired
    private IEmployerRepo employerRepo;

    @Override
    public JobListingDto createJobListing(JobListingDto jobListingDto) {
        logger.info("Creating JobListing with title: {}", jobListingDto.getTitle());

        if (jobListingDto.getTitle() == null || jobListingDto.getTitle().isBlank()) {
            logger.warn("Job title is missing");
            throw new InvalidRequestException("Job title is required");
        }
        if (jobListingDto.getEmployerId() == null) {
            logger.warn("Employer ID is missing");
            throw new InvalidRequestException("Employer ID is required");
        }

        JobListing savedJobListing = jobListingRepo.save(mapToEntity(jobListingDto));
        logger.debug("JobListing saved successfully: {}", savedJobListing);
        return mapToDto(savedJobListing);
    }

    @Override
    public JobListingDto getJobListingById(int id) {
        logger.info("Fetching JobListing by id: {}", id);
        JobListing jobListing = jobListingRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Job listing not found with id: {}", id);
                    return new ResourceNotFoundException("Job listing not found with id: " + id);
                });
        return mapToDto(jobListing);
    }

    @Override
    public List<JobListingDto> getAllJobListings() {
        logger.info("Fetching all JobListings");
        List<JobListingDto> listings = jobListingRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        logger.debug("Total job listings found: {}", listings.size());
        return listings;
    }

    @Override
    public JobListingDto updateJobListing(int id, JobListingDto jobListingDto) {
        logger.info("Updating JobListing with id: {}", id);
        JobListing existingJobListing = jobListingRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Job listing not found with id: {}", id);
                    return new ResourceNotFoundException("Job listing not found with id: " + id);
                });

        existingJobListing.setTitle(jobListingDto.getTitle());
        existingJobListing.setDescription(jobListingDto.getDescription());
        existingJobListing.setQualifications(jobListingDto.getQualifications());
        existingJobListing.setLocation(jobListingDto.getLocation());
        existingJobListing.setSalary(jobListingDto.getSalary());
        existingJobListing.setPostedDate(jobListingDto.getPostedDate());

        if (jobListingDto.getEmployerId() != null) {
            Employer employer = employerRepo.findById(jobListingDto.getEmployerId())
                    .orElseThrow(() -> {
                        logger.error("Employer not found with id: {}", jobListingDto.getEmployerId());
                        return new ResourceNotFoundException("Employer not found with id: " + jobListingDto.getEmployerId());
                    });
            existingJobListing.setEmployer(employer);
        }

        JobListing updatedJobListing = jobListingRepo.save(existingJobListing);
        logger.debug("JobListing updated successfully: {}", updatedJobListing);
        return mapToDto(updatedJobListing);
    }

    @Override
    public void deleteJobListing(int id) {
        logger.info("Deleting JobListing with id: {}", id);
        if (!jobListingRepo.existsById(id)) {
            logger.error("Job listing not found with id: {}", id);
            throw new ResourceNotFoundException("Job listing not found with id: " + id);
        }
        jobListingRepo.deleteById(id);
        logger.info("JobListing deleted successfully with id: {}", id);
    }

    private JobListingDto mapToDto(JobListing jobListing) {
        JobListingDto dto = new JobListingDto();
        dto.setJobListingId(jobListing.getJobListingId());
        dto.setTitle(jobListing.getTitle());
        dto.setDescription(jobListing.getDescription());
        dto.setQualifications(jobListing.getQualifications());
        dto.setLocation(jobListing.getLocation());
        dto.setSalary(jobListing.getSalary());
        dto.setPostedDate(jobListing.getPostedDate());
        dto.setEmployerId(jobListing.getEmployer() != null ? jobListing.getEmployer().getEmployerId() : null);
        return dto;
    }

    private JobListing mapToEntity(JobListingDto dto) {
        JobListing jobListing = new JobListing();
        jobListing.setJobListingId(dto.getJobListingId());
        jobListing.setTitle(dto.getTitle());
        jobListing.setDescription(dto.getDescription());
        jobListing.setQualifications(dto.getQualifications());
        jobListing.setLocation(dto.getLocation());
        jobListing.setSalary(dto.getSalary());
        jobListing.setPostedDate(dto.getPostedDate());

        if (dto.getEmployerId() != null) {
            Employer employer = employerRepo.findById(dto.getEmployerId())
                    .orElseThrow(() -> {
                        logger.error("Employer not found with id: {}", dto.getEmployerId());
                        return new ResourceNotFoundException("Employer not found with id: " + dto.getEmployerId());
                    });
            jobListing.setEmployer(employer);
        }

        return jobListing;
    }
}
