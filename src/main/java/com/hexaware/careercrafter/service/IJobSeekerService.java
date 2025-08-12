package com.hexaware.careercrafter.service;

import java.util.List;
import com.hexaware.careercrafter.dto.JobSeekerDto;

public interface IJobSeekerService {
    JobSeekerDto createJobSeeker(JobSeekerDto jobSeekerDto);
    JobSeekerDto getJobSeekerById(int id);
    List<JobSeekerDto> getAllJobSeekers();
    JobSeekerDto updateJobSeeker(int id, JobSeekerDto jobSeekerDto);
    void deleteJobSeeker(int id);
}
