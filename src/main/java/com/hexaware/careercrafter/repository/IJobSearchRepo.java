package com.hexaware.careercrafter.repository;

import com.hexaware.careercrafter.entities.JobSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IJobSearchRepo extends JpaRepository<JobSearch, Integer> {
    List<JobSearch> findByJobSeeker_JobSeekerId(int jobSeekerId);
}
