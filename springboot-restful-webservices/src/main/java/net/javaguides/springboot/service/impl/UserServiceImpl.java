package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.exception.EmailAlreadyExistException;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.mapper.AutoUserMapper;
import net.javaguides.springboot.mapper.UserMapper;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

   private  ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
//        User user = new
//                User(userDto.getId(), userDto.getFirstName(), userDto.getLastName(), userDto.getEmail());
//        User user = UserMapper.maptoUser(userDto);
//        User user = modelMapper.map(userDto,User.class);

//        USING MAPSTRUCT.....
        User user = AutoUserMapper.MAPPER.maptoUser(userDto);

//        String email = user.getEmail();
//
//
//        List<User>Users = userRepository.findAll().stream().filter(user1->user1.getEmail().equals(email)).collect(Collectors.toList());
//
//        if(Users.size()>0){
//            throw new EmailAlreadyExistException("USER WITH EMAIL ALREADY EXIST");
//        }

        Optional<User> user1 = userRepository.findByEmail(userDto.getEmail());
        if(user1.isPresent()){
            throw new EmailAlreadyExistException("EMAIL ALREADY EXIST FOR THE USER ");
        }

        User savedUser = userRepository.save(user);

//        UserDto dt = new UserDto(savedUser.getId(), savedUser.getFirstName(), savedUser.getLastName(), savedUser.getEmail());
//        UserDto userDto1 = UserMapper.maptoUserDto(savedUser);
//        UserDto userDto1 = modelMapper.map(user,UserDto.class);

//        USING MAPSTRUCT...
        UserDto userDto1 = AutoUserMapper.MAPPER.maptoUserDto(savedUser);

        return userDto1;

    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
//        UserDto userDto = UserMapper.maptoUserDto(user);
//        UserDto userDto = modelMapper.map(user,UserDto.class);

//USING MAPSTRUCT ....
        UserDto userDto = AutoUserMapper.MAPPER.maptoUserDto(user);
        return userDto;
    }

    @Override
    public List<UserDto> findAllUsers() {

        List<User> users = userRepository.findAll();
//        List<UserDto> userDtos = new ArrayList<>();
//        for (int i = 0; i < users.size(); i++) {
//            User user1 = users.get(i);
//            UserDto userDto = UserMapper.maptoUserDto(user1);
//            userDtos.add(userDto);
//        }

//        using map struct
        List<UserDto>userDtos = users.stream().map((user)->AutoUserMapper.MAPPER.maptoUserDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDto updateUser(UserDto user, Long id) {
        User updatedUser = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("user","id",user.getId()));
        updatedUser.setId(id);
        updatedUser.setEmail(user.getEmail());
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        User savedUser = userRepository.save(updatedUser);
//        UserDto userDto = UserMapper.maptoUserDto(savedUser);


//        UserDto userDto = modelMapper.map(savedUser,UserDto.class);

//USING MAPSTRUCT....
        UserDto userDto = AutoUserMapper.MAPPER.maptoUserDto(savedUser);
        return userDto;


    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


}
