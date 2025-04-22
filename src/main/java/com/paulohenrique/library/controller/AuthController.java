package com.paulohenrique.library.controller;

import com.paulohenrique.library.data.dto.request.LoginRequest;
import com.paulohenrique.library.data.dto.request.RegisterRequest;
import com.paulohenrique.library.data.dto.response.LoginResponseDto;
import com.paulohenrique.library.data.dto.response.RegisterResponseDto;
import com.paulohenrique.library.data.dto.response.UserResponseDto;
import com.paulohenrique.library.data.entity.User;
import com.paulohenrique.library.repository.UserRepository;
import com.paulohenrique.library.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequest loginRequest) {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());

        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);

        User user = (User) authenticationResponse.getPrincipal();

        if (user != null) {
            var token = jwtService.generateToken((UserDetails) authenticationResponse.getPrincipal());

            if (token != null) {
                return ResponseEntity
                        .ok()
                        .body(new LoginResponseDto(true, "Login successful", token, new UserResponseDto(user.getUserId(), user.getUsername())));
            }
        }

        return ResponseEntity.internalServerError().build();
    }


    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequest registerRequest) {
        String password = registerRequest.password();
        String username = registerRequest.username();
        String cep = registerRequest.cep();

        if (username.length() < 3 || username.length() > 50) {
            return ResponseEntity.badRequest().body(new RegisterResponseDto(false, "Your username must be more than 2 and less than 51 characters long", null));
        }

        if (password.length() < 8 || password.length() > 16) {
            return ResponseEntity.badRequest().body(new RegisterResponseDto(false, "Your password must be more than 8 and less than 16 characters long", null));
        }

        if (userRepository.findByUsername(registerRequest.username()).isPresent()) {
            return ResponseEntity.badRequest().body(new RegisterResponseDto(false, "There is already a registered user with this name", null));
        }

        password = passwordEncoder.encode(password);
        User user = userRepository.save(new User(username, password, cep));
        return ResponseEntity.ok().body(new RegisterResponseDto(true, "User registered successfully", new UserResponseDto(user.getUserId(), user.getUsername())));
    }
}

