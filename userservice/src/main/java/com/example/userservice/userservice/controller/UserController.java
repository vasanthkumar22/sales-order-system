package com.example.userservice.userservice.controller;

import com.example.userservice.userservice.dto.UserDto;
import com.example.userservice.userservice.model.User;
import com.example.userservice.userservice.repository.UserRepository;
import com.example.userservice.userservice.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users/list")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<UserDto> getUsersByUsername(@PathVariable String username){
        User user = userRepository.findByUsername(username);
        UserDto userDto = UserMapper.mapUserToUserDto(user);
        return ResponseEntity.ok(userDto);

    }

}
