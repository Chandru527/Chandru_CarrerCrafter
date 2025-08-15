package com.hexaware.careercrafter.repository;

/*
 * 
 * Author: Chandru
 * Date: 13-Aug-2025
 * 
 * 
 */

import org.springframework.data.jpa.repository.JpaRepository;
import com.hexaware.careercrafter.entities.User;
import java.util.List;

public interface IUserRepo extends JpaRepository<User, Integer> {

    User findByEmail(String email); 

   
}
