package com.hexaware.careercrafter.service;

/*
 * 
 * Author: Chandru
 * Date: 13-Aug-2025
 * 
 * 
 */

import com.hexaware.careercrafter.dto.ResumeDto;
import com.hexaware.careercrafter.entities.JobSeeker;
import com.hexaware.careercrafter.entities.Resume;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IJobSeekerRepo;
import com.hexaware.careercrafter.repository.IResumeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResumeServiceImplTest {

    @Mock private IResumeRepo resumeRepo;
    @Mock private IJobSeekerRepo jobSeekerRepo;

    @InjectMocks private ResumeServiceImpl service;

    private Resume resume;
    private ResumeDto dto;
    private JobSeeker seeker;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        seeker = new JobSeeker();
        seeker.setJobSeekerId(1);

        resume = new Resume();
        resume.setResumeId(1);
        resume.setFilePath("/path");
        resume.setJobSeeker(seeker);

        dto = new ResumeDto();
        dto.setFilePath("/path");
        dto.setUploadDate(LocalDate.now());
        dto.setJobSeekerId(1);
    }

    @Test
    void testCreateResume_Success() {
        when(jobSeekerRepo.findById(1)).thenReturn(Optional.of(seeker));
        when(resumeRepo.save(any(Resume.class))).thenReturn(resume);

        ResumeDto result = service.createResume(dto);

        assertEquals("/path", result.getFilePath());
        verify(resumeRepo).save(any(Resume.class));
    }

    @Test
    void testGetResumeById_Success() {
        when(resumeRepo.findById(1)).thenReturn(Optional.of(resume));

        ResumeDto result = service.getResumeById(1);

        assertEquals("/path", result.getFilePath());
    }

    @Test
    void testGetResumeById_NotFound() {
        when(resumeRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getResumeById(1));
    }

    @Test
    void testDeleteResume_Success() {
        when(resumeRepo.existsById(1)).thenReturn(true);

        service.deleteResume(1);

        verify(resumeRepo).deleteById(1);
    }
}
