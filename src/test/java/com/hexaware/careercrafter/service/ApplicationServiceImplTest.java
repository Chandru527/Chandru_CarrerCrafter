package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.ApplicationDto;
import com.hexaware.careercrafter.entities.Application;
import com.hexaware.careercrafter.entities.JobListing;
import com.hexaware.careercrafter.entities.JobSeeker;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IApplicationRepo;
import com.hexaware.careercrafter.repository.IJobListingRepo;
import com.hexaware.careercrafter.repository.IJobSeekerRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationServiceImplTest {

    @Mock
    private IApplicationRepo applicationRepo;
    @Mock
    private IJobSeekerRepo jobSeekerRepo;
    @Mock
    private IJobListingRepo jobListingRepo;

    @InjectMocks
    private ApplicationServiceImpl service;

    private JobSeeker jobSeeker;
    private JobListing jobListing;
    private ApplicationDto dto;
    private Application application;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jobSeeker = new JobSeeker();
        jobSeeker.setJobSeekerId(1);

        jobListing = new JobListing();
        jobListing.setJobListingId(1);

        dto = new ApplicationDto();
        dto.setStatus("Pending");
        dto.setApplicationDate(LocalDate.now());
        dto.setJobSeekerId(1);
        dto.setJobListingId(1);

        application = new Application();
        application.setApplicationId(1);
        application.setStatus("Pending");
        application.setJobSeeker(jobSeeker);
        application.setJobListing(jobListing);
    }

    @Test
    void testCreateApplication_Success() {
        when(jobSeekerRepo.findById(1)).thenReturn(Optional.of(jobSeeker));
        when(jobListingRepo.findById(1)).thenReturn(Optional.of(jobListing));
        when(applicationRepo.save(any(Application.class))).thenReturn(application);

        ApplicationDto result = service.createApplication(dto);

        assertEquals("Pending", result.getStatus());
        verify(applicationRepo).save(any(Application.class));
    }

    @Test
    void testGetApplicationById_Success() {
        when(applicationRepo.findById(1)).thenReturn(Optional.of(application));

        ApplicationDto result = service.getApplicationById(1);

        assertEquals("Pending", result.getStatus());
    }

    @Test
    void testGetApplicationById_NotFound() {
        when(applicationRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getApplicationById(1));
    }

    @Test
    void testDeleteApplication_Success() {
        when(applicationRepo.existsById(1)).thenReturn(true);

        service.deleteApplication(1);

        verify(applicationRepo).deleteById(1);
    }
}
