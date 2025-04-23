package com.paulohenrique.library.controller;

import com.paulohenrique.library.data.dto.response.GeneralResponseDto;
import com.paulohenrique.library.data.entity.Loan;
import com.paulohenrique.library.service.LoanService;
import jakarta.annotation.security.RolesAllowed;
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

    @PostMapping
    public ResponseEntity<GeneralResponseDto> createLoan(@RequestParam("bookId") int bookId, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        return loanService.createLoan(bookId, usernamePasswordAuthenticationToken);
    }

    @DeleteMapping
    public ResponseEntity<GeneralResponseDto> returnBook(@RequestParam("loanId") int loanId, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        return loanService.returnBook(loanId, usernamePasswordAuthenticationToken);
    }

    @GetMapping("/user-loans")
    public ResponseEntity<Page<Loan>> listAllUserLoans(@PageableDefault(size = 10, sort = "loanId") Pageable pageable, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        return loanService.listAllUserLoans(pageable, usernamePasswordAuthenticationToken);
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<GeneralResponseDto> deleteLoan(@PathVariable("loanId") int loanId) {
        return loanService.deleteLoanById(loanId);
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<Loan> getLoan(@PathVariable("loanId") int loanId) {
        return loanService.listLoanById(loanId);
    }

    @GetMapping
    public ResponseEntity<Page<Loan>> listAllLoans(@PageableDefault(size = 10, sort = "loanId") Pageable pageable) {
        return loanService.listAllLoans(pageable);
    }
}
