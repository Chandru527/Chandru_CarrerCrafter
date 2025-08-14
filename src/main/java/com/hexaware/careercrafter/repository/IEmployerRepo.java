package com.hexaware.careercrafter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hexaware.careercrafter.entities.Employer;
import java.util.List;

public interface IEmployerRepo extends JpaRepository<Employer, Integer> {

   
}
