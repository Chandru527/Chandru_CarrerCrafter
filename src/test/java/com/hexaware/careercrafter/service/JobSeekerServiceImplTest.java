package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.JobSeekerDto;
import com.hexaware.careercrafter.entities.JobSeeker;
import com.hexaware.careercrafter.entities.User;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IJobSeekerRepo;
import com.hexaware.careercrafter.repository.IUserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobSeekerServiceImplTest {

    @Mock private IJobSeekerRepo jobSeekerRepo;
    @Mock private IUserRepo userRepo;

    @InjectMocks private JobSeekerServiceImpl service;

    private JobSeeker seeker;
    private JobSeekerDto dto;
    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1);

        seeker = new JobSeeker();
        seeker.setJobSeekerId(1);
        seeker.setFullName("John Doe");

        dto = new JobSeekerDto();
        dto.setFullName("John Doe");
        dto.setUserId(1);
    }

    @Test
    void testCreateJobSeeker_Success() {
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(jobSeekerRepo.save(any(JobSeeker.class))).thenReturn(seeker);

        JobSeekerDto result = service.createJobSeeker(dto);

        assertEquals("John Doe", result.getFullName());
        verify(jobSeekerRepo).save(any(JobSeeker.class));
    }

    @Test
    void testGetJobSeekerById_Success() {
        when(jobSeekerRepo.findById(1)).thenReturn(Optional.of(seeker));

        JobSeekerDto result = service.getJobSeekerById(1);

        assertEquals("John Doe", result.getFullName());
    }

    @Test
    void testGetJobSeekerById_NotFound() {
        when(jobSeekerRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getJobSeekerById(1));
    }

    @Test
    void testDeleteJobSeeker_Success() {
        when(jobSeekerRepo.existsById(1)).thenReturn(true);

        service.deleteJobSeeker(1);

        verify(jobSeekerRepo).deleteById(1);
    }
}
