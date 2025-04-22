package com.paulohenrique.library.controller;

import com.paulohenrique.library.data.dto.request.DeleteAccountRequest;
import com.paulohenrique.library.data.entity.User;
import com.paulohenrique.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(@RequestBody DeleteAccountRequest deleteAccountRequest, UsernamePasswordAuthenticationToken authenticationToken) {
        Optional<User> user = userRepository.findByUsername(authenticationToken.getName());

        if (user.isPresent()) {
            if (passwordEncoder.matches(deleteAccountRequest.password(), user.get().getPassword())) {
                userRepository.delete(user.get());
                return ResponseEntity.ok("Deleted account successfully");
            }

            return ResponseEntity.badRequest().body("Invalid password");
        }

        return ResponseEntity.badRequest().body("Invalid username");
    }
}
