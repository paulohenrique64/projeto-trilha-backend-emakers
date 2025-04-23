package com.paulohenrique.library.controller;

import com.paulohenrique.library.data.dto.request.AccountDeleteRequestDto;
import com.paulohenrique.library.data.dto.request.UpdateUserRequestDto;
import com.paulohenrique.library.data.dto.response.GeneralResponseDto;
import com.paulohenrique.library.data.entity.Book;
import com.paulohenrique.library.data.entity.User;
import com.paulohenrique.library.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Excluir conta do usuário autenticado", description = "Remove a conta do usuário atualmente autenticado após validação de credenciais.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta removida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Credenciais inválidas", content = @Content)
    })
    @DeleteMapping
    @Transactional
    public ResponseEntity<GeneralResponseDto> deleteAccount(@RequestBody @Validated AccountDeleteRequestDto deleteAccountRequestDto, UsernamePasswordAuthenticationToken authenticationToken) {
        return userService.deleteAccount(deleteAccountRequestDto, authenticationToken);
    }

    @Operation(summary = "Buscar dados do usuário autenticado", description = "Retorna os dados do usuário atualmente autenticado.")
    @ApiResponse(responseCode = "200", description = "Dados do usuário retornados com sucesso")
    @GetMapping("/user-data")
    public ResponseEntity<User> getUserData(UsernamePasswordAuthenticationToken authenticationToken) {
        return userService.getUserData(authenticationToken);
    }

    @Operation(summary = "Atualizar dados do usuário autenticado", description = "Permite que o usuário autenticado atualize suas próprias informações.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PatchMapping
    @Transactional
    public ResponseEntity<User> updateData(@RequestBody @Validated UpdateUserRequestDto updateUserRequestDto, UsernamePasswordAuthenticationToken authenticationToken) {
        return userService.updateData(updateUserRequestDto, authenticationToken);
    }

    @Operation(summary = "Excluir usuário por ID | \uD83D\uDD10 Requer ADMIN", description = "Remove um usuário do sistema com base no ID (somente administradores).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    @Transactional
    public ResponseEntity<GeneralResponseDto> deleteUser(@PathVariable int userId) {
        return userService.deleteUser(userId);
    }

    @Operation(summary = "Buscar usuário por ID | \uD83D\uDD10 Requer ADMIN", description = "Retorna os dados de um usuário específico com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<User> listUser(@PathVariable int userId) {
        return userService.listUser(userId);
    }

    @Operation(summary = "Atualizar dados de um usuário por ID | \uD83D\uDD10 Requer ADMIN", description = "Atualiza as informações de um usuário específico (somente administradores).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do usuário atualizados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{userId}")
    @Transactional
    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody @Validated UpdateUserRequestDto updateUserRequestDto) {
        return userService.updateUser(userId, updateUserRequestDto);
    }

    @Operation(summary = "Listar todos os usuários | \uD83D\uDD10 Requer ADMIN", description = "Retorna uma lista paginada de todos os usuários cadastrados (somente administradores).")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<User>> listAllUsers(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return userService.listAllUsers(pageable);
    }
}
