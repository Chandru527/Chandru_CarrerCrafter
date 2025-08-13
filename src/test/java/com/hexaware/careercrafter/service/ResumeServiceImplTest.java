package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.ResumeDto;
import com.hexaware.careercrafter.entities.JobSeeker;
import com.hexaware.careercrafter.entities.Resume;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IJobSeekerRepo;
import com.hexaware.careercrafter.repository.IResumeRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
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
    void createResume_success() {
        when(jobSeekerRepo.findById(1)).thenReturn(Optional.of(seeker));
        when(resumeRepo.save(any(Resume.class))).thenReturn(resume);

        ResumeDto result = service.createResume(dto);
        assertEquals("/path", result.getFilePath());
    }

    @Test
    void createResume_missingFilePath() {
        dto.setFilePath("");
        assertThrows(InvalidRequestException.class, () -> service.createResume(dto));
    }

    @Test
    void getResumeById_success() {
        when(resumeRepo.findById(1)).thenReturn(Optional.of(resume));
        assertEquals("/path", service.getResumeById(1).getFilePath());
    }

    @Test
    void getResumeById_notFound() {
        when(resumeRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getResumeById(1));
    }

    @Test
    void getAllResumes_success() {
        when(resumeRepo.findAll()).thenReturn(Arrays.asList(resume));
        assertEquals(1, service.getAllResumes().size());
    }

    @Test
    void updateResume_success() {
        when(resumeRepo.findById(1)).thenReturn(Optional.of(resume));
        when(jobSeekerRepo.findById(1)).thenReturn(Optional.of(seeker));
        when(resumeRepo.save(any(Resume.class))).thenReturn(resume);

        dto.setFilePath("/newpath");
        ResumeDto result = service.updateResume(1, dto);
        assertEquals("/newpath", result.getFilePath());
    }

    @Test
    void deleteResume_success() {
        when(resumeRepo.existsById(1)).thenReturn(true);
        service.deleteResume(1);
        verify(resumeRepo).deleteById(1);
    }

    @Test
    void deleteResume_notFound() {
        when(resumeRepo.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.deleteResume(1));
    }
}
