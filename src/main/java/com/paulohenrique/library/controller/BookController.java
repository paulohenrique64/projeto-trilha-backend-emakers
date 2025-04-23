package com.paulohenrique.library.controller;

import com.paulohenrique.library.data.dto.request.BookEditRequestDto;
import com.paulohenrique.library.data.dto.request.BookRequestDto;
import com.paulohenrique.library.data.dto.response.GeneralResponseDto;
import com.paulohenrique.library.data.entity.Book;
import com.paulohenrique.library.service.BookService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Page<Book>> findAllBooks(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> listBook(@PathVariable Long bookId) {
        return bookService.findById(bookId);
    }

    @PostMapping("/")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Book> save(@RequestBody BookRequestDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> editBook(@PathVariable Long id, @RequestBody @Valid BookEditRequestDto dto) {
        return bookService.editBook(id, dto);
    }

    @DeleteMapping("/")
    @RolesAllowed("ADMIN")
    public ResponseEntity<GeneralResponseDto> deleteBook(@RequestParam("bookId") Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok(new GeneralResponseDto(true, "Book deleted"));
    }
}