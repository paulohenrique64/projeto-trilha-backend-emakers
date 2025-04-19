package com.paulohenrique.library.data.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int loanId;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name="person_id")
    private Person person;

    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name="book_id")
    private Book book;

    @Basic(optional=false)
    private LocalDate loanDate;

    public Loan() {}

    public Loan(Person person, Book book, LocalDate loanDate) {
        this.person = person;
        this.book = book;
        this.loanDate = loanDate;
    }

    public int getLoanId() {
        return loanId;
    }

    public int getPersonId() {
        return person.getPersonId();
    }

    public int getBookId() {
        return book.getBookId();
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }
}
