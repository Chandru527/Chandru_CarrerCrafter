package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.JobSeekerDto;
import com.hexaware.careercrafter.entities.JobSeeker;
import com.hexaware.careercrafter.entities.User;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IJobSeekerRepo;
import com.hexaware.careercrafter.repository.IUserRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
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
    void createJobSeeker_success() {
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(jobSeekerRepo.save(any(JobSeeker.class))).thenReturn(seeker);
        JobSeekerDto result = service.createJobSeeker(dto);
        assertEquals("John Doe", result.getFullName());
    }

    @Test
    void createJobSeeker_missingName() {
        dto.setFullName("");
        assertThrows(InvalidRequestException.class, () -> service.createJobSeeker(dto));
    }

    @Test
    void getJobSeekerById_success() {
        when(jobSeekerRepo.findById(1)).thenReturn(Optional.of(seeker));
        JobSeekerDto result = service.getJobSeekerById(1);
        assertEquals("John Doe", result.getFullName());
    }

    @Test
    void getJobSeekerById_notFound() {
        when(jobSeekerRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getJobSeekerById(1));
    }

    @Test
    void getAllJobSeekers_success() {
        when(jobSeekerRepo.findAll()).thenReturn(Arrays.asList(seeker));
        assertEquals(1, service.getAllJobSeekers().size());
    }

    @Test
    void updateJobSeeker_success() {
        when(jobSeekerRepo.findById(1)).thenReturn(Optional.of(seeker));
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(jobSeekerRepo.save(any(JobSeeker.class))).thenReturn(seeker);

        dto.setEducation("BSc");
        JobSeekerDto result = service.updateJobSeeker(1, dto);
        assertEquals("BSc", dto.getEducation());
    }

    @Test
    void deleteJobSeeker_success() {
        when(jobSeekerRepo.existsById(1)).thenReturn(true);
        service.deleteJobSeeker(1);
        verify(jobSeekerRepo).deleteById(1);
    }

    @Test
    void deleteJobSeeker_notFound() {
        when(jobSeekerRepo.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.deleteJobSeeker(1));
    }
}
