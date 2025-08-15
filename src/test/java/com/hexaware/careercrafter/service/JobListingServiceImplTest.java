package com.hexaware.careercrafter.service;

/*
 * 
 * Author: Chandru
 * Date: 13-Aug-2025
 * 
 * 
 */

import com.hexaware.careercrafter.dto.JobListingDto;
import com.hexaware.careercrafter.entities.Employer;
import com.hexaware.careercrafter.entities.JobListing;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IEmployerRepo;
import com.hexaware.careercrafter.repository.IJobListingRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobListingServiceImplTest {

    @Mock
    private IJobListingRepo jobListingRepo;

    @Mock
    private IEmployerRepo employerRepo;

    @InjectMocks
    private JobListingServiceImpl service;

    private JobListing jobListing;
    private JobListingDto dto;
    private Employer employer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employer = new Employer();
        employer.setEmployerId(100);
        employer.setCompanyName("ABC Corp");

        jobListing = new JobListing();
        jobListing.setJobListingId(1);
        jobListing.setTitle("Developer");
        jobListing.setEmployer(employer);

        dto = new JobListingDto();
        dto.setTitle("Developer");
        dto.setEmployerId(100);
    }

  /*  @Test
    void testCreateJobListing_Success() {
        when(employerRepo.findById(100)).thenReturn(Optional.of(employer));
        when(jobListingRepo.save(any(JobListing.class))).thenReturn(jobListing);

        JobListingDto result = service.createJobListing(dto);

        assertEquals("Developer", result.getTitle());
        assertEquals(100, result.getEmployerId());
        verify(jobListingRepo).save(any(JobListing.class));
    }*/

    @Test
    void testGetJobListingById_Success() {
        when(jobListingRepo.findById(1)).thenReturn(Optional.of(jobListing));
        JobListingDto result = service.getJobListingById(1);
        assertEquals("Developer", result.getTitle());
        assertEquals(100, result.getEmployerId());
    }

    @Test
    void testGetJobListingById_NotFound() {
        when(jobListingRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getJobListingById(1));
    }

    @Test
    void testDeleteJobListing_Success() {
        when(jobListingRepo.existsById(1)).thenReturn(true);
        service.deleteJobListing(1);
        verify(jobListingRepo).deleteById(1);
    }
}
