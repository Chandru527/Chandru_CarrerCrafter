package com.hexaware.careercrafter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hexaware.careercrafter.entities.Employee;
import java.util.List;

public interface IEmployeeRepo extends JpaRepository<Employee, Integer> {

    List<Employee> findByCompanyNameContaining(String keyword); 
}
