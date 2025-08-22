package com.hexaware.careercrafter.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hexaware.careercrafter.entities.Resume;

public interface IResumeRepo extends JpaRepository<Resume, Integer> {
    Optional<Resume> findByJobSeeker_JobSeekerId(Integer jobSeekerId);
}
