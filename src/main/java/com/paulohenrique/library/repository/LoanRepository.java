package com.paulohenrique.library.repository;

import com.paulohenrique.library.data.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findLoanByLoanId(int loanId);
    List<Loan> findAllByBook_BookId(int bookId);
    Page<Loan> findAllByUser_UserId(Integer userId, Pageable pageable);
    List<Loan> findAllByUser_UserId(int bookId);
}
