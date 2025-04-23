package com.paulohenrique.library.service;

import com.paulohenrique.library.data.entity.User;
import com.paulohenrique.library.exception.LibraryApiException;
import com.paulohenrique.library.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        System.out.println(name);
        Optional<User> user = userRepository.findByEmail(name);

        if (user.isPresent()) {
            return user.get();
        }

        throw new LibraryApiException(HttpStatus.FORBIDDEN, "Email not found: " + name);
    }
}