package com.hexaware.careercrafter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careercrafter.entities.JobSeeker;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IJobSeekerRepo;

@Service
public class JobSeekerServiceImpl implements IJobSeekerService {

    @Autowired
    private IJobSeekerRepo jobSeekerRepo;

    @Override
    public JobSeeker saveJobSeeker(JobSeeker jobSeeker) {
        if (jobSeeker.getSkills() == null || jobSeeker.getSkills().isBlank()) {
            throw new InvalidRequestException("Skills are required");
        }
        return jobSeekerRepo.save(jobSeeker);
    }

    @Override
    public JobSeeker getJobSeekerById(int id) {
        return jobSeekerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job Seeker not found with id: " + id));
    }

    @Override
    public List<JobSeeker> getAllJobSeekers() {
        return jobSeekerRepo.findAll();
    }

    @Override
    public void deleteJobSeeker(int id) {
        if (!jobSeekerRepo.existsById(id)) {
            throw new ResourceNotFoundException("Job Seeker not found with id: " + id);
        }
        jobSeekerRepo.deleteById(id);
    }
}
