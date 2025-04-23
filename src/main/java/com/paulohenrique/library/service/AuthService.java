package com.paulohenrique.library.service;

import com.paulohenrique.library.data.dto.request.LoginRequest;
import com.paulohenrique.library.data.dto.request.RegisterRequest;
import com.paulohenrique.library.data.dto.response.LoginResponseDto;
import com.paulohenrique.library.data.dto.response.RegisterResponseDto;
import com.paulohenrique.library.data.dto.response.UserResponseDto;
import com.paulohenrique.library.data.entity.Role;
import com.paulohenrique.library.data.entity.User;
import com.paulohenrique.library.exception.LibraryApiException;
import com.paulohenrique.library.repository.RoleRepository;
import com.paulohenrique.library.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthService(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<LoginResponseDto> login(LoginRequest loginRequest) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.email(), loginRequest.password());
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);
        User user = (User) authenticationResponse.getPrincipal();

        if (user != null) {
            var token = jwtService.generateToken((UserDetails) authenticationResponse.getPrincipal());
            if (token != null) {
                return ResponseEntity.ok().body(new LoginResponseDto(true, "Login successful", token, new UserResponseDto(user.getUserId(), user.getName(), user.getUsername(), user.getCep(), user.getState(), user.getRoleName())));
            }
        }

        throw new LibraryApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    }

    public ResponseEntity<RegisterResponseDto> register(RegisterRequest registerRequest) {
        String password = registerRequest.password();
        String email = registerRequest.email();
        String name = registerRequest.name();
        String cep = registerRequest.cep();
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        String state = "Unknown";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> body = response.getBody();
            state = (String) body.get("estado");
        }

        if (userRepository.findByEmail(registerRequest.email()).isPresent()) {
            throw new LibraryApiException(HttpStatus.BAD_REQUEST, "Email is already in use");
        }

        password = passwordEncoder.encode(password);
        Optional<Role> role = roleRepository.findById(2L);

        if (role.isEmpty()) {
            throw new LibraryApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }

        User user = userRepository.save(new User(name, email, password, cep, state, role.get()));

        return ResponseEntity.ok().body(new RegisterResponseDto(true, "User registered successfully", new UserResponseDto(user.getUserId(), user.getName(), user.getUsername(), user.getCep(), user.getState(), user.getRoleName())));
    }
}
