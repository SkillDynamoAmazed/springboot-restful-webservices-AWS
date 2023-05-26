package net.javaguides.springboot.service;

import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto user);
    UserDto getUserById(Long userId);

    List<UserDto>findAllUsers();

    UserDto updateUser(UserDto user,Long id);

    void deleteUserById(Long id);
}
