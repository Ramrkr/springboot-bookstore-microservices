package com.example.order_service.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.order_service.dto.BookResponseDto;
import com.example.order_service.dto.OrderRequestDto;
import com.example.order_service.dto.OrderResponseDto;
import com.example.order_service.feignClient.ApiClient;
import com.example.order_service.model.Order;
import com.example.order_service.repository.OrderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

    private RestTemplate restTemplate;
    private WebClient webClient;
    private ApiClient apiClient;

    private ModelMapper modelMapper;

    public OrderResponseDto placeOrder(OrderRequestDto orderRequest) {

        String bookServiceUrl = "http://localhost:8081/api/books/" + orderRequest.getBookId();

        // RestTemplate
        // BookResponseDto bookResponse = restTemplate.getForObject(bookServiceUrl,
        // BookResponseDto.class);

        // WebClient
        // BookResponseDto bookResponse = webClient.get()
        // .uri(bookServiceUrl)
        // .retrieve()
        // .bodyToMono(BookResponseDto.class)
        // .block();

        // OpenFeign client
        BookResponseDto bookResponse = apiClient.getBookById(orderRequest.getBookId()).getBody();

        Order order = new Order();
        order.setBookId(orderRequest.getBookId());
        order.setQuantity(orderRequest.getQuantity());
        order.setTotalPrice(bookResponse.getPrice() * orderRequest.getQuantity());
        Order savedOrder = orderRepository.save(order);

        return modelMapper.map(savedOrder, OrderResponseDto.class);
    }

    public OrderResponseDto getOrderById(Long id) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return modelMapper.map(existingOrder, OrderResponseDto.class);
    }

    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> modelMapper.map(order, OrderResponseDto.class)).toList();
    }

}
