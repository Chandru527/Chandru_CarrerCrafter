package com.hexaware.careercrafter.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hexaware.careercrafter.dto.ResumeDto;
import com.hexaware.careercrafter.entities.JobSeeker;
import com.hexaware.careercrafter.entities.Resume;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IJobSeekerRepo;
import com.hexaware.careercrafter.repository.IResumeRepo;

@Service
public class ResumeServiceImpl implements IResumeService {

    private static final Logger logger = LoggerFactory.getLogger(ResumeServiceImpl.class);

    @Autowired
    private IResumeRepo resumeRepo;

    @Autowired
    private IJobSeekerRepo jobSeekerRepo;

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
        return resumeRepo.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResumeDto updateResume(int id, ResumeDto resumeDto) {
        logger.info("Updating Resume with id: {}", id);
        Resume existingResume = resumeRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Resume not found with id: {}", id);
                    return new ResourceNotFoundException("Resume not found with id: " + id);
                });
        if (resumeDto.getFilePath() != null) existingResume.setFilePath(resumeDto.getFilePath());
        if (resumeDto.getUploadDate() != null) existingResume.setUploadDate(resumeDto.getUploadDate());
        if (resumeDto.getJobSeekerId() != null) {
            JobSeeker jobSeeker = jobSeekerRepo.findById(resumeDto.getJobSeekerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Job seeker not found with id: " + resumeDto.getJobSeekerId()));
            existingResume.setJobSeeker(jobSeeker);
        }
        Resume updated = resumeRepo.save(existingResume);
        return mapToDto(updated);
    }

    @Override
    public void deleteResume(int id) {
        logger.info("Deleting Resume with id: {}", id);
        if (!resumeRepo.existsById(id)) {
            throw new ResourceNotFoundException("Resume not found with id: " + id);
        }
        resumeRepo.deleteById(id);
        logger.info("Deleted Resume with id: {}", id);
    }

    @Override
    public ResumeDto uploadResume(MultipartFile file, Integer jobSeekerId) {
        if (file == null || file.isEmpty()) {
            throw new InvalidRequestException("Uploaded file is empty");
        }
        try {
            Path currentPath = Paths.get("").toAbsolutePath();
            Path projectRoot = currentPath;
            while (projectRoot != null && !Files.exists(projectRoot.resolve("pom.xml")) && !Files.exists(projectRoot.resolve("build.gradle"))) {
                projectRoot = projectRoot.getParent();
            }
            if (projectRoot == null) {
                throw new RuntimeException("Could not determine project root directory to store resumes");
            }
            Path uploadDir = projectRoot.resolve("resumes");
            Files.createDirectories(uploadDir);

            String originalFilename = file.getOriginalFilename();
            String timestampedFilename = jobSeekerId + "_" + System.currentTimeMillis() + "_" + originalFilename;
            Path filePath = uploadDir.resolve(timestampedFilename);

            file.transferTo(filePath.toFile());
            logger.info("File saved to: {}", filePath.toAbsolutePath());

            Optional<Resume> existingResumeOpt = resumeRepo.findByJobSeeker_JobSeekerId(jobSeekerId);

            Resume resume = existingResumeOpt.orElse(new Resume());
            resume.setFilePath(filePath.toString());
            resume.setUploadDate(LocalDate.now());

            JobSeeker jobSeeker = jobSeekerRepo.findById(jobSeekerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Job seeker not found with id: " + jobSeekerId));
            resume.setJobSeeker(jobSeeker);

            Resume savedResume = resumeRepo.save(resume);
            logger.info("Resume record saved with id: {}", savedResume.getResumeId());

            return mapToDto(savedResume);

        } catch (IOException e) {
            logger.error("Failed to save uploaded file", e);
            throw new RuntimeException("Failed to save file: " + e.getMessage(), e);
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
}
