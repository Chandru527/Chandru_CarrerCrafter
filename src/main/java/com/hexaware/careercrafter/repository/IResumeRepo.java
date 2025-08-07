package com.hexaware.careercrafter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hexaware.careercrafter.entities.Resume;

public interface IResumeRepo extends JpaRepository<Resume, Integer> {

    Resume findByJobSeeker_JobSeekerId(int jobSeekerId); 
}
