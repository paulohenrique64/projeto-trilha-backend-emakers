package com.paulohenrique.library.controller;

import com.paulohenrique.library.data.dto.response.GeneralResponseDto;
import com.paulohenrique.library.data.entity.Loan;
import com.paulohenrique.library.service.LoanService;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @Operation(summary = "Realizar empréstimo de um livro", description = "Cria um novo empréstimo para o livro informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empréstimo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    })
    @PostMapping("/{bookId}")
    @Transactional
    public ResponseEntity<GeneralResponseDto> createLoan(@PathVariable int bookId, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        return loanService.createLoan(bookId, usernamePasswordAuthenticationToken);
    }

    @Operation(summary = "Devolver um livro emprestado", description = "Efetua a devolução de um livro emprestado, encerrando o empréstimo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro devolvido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado", content = @Content)
    })
    @DeleteMapping("/return/{loanId}")
    public ResponseEntity<GeneralResponseDto> returnBook(@PathVariable int loanId, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        return loanService.returnBook(loanId, usernamePasswordAuthenticationToken);
    }

    @Operation(summary = "Listar empréstimos do usuário", description = "Retorna todos os empréstimos realizados pelo usuário autenticado.")
    @ApiResponse(responseCode = "200", description = "Lista de empréstimos do usuário")
    @GetMapping("/user-loans")
    public ResponseEntity<Page<Loan>> listAllUserLoans(@PageableDefault(size = 10, sort = "loanId") Pageable pageable, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        return loanService.listAllUserLoans(pageable, usernamePasswordAuthenticationToken);
    }

    @Operation(summary = "Excluir empréstimo | \uD83D\uDD10 Requer ADMIN", description = "Remove um empréstimo do sistema com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empréstimo removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado", content = @Content)
    })
    @DeleteMapping("/{loanId}")
    @Transactional
    public ResponseEntity<GeneralResponseDto> deleteLoan(@PathVariable("loanId") int loanId) {
        return loanService.deleteLoanById(loanId);
    }

    @Operation(summary = "Buscar empréstimo por ID | \uD83D\uDD10 Requer ADMIN", description = "Retorna as informações de um empréstimo específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empréstimo encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado", content = @Content)
    })
    @GetMapping("/{loanId}")
    public ResponseEntity<Loan> getLoan(@PathVariable("loanId") int loanId) {
        return loanService.listLoanById(loanId);
    }

    @Operation(summary = "Listar todos os empréstimos | \uD83D\uDD10 Requer ADMIN", description = "Retorna todos os empréstimos cadastrados no sistema (somente administradores).")
    @ApiResponse(responseCode = "200", description = "Lista de todos os empréstimos retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<Loan>> listAllLoans(@PageableDefault(size = 10, sort = "loanId") Pageable pageable) {
        return loanService.listAllLoans(pageable);
    }
}
