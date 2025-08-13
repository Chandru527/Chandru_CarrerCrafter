package com.hexaware.careercrafter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
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

    private static final Logger logger = LoggerFactory.getLogger(JobSeekerServiceImpl.class); // 🔹 Logger

    @Autowired
    private IJobSeekerRepo jobSeekerRepo;

    @Autowired
    private IUserRepo userRepo;

    @Override
    public JobSeekerDto createJobSeeker(JobSeekerDto jobSeekerDto) {
        logger.info("Creating new JobSeeker with name: {}", jobSeekerDto.getFullName());

        if (jobSeekerDto.getFullName() == null || jobSeekerDto.getFullName().isBlank()) {
            logger.warn("Full name is missing");
            throw new InvalidRequestException("Full name is required");
        }
        if (jobSeekerDto.getUserId() == null) {
            logger.warn("User ID is missing");
            throw new InvalidRequestException("User ID is required");
        }

        JobSeeker jobSeeker = mapToEntity(jobSeekerDto);
        JobSeeker savedJobSeeker = jobSeekerRepo.save(jobSeeker);
        logger.debug("Saved JobSeeker entity: {}", savedJobSeeker);
        return mapToDto(savedJobSeeker);
    }

    @Override
    public JobSeekerDto getJobSeekerById(int id) {
        logger.info("Fetching JobSeeker by id: {}", id);
        JobSeeker jobSeeker = jobSeekerRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("JobSeeker not found with id: {}", id);
                    return new ResourceNotFoundException("Job seeker not found with id: " + id);
                });
        return mapToDto(jobSeeker);
    }

    @Override
    public List<JobSeekerDto> getAllJobSeekers() {
        logger.info("Fetching all JobSeekers");
        List<JobSeekerDto> seekers = jobSeekerRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        logger.debug("Total JobSeekers found: {}", seekers.size());
        return seekers;
    }

    @Override
    public JobSeekerDto updateJobSeeker(int id, JobSeekerDto jobSeekerDto) {
        logger.info("Updating JobSeeker with id: {}", id);

        JobSeeker existingJobSeeker = jobSeekerRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("JobSeeker not found with id: {}", id);
                    return new ResourceNotFoundException("Job seeker not found with id: " + id);
                });

        existingJobSeeker.setFullName(jobSeekerDto.getFullName());
        existingJobSeeker.setEducation(jobSeekerDto.getEducation());
        existingJobSeeker.setExperience(jobSeekerDto.getExperience());
        existingJobSeeker.setSkills(jobSeekerDto.getSkills());

        if (jobSeekerDto.getUserId() != null) {
            User user = userRepo.findById(jobSeekerDto.getUserId())
                    .orElseThrow(() -> {
                        logger.error("User not found with id: {}", jobSeekerDto.getUserId());
                        return new ResourceNotFoundException("User not found with ID: " + jobSeekerDto.getUserId());
                    });
            existingJobSeeker.setUser(user);
        }

        JobSeeker updatedJobSeeker = jobSeekerRepo.save(existingJobSeeker);
        logger.debug("JobSeeker updated successfully: {}", updatedJobSeeker);
        return mapToDto(updatedJobSeeker);
    }

    @Override
    public void deleteJobSeeker(int id) {
        logger.info("Deleting JobSeeker with id: {}", id);
        if (!jobSeekerRepo.existsById(id)) {
            logger.error("JobSeeker not found with id: {}", id);
            throw new ResourceNotFoundException("Job seeker not found with id: " + id);
        }
        jobSeekerRepo.deleteById(id);
        logger.info("JobSeeker deleted successfully with id: {}", id);
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
                    .orElseThrow(() -> {
                        logger.error("User not found with id: {}", dto.getUserId());
                        return new ResourceNotFoundException("User not found with ID: " + dto.getUserId());
                    });
            jobSeeker.setUser(user);
        }

        return jobSeeker;
    }
}
