package com.paulohenrique.library.service;

import com.paulohenrique.library.data.dto.request.BookEditRequestDto;
import com.paulohenrique.library.data.dto.request.BookRequestDto;
import com.paulohenrique.library.data.dto.response.GeneralResponseDto;
import com.paulohenrique.library.data.entity.Book;
import com.paulohenrique.library.exception.LibraryApiException;
import com.paulohenrique.library.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public ResponseEntity<Book> save(BookRequestDto bookRequestDto) {
        Book book = bookRepository.save(new Book(bookRequestDto.title(), bookRequestDto.author(), bookRequestDto.releaseDate()));
        return ResponseEntity.ok(book);
    }

    public ResponseEntity<GeneralResponseDto> deleteBook(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isEmpty()) {
            throw new LibraryApiException(HttpStatus.NOT_FOUND, "Book not found");
        }

        bookRepository.delete(book.get());
        return ResponseEntity.ok(new GeneralResponseDto(true, "Book deleted successfully"));
    }

    public ResponseEntity<Book> editBook(Long bookId, BookEditRequestDto dto) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (bookOptional.isEmpty()) {
            throw new LibraryApiException(HttpStatus.NOT_FOUND, "Book not found");
        }

        Book book = bookOptional.get();

        if (dto.title() != null && !dto.title().isBlank()) {
            book.setTitle(dto.title());
        }

        if (dto.author() != null && !dto.author().isBlank()) {
            book.setAuthor(dto.author());
        }

        if (dto.releaseDate() != null) {
            book.setReleaseDate(dto.releaseDate());
        }

        Book updated = bookRepository.save(book);
        return ResponseEntity.ok(updated);
    }

    public ResponseEntity<Book> findById(Long id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            throw new LibraryApiException(HttpStatus.NOT_FOUND, "Book not found");
        }

        return ResponseEntity.ok(book.get());
    }

    public ResponseEntity<Page<Book>> findAll(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);

        if (books.isEmpty()) {
            throw new LibraryApiException(HttpStatus.NOT_FOUND, "No books found");
        }

        return ResponseEntity.ok(books);
    }
}
