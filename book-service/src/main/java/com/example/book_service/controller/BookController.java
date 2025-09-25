package com.example.book_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.book_service.dto.BookResponseDto;
import com.example.book_service.model.Book;
import com.example.book_service.service.BookService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable long id) {
        BookResponseDto responseDto = bookService.getBookById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping()
    public ResponseEntity<List<BookResponseDto>> getAll() {
        List<BookResponseDto> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @PostMapping()
    public ResponseEntity<BookResponseDto> addBook(@RequestBody Book book) {
        BookResponseDto savedBook = bookService.addNewBook(book);
        return ResponseEntity.ok(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable long id, @RequestBody Book book) {
        BookResponseDto updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }

}
