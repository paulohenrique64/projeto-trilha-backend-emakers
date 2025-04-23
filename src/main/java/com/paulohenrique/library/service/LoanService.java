package com.paulohenrique.library.service;

import com.paulohenrique.library.data.dto.response.GeneralResponseDto;
import com.paulohenrique.library.data.entity.Book;
import com.paulohenrique.library.data.entity.Loan;
import com.paulohenrique.library.data.entity.User;
import com.paulohenrique.library.exception.LibraryApiException;
import com.paulohenrique.library.exception.UnauthorizedException;
import com.paulohenrique.library.repository.BookRepository;
import com.paulohenrique.library.repository.LoanRepository;
import com.paulohenrique.library.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public ResponseEntity<GeneralResponseDto> createLoan(int bookId, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        Optional<User> userOptional = userRepository.findByUsername(usernamePasswordAuthenticationToken.getName());
        Optional<Book> bookOptional = bookRepository.findByBookId(bookId);

        if (userOptional.isEmpty()) {
            throw new LibraryApiException(HttpStatus.NOT_FOUND, "User not found");
        }

        if (bookOptional.isEmpty()) {
            throw new LibraryApiException(HttpStatus.NOT_FOUND, "Book not found");
        }

        List<Loan> loanList = loanRepository.findAllByBook_BookId(bookId);

        if (!loanList.isEmpty()) {
            throw new LibraryApiException(HttpStatus.CONFLICT, "Loan already exists");
        }

        loanRepository.save(new Loan(userOptional.get(), bookOptional.get(), LocalDate.now()));

        return ResponseEntity.ok().body(new GeneralResponseDto(true, "Loan created successfully"));
    }

    public ResponseEntity<GeneralResponseDto> deleteLoan(int loanId, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        Optional<User> userOptional = userRepository.findByUsername(usernamePasswordAuthenticationToken.getName());

        if (userOptional.isEmpty()) {
            throw new LibraryApiException(HttpStatus.NOT_FOUND, "User not found");
        }

        User user = userOptional.get();

        Optional<Loan> loanOptional = loanRepository.findLoanByLoanId(loanId);

        if (loanOptional.isEmpty()) {
            throw new LibraryApiException(HttpStatus.NOT_FOUND, "Loan not found");
        }

        Loan loan = loanOptional.get();

        if (loan.getUserId() != user.getUserId()) {
            throw new UnauthorizedException("You do not have permission to delete this loan");
        }

        loanRepository.delete(loanOptional.get());

        return ResponseEntity.ok().body(new GeneralResponseDto(true, "Loan deleted successfully"));
    }

    public ResponseEntity<Page<Loan>> listAllUserLoans(Pageable pageable, UsernamePasswordAuthenticationToken authentication) {
        Optional<User> userOptional = userRepository.findByUsername(authentication.getName());

        if (userOptional.isEmpty()) {
            throw new LibraryApiException(HttpStatus.NOT_FOUND, "User not found");
        }

        Page<Loan> loanPage = loanRepository.findAllByUser_UserId(userOptional.get().getUserId(), pageable);

        if (loanPage.isEmpty()) {
            throw new LibraryApiException(HttpStatus.NOT_FOUND, "No loans found for user");
        }

        return ResponseEntity.ok(loanPage);
    }

    public ResponseEntity<GeneralResponseDto> deleteLoanById(int loanId) {
        Optional<Loan> loanOptional = loanRepository.findLoanByLoanId(loanId);

        if (loanOptional.isEmpty()) {
            throw new LibraryApiException(HttpStatus.NOT_FOUND, "Loan not found");
        }

        loanRepository.delete(loanOptional.get());

        return ResponseEntity.ok().body(new GeneralResponseDto(true, "Loan deleted successfully"));
    }

    public ResponseEntity<Loan> listLoanById(int loanId) {
        Optional<Loan> loanOptional = loanRepository.findLoanByLoanId(loanId);

        if (loanOptional.isEmpty()) {
            throw new LibraryApiException(HttpStatus.NOT_FOUND, "Loan not found");
        }

        return ResponseEntity.ok().body(loanOptional.get());
    }

    public ResponseEntity<Page<Loan>> listAllLoans(Pageable pageable) {
        Page<Loan> loanPage = loanRepository.findAll(pageable);

        if (loanPage.isEmpty()) {
            throw new LibraryApiException(HttpStatus.NOT_FOUND, "No loans found");
        }

        return ResponseEntity.ok(loanPage);
    }
}


