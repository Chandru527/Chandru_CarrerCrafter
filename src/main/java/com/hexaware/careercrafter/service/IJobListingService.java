
package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.entities.JobListing;
import java.util.List;

public interface IJobListingService {
    JobListing saveJobListing(JobListing jobListing);
    JobListing getJobListingById(int id);
    List<JobListing> getAllJobListings();
    void deleteJobListing(int id);
}
