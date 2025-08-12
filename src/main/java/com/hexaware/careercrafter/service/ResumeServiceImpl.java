package com.hexaware.careercrafter.service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private IResumeRepo resumeRepo;

    @Autowired
    private IJobSeekerRepo jobSeekerRepo;

    @Override
    public ResumeDto createResume(ResumeDto resumeDto) {
        validateResumeDto(resumeDto);

        
        Resume savedResume = resumeRepo.save(mapToEntity(resumeDto));
        return mapToDto(savedResume);
    }

    @Override
    public ResumeDto getResumeById(int id) {
        Resume resume = resumeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found with id: " + id));
        return mapToDto(resume);
    }

    @Override
    public List<ResumeDto> getAllResumes() {
        return resumeRepo.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ResumeDto updateResume(int id, ResumeDto resumeDto) {
        Resume existingResume = resumeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found with id: " + id));

        if (resumeDto.getFilePath() != null) {
            existingResume.setFilePath(resumeDto.getFilePath());
        }
        if (resumeDto.getUploadDate() != null) {
            existingResume.setUploadDate(resumeDto.getUploadDate());
        }
        if (resumeDto.getJobSeekerId() != null) {
            JobSeeker jobSeeker = jobSeekerRepo.findById(resumeDto.getJobSeekerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Job seeker not found with id: " + resumeDto.getJobSeekerId()));
            existingResume.setJobSeeker(jobSeeker);
        }

        return mapToDto(resumeRepo.save(existingResume));
    }

    @Override
    public void deleteResume(int id) {
        if (!resumeRepo.existsById(id)) {
            throw new ResourceNotFoundException("Resume not found with id: " + id);
        }
        resumeRepo.deleteById(id);
    }

   
    private void validateResumeDto(ResumeDto resumeDto) {
        if (resumeDto.getFilePath() == null || resumeDto.getFilePath().isBlank()) {
            throw new InvalidRequestException("File path is required");
        }
        if (resumeDto.getUploadDate() == null) {
            throw new InvalidRequestException("Upload date is required");
        }
        if (resumeDto.getJobSeekerId() == null) {
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
                .orElseThrow(() -> new ResourceNotFoundException("Job seeker not found with id: " + dto.getJobSeekerId()));
        resume.setJobSeeker(jobSeeker);

        return resume;
    }
}
