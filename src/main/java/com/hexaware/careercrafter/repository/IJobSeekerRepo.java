package com.hexaware.careercrafter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hexaware.careercrafter.entities.JobSeeker;
import java.util.List;

public interface IJobSeekerRepo extends JpaRepository<JobSeeker, Integer> {

    List<JobSeeker> findBySkillsContaining(String skill); 
}
