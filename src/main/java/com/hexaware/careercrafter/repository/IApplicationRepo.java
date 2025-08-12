package com.hexaware.careercrafter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hexaware.careercrafter.entities.Application;
import java.util.List;

public interface IApplicationRepo extends JpaRepository<Application, Integer> {

   
}
