package com.hexaware.careercrafter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careercrafter.dto.ResumeDto;
import com.hexaware.careercrafter.entities.JobSeeker;
import com.hexaware.careercrafter.entities.Resume;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IJobSeekerRepo;
import com.hexaware.careercrafter.repository.IResumeRepo;

@Service
public class ResumeServiceImpl implements IResumeService {

    private static final Logger logger = LoggerFactory.getLogger(ResumeServiceImpl.class); // 🔹 Logger

    @Autowired
    private IResumeRepo resumeRepo;

    @Autowired
    private IJobSeekerRepo jobSeekerRepo;

    @Override
    public ResumeDto createResume(ResumeDto resumeDto) {
        logger.info("Creating Resume for JobSeekerId: {}", resumeDto.getJobSeekerId());
        validateResumeDto(resumeDto);
        Resume savedResume = resumeRepo.save(mapToEntity(resumeDto));
        logger.debug("Resume created successfully: {}", savedResume);
        return mapToDto(savedResume);
    }

    @Override
    public ResumeDto getResumeById(int id) {
        logger.info("Fetching Resume by id: {}", id);
        Resume resume = resumeRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Resume not found with id: {}", id);
                    return new ResourceNotFoundException("Resume not found with id: " + id);
                });
        return mapToDto(resume);
    }

    @Override
    public List<ResumeDto> getAllResumes() {
        logger.info("Fetching all Resumes");
        List<ResumeDto> resumes = resumeRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        logger.debug("Total resumes found: {}", resumes.size());
        return resumes;
    }

    @Override
    public ResumeDto updateResume(int id, ResumeDto resumeDto) {
        logger.info("Updating Resume with id: {}", id);
        Resume existingResume = resumeRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Resume not found with id: {}", id);
                    return new ResourceNotFoundException("Resume not found with id: " + id);
                });

        if (resumeDto.getFilePath() != null) {
            existingResume.setFilePath(resumeDto.getFilePath());
        }
        if (resumeDto.getUploadDate() != null) {
            existingResume.setUploadDate(resumeDto.getUploadDate());
        }
        if (resumeDto.getJobSeekerId() != null) {
            JobSeeker jobSeeker = jobSeekerRepo.findById(resumeDto.getJobSeekerId())
                    .orElseThrow(() -> {
                        logger.error("JobSeeker not found with id: {}", resumeDto.getJobSeekerId());
                        return new ResourceNotFoundException("Job seeker not found with id: " + resumeDto.getJobSeekerId());
                    });
            existingResume.setJobSeeker(jobSeeker);
        }

        Resume updatedResume = resumeRepo.save(existingResume);
        logger.debug("Resume updated successfully: {}", updatedResume);
        return mapToDto(updatedResume);
    }

    @Override
    public void deleteResume(int id) {
        logger.info("Deleting Resume with id: {}", id);
        if (!resumeRepo.existsById(id)) {
            logger.error("Resume not found with id: {}", id);
            throw new ResourceNotFoundException("Resume not found with id: " + id);
        }
        resumeRepo.deleteById(id);
        logger.info("Resume deleted successfully with id: {}", id);
    }

    private void validateResumeDto(ResumeDto resumeDto) {
        if (resumeDto.getFilePath() == null || resumeDto.getFilePath().isBlank()) {
            logger.warn("Validation failed: File path missing");
            throw new InvalidRequestException("File path is required");
        }
        if (resumeDto.getUploadDate() == null) {
            logger.warn("Validation failed: Upload date missing");
            throw new InvalidRequestException("Upload date is required");
        }
        if (resumeDto.getJobSeekerId() == null) {
            logger.warn("Validation failed: Job seeker ID missing");
            throw new InvalidRequestException("Job seeker ID is required");
        }
    }

    private ResumeDto mapToDto(Resume resume) {
        ResumeDto dto = new ResumeDto();
        dto.setResumeId(resume.getResumeId()); 
        dto.setFilePath(resume.getFilePath());
        dto.setUploadDate(resume.getUploadDate());
        dto.setJobSeekerId(resume.getJobSeeker() != null ? resume.getJobSeeker().getJobSeekerId() : null);
        return dto;
    }

    private Resume mapToEntity(ResumeDto dto) {
        Resume resume = new Resume();
        if (dto.getResumeId() != null) {
            resume.setResumeId(dto.getResumeId());
        }
        resume.setFilePath(dto.getFilePath());
        resume.setUploadDate(dto.getUploadDate());

        JobSeeker jobSeeker = jobSeekerRepo.findById(dto.getJobSeekerId())
                .orElseThrow(() -> {
                    logger.error("JobSeeker not found with id: {}", dto.getJobSeekerId());
                    return new ResourceNotFoundException("Job seeker not found with id: " + dto.getJobSeekerId());
                });
        resume.setJobSeeker(jobSeeker);

        return resume;
    }
}
