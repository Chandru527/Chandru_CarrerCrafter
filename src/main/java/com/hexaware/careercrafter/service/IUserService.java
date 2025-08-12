package com.hexaware.careercrafter.service;

import java.util.List;
import com.hexaware.careercrafter.dto.UserDto;

public interface IUserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(int id);
    List<UserDto> getAllUsers();
    UserDto updateUser(int id, UserDto userDto);
    void deleteUser(int id);
}
