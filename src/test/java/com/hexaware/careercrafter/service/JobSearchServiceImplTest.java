package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.JobListingDto;
import com.hexaware.careercrafter.dto.JobSearchDto;
import com.hexaware.careercrafter.entities.JobListing;
import com.hexaware.careercrafter.entities.JobSearch;
import com.hexaware.careercrafter.entities.JobSeeker;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IJobListingRepo;
import com.hexaware.careercrafter.repository.IJobSearchRepo;
import com.hexaware.careercrafter.repository.IJobSeekerRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
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
        dto.setSearchId(1);
        dto.setJobSeekerId(1);
        dto.setKeywords("Java");
        dto.setLocation("NY");
    }

    @Test
    void createSearch_success() {
        when(jobSeekerRepo.findById(1)).thenReturn(Optional.of(seeker));
        when(jobSearchRepo.save(any(JobSearch.class))).thenReturn(search);

        JobSearchDto result = service.createSearch(dto);
        assertEquals("Java Developer", result.getKeywords());
    }

    @Test
    void createSearch_jobSeekerNotFound() {
        when(jobSeekerRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.createSearch(dto));
    }

    @Test
    void getSearchById_success() {
        when(jobSearchRepo.findById(1)).thenReturn(Optional.of(search));
        JobSearchDto result = service.getSearchById(1);
        assertEquals("Java Developer", result.getKeywords());
    }

    @Test
    void getSearchById_notFound() {
        when(jobSearchRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getSearchById(1));
    }

    @Test
    void getSearchesByJobSeeker_success() {
        when(jobSearchRepo.findByJobSeeker_JobSeekerId(1)).thenReturn(Arrays.asList(search));
        List<JobSearchDto> list = service.getSearchesByJobSeeker(1);
        assertEquals(1, list.size());
    }

    @Test
    void updateSearch_success() {
        when(jobSearchRepo.findById(1)).thenReturn(Optional.of(search));
        when(jobSearchRepo.save(any(JobSearch.class))).thenReturn(search);

        dto.setKeywords("Updated");
        JobSearchDto result = service.updateSearch(dto);
        assertEquals("Updated", result.getKeywords());
    }

    @Test
    void deleteSearch_success() {
        when(jobSearchRepo.existsById(1)).thenReturn(true);
        service.deleteSearch(1);
        verify(jobSearchRepo).deleteById(1);
    }

    @Test
    void deleteSearch_notFound() {
        when(jobSearchRepo.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.deleteSearch(1));
    }

    @Test
    void recommendJobs_success() {
        JobListing listing = new JobListing();
        listing.setJobListingId(1);
        listing.setTitle("Java Developer");
        listing.setDescription("Developer role");
        listing.setLocation("NY");

        when(jobListingRepo.findAll()).thenReturn(Arrays.asList(listing));

        List<JobListingDto> jobs = service.recommendJobs(dto);
        assertEquals(1, jobs.size());
    }
}
