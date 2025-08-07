package com.hexaware.careercrafter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careercrafter.entities.JobListing;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IJobListingRepo;

@Service
public class JobListingServiceImpl implements IJobListingService {

    @Autowired
    private IJobListingRepo jobListingRepo;

    @Override
    public JobListing saveJobListing(JobListing jobListing) {
        if (jobListing.getTitle() == null || jobListing.getTitle().isBlank()) {
            throw new InvalidRequestException("Job title is required");
        }
        return jobListingRepo.save(jobListing);
    }

    @Override
    public JobListing getJobListingById(int id) {
        return jobListingRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job Listing not found with id: " + id));
    }

    @Override
    public List<JobListing> getAllJobListings() {
        return jobListingRepo.findAll();
    }

    @Override
    public void deleteJobListing(int id) {
        if (!jobListingRepo.existsById(id)) {
            throw new ResourceNotFoundException("Job Listing not found with id: " + id);
        }
        jobListingRepo.deleteById(id);
    }
}
