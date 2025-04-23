package com.paulohenrique.library.controller;

import com.paulohenrique.library.data.dto.request.BookEditRequestDto;
import com.paulohenrique.library.data.dto.request.BookRequestDto;
import com.paulohenrique.library.data.dto.response.GeneralResponseDto;
import com.paulohenrique.library.data.entity.Book;
import com.paulohenrique.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Listar todos os livros", description = "Retorna uma lista paginada de todos os livros cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<Book>> findAllBooks(@PageableDefault(size = 10, sort = "title") Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @Operation(summary = "Buscar um livro pelo ID", description = "Retorna os detalhes de um livro específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", content = @Content)
    })
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> listBook(@PathVariable Long bookId) {
        return bookService.findById(bookId);
    }

    @Operation(summary = "Cadastrar um novo livro | \uD83D\uDD10 Requer ADMIN", description = "Adiciona um novo livro ao sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    @Transactional
    public ResponseEntity<Book> save(@RequestBody @Validated BookRequestDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }

    @Operation(summary = "Editar um livro existente | \uD83D\uDD10 Requer ADMIN", description = "Atualiza os dados de um livro já cadastrado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", content = @Content)
    })
    @PatchMapping("/{bookId}")
    @Transactional
    public ResponseEntity<Book> editBook(@PathVariable Long bookId, @RequestBody @Validated BookEditRequestDto dto) {
        return bookService.editBook(bookId, dto);
    }

    @Operation(summary = "Remover um livro | \uD83D\uDD10 Requer ADMIN", description = "Exclui um livro do sistema pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", content = @Content)
    })
    @DeleteMapping("/{bookId}")
    @Transactional
    public ResponseEntity<GeneralResponseDto> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok(new GeneralResponseDto(true, "Book deleted"));
    }
}