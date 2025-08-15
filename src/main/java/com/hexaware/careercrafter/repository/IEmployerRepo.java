package com.hexaware.careercrafter.repository;

/*
 * 
 * Author: Chandru
 * Date: 13-Aug-2025
 * 
 * 
 */

import org.springframework.data.jpa.repository.JpaRepository;
import com.hexaware.careercrafter.entities.Employer;
import java.util.List;

public interface IEmployerRepo extends JpaRepository<Employer, Integer> {

   
}
