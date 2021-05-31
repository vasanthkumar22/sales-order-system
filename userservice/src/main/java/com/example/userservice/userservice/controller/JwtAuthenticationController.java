package com.example.userservice.userservice.controller;

import com.example.userservice.userservice.dto.JwtResponse;
import com.example.userservice.userservice.dto.LoginRequest;
import com.example.userservice.userservice.dto.SignupRequest;
import com.example.userservice.userservice.exception.PasswordNotMatchException;
import com.example.userservice.userservice.security.JwtUserDetailsService;
import com.example.userservice.userservice.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class JwtAuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationController.class);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest){
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(loginRequest.getUsername());

        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            throw new PasswordNotMatchException("Password doesn't match");
        }

        String jwtToken = jwtTokenUtil.generateToken(userDetails);
        LOGGER.info("Sending jwtToken back: {}", jwtToken);

        return ResponseEntity.ok(new JwtResponse(jwtToken));
    }

    @PostMapping("/signup")
    public ResponseEntity createNewUser(@RequestBody @Valid SignupRequest signupRequest) {
        LOGGER.info("Signing up new user: {}", signupRequest);
        jwtUserDetailsService.saveNewUser(
                signupRequest.getName(),
                signupRequest.getUsername(),
                signupRequest.getPassword(),
                signupRequest.getEmail(),
                signupRequest.getRoles()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
