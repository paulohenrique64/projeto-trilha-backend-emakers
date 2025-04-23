package com.paulohenrique.library.controller;

import com.paulohenrique.library.data.dto.request.AccountDeleteRequestDto;
import com.paulohenrique.library.data.dto.request.UpdateUserRequestDto;
import com.paulohenrique.library.data.dto.response.GeneralResponseDto;
import com.paulohenrique.library.data.entity.Book;
import com.paulohenrique.library.data.entity.User;
import com.paulohenrique.library.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping
    public ResponseEntity<GeneralResponseDto> deleteAccount(@RequestBody AccountDeleteRequestDto deleteAccountRequestDto, UsernamePasswordAuthenticationToken authenticationToken) {
        return userService.deleteAccount(deleteAccountRequestDto, authenticationToken);
    }

    @GetMapping("/user-data")
    public ResponseEntity<User> getUserData(UsernamePasswordAuthenticationToken authenticationToken) {
        return userService.getUserData(authenticationToken);
    }

    @PatchMapping("/")
    public ResponseEntity<User> updateData(@RequestBody UpdateUserRequestDto updateUserRequestDto, UsernamePasswordAuthenticationToken authenticationToken) {
        return userService.updateData(updateUserRequestDto, authenticationToken);
    }

    @DeleteMapping("/{userId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<GeneralResponseDto> deleteUser(@PathVariable int userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<User> listUser(@PathVariable int userId) {
        return userService.listUser(userId);
    }

    @PatchMapping("/admin/{userId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody UpdateUserRequestDto updateUserRequestDto) {
        return userService.updateUser(userId, updateUserRequestDto);
    }

    @GetMapping("/")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Page<User>> adminListAllUsers(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return userService.listAllUsers(pageable);
    }
}
