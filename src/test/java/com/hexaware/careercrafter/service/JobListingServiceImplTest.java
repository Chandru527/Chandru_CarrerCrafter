package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.JobListingDto;
import com.hexaware.careercrafter.entities.Employee;
import com.hexaware.careercrafter.entities.JobListing;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IEmployeeRepo;
import com.hexaware.careercrafter.repository.IJobListingRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobListingServiceImplTest {

    @Mock
    private IJobListingRepo jobListingRepo;

    @InjectMocks
    private JobListingServiceImpl service;

    private JobListing jobListing;
    private JobListingDto dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jobListing = new JobListing();
        jobListing.setJobListingId(1);
        jobListing.setTitle("Developer");

        dto = new JobListingDto();
        dto.setTitle("Developer");
    }

  /*  @Test
    void testCreateJobListing_Success() {
        when(jobListingRepo.save(any(JobListing.class))).thenReturn(jobListing);

        JobListingDto result = service.createJobListing(dto);

        assertEquals("Developer", result.getTitle());
        verify(jobListingRepo).save(any(JobListing.class));
    }*/




    @Test
    void testGetJobListingById_Success() {
        when(jobListingRepo.findById(1)).thenReturn(Optional.of(jobListing));
        JobListingDto result = service.getJobListingById(1);
        assertEquals("Developer", result.getTitle());
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
