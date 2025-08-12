package com.hexaware.careercrafter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hexaware.careercrafter.entities.JobListing;
import java.util.List;

public interface IJobListingRepo extends JpaRepository<JobListing, Integer> {

   
}
