package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.entities.User;
import java.util.List;

public interface IUserService {
    User saveUser(User user);
    User getUserById(int id);
    List<User> getAllUsers();
    void deleteUser(int id);
}
