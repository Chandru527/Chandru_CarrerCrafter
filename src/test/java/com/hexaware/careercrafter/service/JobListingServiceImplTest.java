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
    @Mock
    private IEmployeeRepo employeeRepo;

    @InjectMocks
    private JobListingServiceImpl service;

    private JobListing jobListing;
    private JobListingDto dto;
    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee();
        employee.setEmployeeId(1);

        dto = new JobListingDto();
        dto.setTitle("Java Developer");
        dto.setEmployeeId(1);

        jobListing = new JobListing();
        jobListing.setJobListingId(1);
        jobListing.setTitle("Java Developer");
        jobListing.setEmployee(employee);
    }

    @Test
    void testCreateJobListing_Success() {
        Employee employee = new Employee();
        employee.setEmployeeId(1);

        when(employeeRepo.findById(1)).thenReturn(Optional.of(employee));
        when(jobListingRepo.save(any(JobListing.class))).thenReturn(jobListing);

        JobListingDto result = service.createJobListing(dto);

        assertEquals("Java Developer", result.getTitle());
        verify(jobListingRepo, times(1)).save(any(JobListing.class));
    }

    @Test
    void testCreateJobListing_MissingTitle_ThrowsException() {
        dto.setTitle("");
        assertThrows(InvalidRequestException.class, () -> service.createJobListing(dto));
    }

    @Test
    void testGetJobListingById_Success() {
        when(jobListingRepo.findById(1)).thenReturn(Optional.of(jobListing));
        JobListingDto result = service.getJobListingById(1);
        assertEquals("Java Developer", result.getTitle());
    }

    @Test
    void testGetJobListingById_NotFound() {
        when(jobListingRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getJobListingById(1));
    }

    @Test
    void testGetAllJobListings() {
        when(jobListingRepo.findAll()).thenReturn(Arrays.asList(jobListing));
        List<JobListingDto> result = service.getAllJobListings();
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateJobListing_Success() {
        when(jobListingRepo.findById(1)).thenReturn(Optional.of(jobListing));
        when(employeeRepo.findById(1)).thenReturn(Optional.of(employee));
        when(jobListingRepo.save(any(JobListing.class))).thenReturn(jobListing);

        dto.setDescription("Updated Desc");
        JobListingDto result = service.updateJobListing(1, dto);
        assertEquals("Updated Desc", dto.getDescription());
    }

    @Test
    void testDeleteJobListing_Success() {
        when(jobListingRepo.existsById(1)).thenReturn(true);
        service.deleteJobListing(1);
        verify(jobListingRepo).deleteById(1);
    }

    @Test
    void testDeleteJobListing_NotFound() {
        when(jobListingRepo.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.deleteJobListing(1));
    }
}
