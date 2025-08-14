package com.hexaware.careercrafter.security;

import com.hexaware.careercrafter.entities.User;
import com.hexaware.careercrafter.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

/*
 * 
 * Author: Chandru
 * Date: 13-Aug-2025
 * 
 * 
 */


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new CustomUserDetails(user);
    }
}
