package com.hexaware.careercrafter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hexaware.careercrafter.entities.User;
import java.util.List;

public interface IUserRepo extends JpaRepository<User, Integer> {

    User findByEmail(String email); 

   
}
