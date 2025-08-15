package com.hexaware.careercrafter.repository;

/*
 * 
 * Author: Chandru
 * Date: 13-Aug-2025
 * 
 * 
 */

import org.springframework.data.jpa.repository.JpaRepository;
import com.hexaware.careercrafter.entities.Resume;

public interface IResumeRepo extends JpaRepository<Resume, Integer> {

    
}
