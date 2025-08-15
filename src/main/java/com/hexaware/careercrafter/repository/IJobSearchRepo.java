package com.hexaware.careercrafter.repository;

/*
 * 
 * Author: Chandru
 * Date: 13-Aug-2025
 * 
 * 
 */

import com.hexaware.careercrafter.entities.JobSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IJobSearchRepo extends JpaRepository<JobSearch, Integer> {
    List<JobSearch> findByJobSeeker_JobSeekerId(int jobSeekerId);
}
