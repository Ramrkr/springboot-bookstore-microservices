package com.example.order_service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.order_service.dto.BookResponseDto;

@FeignClient(name = "BOOK-SERVICE")
public interface ApiClient {

    @GetMapping("/api/books/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable long id);

}
