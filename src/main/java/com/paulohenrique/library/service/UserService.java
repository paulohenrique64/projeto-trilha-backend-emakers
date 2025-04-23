package com.paulohenrique.library.service;

import com.paulohenrique.library.data.dto.request.AccountDeleteRequestDto;
import com.paulohenrique.library.data.dto.request.UpdateUserRequestDto;
import com.paulohenrique.library.data.dto.response.GeneralResponseDto;
import com.paulohenrique.library.data.entity.User;
import com.paulohenrique.library.exception.LibraryApiException;
import com.paulohenrique.library.exception.UnauthorizedException;
import com.paulohenrique.library.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<GeneralResponseDto> deleteAccount(AccountDeleteRequestDto deleteAccountRequestDto, UsernamePasswordAuthenticationToken authenticationToken) {
        Optional<User> user = userRepository.findByUsername(authenticationToken.getName());

        if (user.isPresent()) {
            if (passwordEncoder.matches(deleteAccountRequestDto.password(), user.get().getPassword())) {
                userRepository.delete(user.get());
                return ResponseEntity.ok(new GeneralResponseDto(true, "You have successfully deleted"));
            }

            throw new UnauthorizedException("Invalid password");
        }

        throw new UnauthorizedException("Invalid username");
    }

    public ResponseEntity<User> updateData(UpdateUserRequestDto updateUserRequestDto, UsernamePasswordAuthenticationToken authenticationToken) {
        Optional<User> userOptional = userRepository.findByUsername(authenticationToken.getName());

        if (userOptional.isPresent()) {
            User user = compareAndUpdateUser(updateUserRequestDto, userOptional.get());
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }

        throw new UnauthorizedException("Invalid username");
    }

    public ResponseEntity<User> updateUser(int userId, UpdateUserRequestDto updateUserRequestDto) {
        Optional<User> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isPresent()) {
            User user = compareAndUpdateUser(updateUserRequestDto, userOptional.get());
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }

        throw new UnauthorizedException("Invalid username");
    }

    private User compareAndUpdateUser(UpdateUserRequestDto updateUserRequestDto, User user) {
        if (updateUserRequestDto.username() != null) {
            user.setName(updateUserRequestDto.username());
        }

        if (updateUserRequestDto.password() != null) {
            user.setPassword(passwordEncoder.encode(updateUserRequestDto.password()));
        }

        if (updateUserRequestDto.cep() != null) {
            user.setCep(updateUserRequestDto.cep());
        }

        return user;
    }

    public ResponseEntity<GeneralResponseDto> deleteUser(int userId) {
        Optional<User> user = userRepository.findByUserId(userId);

        if (user.isPresent()) {
            userRepository.delete(user.get());
            return ResponseEntity.ok(new GeneralResponseDto(true, "User deleted successfully"));
        }

        throw new UnauthorizedException("Invalid user id");
    }

    public ResponseEntity<User> getUserData(UsernamePasswordAuthenticationToken authenticationToken) {
        Optional<User> user = userRepository.findByUsername(authenticationToken.getName());

        if (user.isEmpty()) {
            throw new UnauthorizedException("Invalid username");
        }

        return ResponseEntity.ok(user.get());
    }

    public ResponseEntity<User> listUser(int userId) {
        Optional<User> user = userRepository.findByUserId(userId);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }

        throw new UnauthorizedException("Invalid user id");
    }

    public ResponseEntity<Page<User>> listAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        if (users.isEmpty()) {
            throw new LibraryApiException(HttpStatus.OK, "No users found");
        }

        return ResponseEntity.ok(users);
    }
}
