package com.paulohenrique.library.data.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="book")
public class Book {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int bookId;

    @Basic(optional=false)
    private String name;

    @Basic(optional=false)
    private String author;

    @Column(name="release_date")
    @Basic(optional=false)
    private LocalDate releaseDate;

    public Book() {}

    public Book(String name, String author, LocalDate releaseDate) {
        this.name = name;
        this.author = author;
        this.releaseDate = releaseDate;
    }

    public int getBookId() {
        return bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
