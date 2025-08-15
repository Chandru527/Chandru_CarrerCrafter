package com.hexaware.careercrafter.service;

/*
 * 
 * Author: Chandru
 * Date: 13-Aug-2025
 * 
 * 
 */

import com.hexaware.careercrafter.dto.JobSearchDto;
import com.hexaware.careercrafter.entities.JobSearch;
import com.hexaware.careercrafter.entities.JobSeeker;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IJobListingRepo;
import com.hexaware.careercrafter.repository.IJobSearchRepo;
import com.hexaware.careercrafter.repository.IJobSeekerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobSearchServiceImplTest {

    @Mock private IJobSearchRepo jobSearchRepo;
    @Mock private IJobSeekerRepo jobSeekerRepo;
    @Mock private IJobListingRepo jobListingRepo;

    @InjectMocks private JobSearchServiceImpl service;

    private JobSeeker seeker;
    private JobSearch search;
    private JobSearchDto dto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        seeker = new JobSeeker();
        seeker.setJobSeekerId(1);

        search = new JobSearch();
        search.setSearchId(1);
        search.setJobSeeker(seeker);
        search.setKeywords("Java Developer");
        search.setLocation("NY");
        search.setIndustry("IT");

        dto = new JobSearchDto();
        dto.setJobSeekerId(1);
        dto.setKeywords("Java Developer");
        dto.setLocation("NY");
    }

    @Test
    void testCreateSearch_Success() {
        when(jobSeekerRepo.findById(1)).thenReturn(Optional.of(seeker));
        when(jobSearchRepo.save(any(JobSearch.class))).thenReturn(search);

        JobSearchDto result = service.createSearch(dto);

        assertEquals("Java Developer", result.getKeywords());
        verify(jobSearchRepo).save(any(JobSearch.class));
    }

    @Test
    void testGetSearchById_Success() {
        when(jobSearchRepo.findById(1)).thenReturn(Optional.of(search));

        JobSearchDto result = service.getSearchById(1);

        assertEquals("Java Developer", result.getKeywords());
    }

    @Test
    void testGetSearchById_NotFound() {
        when(jobSearchRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getSearchById(1));
    }

    @Test
    void testDeleteSearch_Success() {
        when(jobSearchRepo.existsById(1)).thenReturn(true);

        service.deleteSearch(1);

        verify(jobSearchRepo).deleteById(1);
    }
}
