package com.hexaware.careercrafter.service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private IJobListingRepo jobListingRepo;

    @Autowired
    private IEmployeeRepo employeeRepo;

    @Override
    public JobListingDto createJobListing(JobListingDto jobListingDto) {
        if (jobListingDto.getTitle() == null || jobListingDto.getTitle().isBlank()) {
            throw new InvalidRequestException("Job title is required");
        }
        if (jobListingDto.getEmployeeId() == null) {
            throw new InvalidRequestException("Employee ID is required");
        }

        JobListing savedJobListing = jobListingRepo.save(mapToEntity(jobListingDto));
        return mapToDto(savedJobListing);
    }

    @Override
    public JobListingDto getJobListingById(int id) {
        JobListing jobListing = jobListingRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job listing not found with id: " + id));
        return mapToDto(jobListing);
    }

    @Override
    public List<JobListingDto> getAllJobListings() {
        return jobListingRepo.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public JobListingDto updateJobListing(int id, JobListingDto jobListingDto) {
        JobListing existingJobListing = jobListingRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job listing not found with id: " + id));

        existingJobListing.setTitle(jobListingDto.getTitle());
        existingJobListing.setDescription(jobListingDto.getDescription());
        existingJobListing.setQualifications(jobListingDto.getQualifications());
        existingJobListing.setLocation(jobListingDto.getLocation());
        existingJobListing.setSalary(jobListingDto.getSalary());
        existingJobListing.setPostedDate(jobListingDto.getPostedDate());

        if (jobListingDto.getEmployeeId() != null) {
            Employee employee = employeeRepo.findById(jobListingDto.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + jobListingDto.getEmployeeId()));
            existingJobListing.setEmployee(employee);
        }

        return mapToDto(jobListingRepo.save(existingJobListing));
    }

    @Override
    public void deleteJobListing(int id) {
        if (!jobListingRepo.existsById(id)) {
            throw new ResourceNotFoundException("Job listing not found with id: " + id);
        }
        jobListingRepo.deleteById(id);
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
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + dto.getEmployeeId()));
            jobListing.setEmployee(employee);
        }

        return jobListing;
    }
}
