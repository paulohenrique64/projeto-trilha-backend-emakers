package com.paulohenrique.library.data.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int loanId;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name="book_id")
    private Book book;

    @Column(nullable = false)
    private LocalDate loanDate;

    public Loan() {}

    public Loan(User user, Book book, LocalDate loanDate) {
        this.user = user;
        this.book = book;
        this.loanDate = loanDate;
    }

    public int getLoanId() {
        return loanId;
    }

    public int getUserId() {
        return user.getUserId();
    }

    public int getBookId() {
        return book.getBookId();
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }
}
