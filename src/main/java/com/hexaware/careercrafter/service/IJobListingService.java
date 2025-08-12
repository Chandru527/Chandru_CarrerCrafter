package com.hexaware.careercrafter.service;

import java.util.List;
import com.hexaware.careercrafter.dto.JobListingDto;

public interface IJobListingService {
    JobListingDto createJobListing(JobListingDto jobListingDto);
    JobListingDto getJobListingById(int id);
    List<JobListingDto> getAllJobListings();
    JobListingDto updateJobListing(int id, JobListingDto jobListingDto);
    void deleteJobListing(int id);
}
