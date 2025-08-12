package com.hexaware.careercrafter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careercrafter.dto.JobSeekerDto;
import com.hexaware.careercrafter.entities.JobSeeker;
import com.hexaware.careercrafter.entities.User;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IJobSeekerRepo;
import com.hexaware.careercrafter.repository.IUserRepo;

@Service
public class JobSeekerServiceImpl implements IJobSeekerService {

    @Autowired
    private IJobSeekerRepo jobSeekerRepo;

    @Autowired
    private IUserRepo userRepo;

    @Override
    public JobSeekerDto createJobSeeker(JobSeekerDto jobSeekerDto) {
        if (jobSeekerDto.getFullName() == null || jobSeekerDto.getFullName().isBlank()) {
            throw new InvalidRequestException("Full name is required");
        }
        if (jobSeekerDto.getUserId() == null) {
            throw new InvalidRequestException("User ID is required");
        }

        JobSeeker jobSeeker = mapToEntity(jobSeekerDto);
        JobSeeker savedJobSeeker = jobSeekerRepo.save(jobSeeker);
        return mapToDto(savedJobSeeker);
    }

    @Override
    public JobSeekerDto getJobSeekerById(int id) {
        JobSeeker jobSeeker = jobSeekerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job seeker not found with id: " + id));
        return mapToDto(jobSeeker);
    }

    @Override
    public List<JobSeekerDto> getAllJobSeekers() {
        return jobSeekerRepo.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public JobSeekerDto updateJobSeeker(int id, JobSeekerDto jobSeekerDto) {
        JobSeeker existingJobSeeker = jobSeekerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job seeker not found with id: " + id));

        existingJobSeeker.setFullName(jobSeekerDto.getFullName());
        existingJobSeeker.setEducation(jobSeekerDto.getEducation());
        existingJobSeeker.setExperience(jobSeekerDto.getExperience());
        existingJobSeeker.setSkills(jobSeekerDto.getSkills());

        if (jobSeekerDto.getUserId() != null) {
            User user = userRepo.findById(jobSeekerDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + jobSeekerDto.getUserId()));
            existingJobSeeker.setUser(user);
        }

        return mapToDto(jobSeekerRepo.save(existingJobSeeker));
    }

    @Override
    public void deleteJobSeeker(int id) {
        if (!jobSeekerRepo.existsById(id)) {
            throw new ResourceNotFoundException("Job seeker not found with id: " + id);
        }
        jobSeekerRepo.deleteById(id);
    }

   
    private JobSeekerDto mapToDto(JobSeeker jobSeeker) {
        JobSeekerDto dto = new JobSeekerDto();
        dto.setJobSeekerId(jobSeeker.getJobSeekerId());
        dto.setFullName(jobSeeker.getFullName());
        dto.setEducation(jobSeeker.getEducation());
        dto.setExperience(jobSeeker.getExperience());
        dto.setSkills(jobSeeker.getSkills());
        dto.setUserId(jobSeeker.getUser() != null ? jobSeeker.getUser().getUserId() : null);
        return dto;
    }

    
    private JobSeeker mapToEntity(JobSeekerDto dto) {
        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setJobSeekerId(dto.getJobSeekerId());
        jobSeeker.setFullName(dto.getFullName());
        jobSeeker.setEducation(dto.getEducation());
        jobSeeker.setExperience(dto.getExperience());
        jobSeeker.setSkills(dto.getSkills());

        if (dto.getUserId() != null) {
            User user = userRepo.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + dto.getUserId()));
            jobSeeker.setUser(user);
        }

        return jobSeeker;
    }
}
