package com.example.book_service.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.book_service.dto.BookResponseDto;
import com.example.book_service.model.Book;
import com.example.book_service.repository.BookRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookService {

    private BookRepository bookRepository;

    private ModelMapper modelMapper;

    public BookResponseDto getBookById(long id) {

        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        return modelMapper.map(book, BookResponseDto.class);

    }

    public List<BookResponseDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(book -> modelMapper.map(book, BookResponseDto.class)).toList();
    }

    public BookResponseDto addNewBook(Book book) {
        Book savedBook = bookRepository.save(book);
        return modelMapper.map(savedBook, BookResponseDto.class);
    }

    public BookResponseDto updateBook(long id, Book book) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setId(existingBook.getId());
        // existingBook.setTitle(book.getTitle());
        // existingBook.setAuthor(book.getAuthor());
        // existingBook.setPrice(book.getPrice());
        // existingBook.setGenre(book.getGenre());
        // existingBook.setStock(book.getStock());
        Book updatedBook = bookRepository.save(book);
        return modelMapper.map(updatedBook, BookResponseDto.class);
    }

    public void deleteBook(long id) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepository.delete(existingBook);
    }

}
