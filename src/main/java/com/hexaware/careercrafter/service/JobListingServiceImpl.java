package com.hexaware.careercrafter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careercrafter.dto.JobListingDto;
import com.hexaware.careercrafter.entities.Employee;
import com.hexaware.careercrafter.entities.JobListing;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IEmployeeRepo;
import com.hexaware.careercrafter.repository.IJobListingRepo;

@Service
public class JobListingServiceImpl implements IJobListingService {

    private static final Logger logger = LoggerFactory.getLogger(JobListingServiceImpl.class); // 🔹 Logger

    @Autowired
    private IJobListingRepo jobListingRepo;

    @Autowired
    private IEmployeeRepo employeeRepo;

    @Override
    public JobListingDto createJobListing(JobListingDto jobListingDto) {
        logger.info("Creating JobListing with title: {}", jobListingDto.getTitle());

        if (jobListingDto.getTitle() == null || jobListingDto.getTitle().isBlank()) {
            logger.warn("Job title is missing");
            throw new InvalidRequestException("Job title is required");
        }
        if (jobListingDto.getEmployeeId() == null) {
            logger.warn("Employee ID is missing");
            throw new InvalidRequestException("Employee ID is required");
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

        if (jobListingDto.getEmployeeId() != null) {
            Employee employee = employeeRepo.findById(jobListingDto.getEmployeeId())
                    .orElseThrow(() -> {
                        logger.error("Employee not found with id: {}", jobListingDto.getEmployeeId());
                        return new ResourceNotFoundException("Employee not found with id: " + jobListingDto.getEmployeeId());
                    });
            existingJobListing.setEmployee(employee);
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
        dto.setEmployeeId(jobListing.getEmployee() != null ? jobListing.getEmployee().getEmployeeId() : null);
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

        if (dto.getEmployeeId() != null) {
            Employee employee = employeeRepo.findById(dto.getEmployeeId())
                    .orElseThrow(() -> {
                        logger.error("Employee not found with id: {}", dto.getEmployeeId());
                        return new ResourceNotFoundException("Employee not found with id: " + dto.getEmployeeId());
                    });
            jobListing.setEmployee(employee);
        }

        return jobListing;
    }
}
