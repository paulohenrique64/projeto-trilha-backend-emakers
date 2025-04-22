package com.paulohenrique.library.controller;

import com.paulohenrique.library.data.dto.request.LoginRequest;
import com.paulohenrique.library.data.dto.request.RegisterRequest;
import com.paulohenrique.library.data.entity.User;
import com.paulohenrique.library.repository.UserRepository;
import com.paulohenrique.library.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationObservationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());

        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);

        System.out.println(authenticationResponse);

        UserDetails userDetails = (UserDetails) authenticationResponse.getPrincipal();

        if (userDetails != null) {
            var token = jwtService.generateToken((UserDetails) authenticationResponse.getPrincipal());

            if (token != null) {
                System.out.println(token);
                return ResponseEntity.ok().header("Authorization", "Bearer " + token).build();
            }
        }

        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        String password = passwordEncoder.encode(registerRequest.password());

        User user = new User(registerRequest.username(), password, registerRequest.cep());
        user = userRepository.save(user);

        return ResponseEntity.ok().body("Sucessfully registred. Here is your user id " + user.getUserId());
    }
}
