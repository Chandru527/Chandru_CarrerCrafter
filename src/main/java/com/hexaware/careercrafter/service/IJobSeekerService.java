package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.entities.JobSeeker;
import java.util.List;

public interface IJobSeekerService {
    JobSeeker saveJobSeeker(JobSeeker jobSeeker);
    JobSeeker getJobSeekerById(int id);
    List<JobSeeker> getAllJobSeekers();
    void deleteJobSeeker(int id);
}
