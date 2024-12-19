package io.souz.fileingestionapi.controller;

import io.souz.fileingestionapi.dto.OrderDto;
import io.souz.fileingestionapi.exception.BadRequestException;
import io.souz.fileingestionapi.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    private OrderController orderController;

    @BeforeEach
    void setUp() {
        this.orderController = new OrderController(this.orderService);
    }

    @Test
    void findOrderByIdWithValidId() {
        Long orderId = 1L;
        OrderDto order = new OrderDto(orderId, "100.0", LocalDate.now(), new HashSet<>());
        when(this.orderService.findOrderById(orderId)).thenReturn(order);

        ResponseEntity<OrderDto> response = this.orderController.findOrderById(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void findOrdersWithValidDates() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        Set<OrderDto> orders = new HashSet<>();
        when(this.orderService.findOrders(startDate, endDate)).thenReturn(orders);

        ResponseEntity<Set<OrderDto>> response = this.orderController.findOrders(startDate, endDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void findOrdersWithInvalidDateRange() {
        LocalDate startDate = LocalDate.of(2023, 12, 31);
        LocalDate endDate = LocalDate.of(2023, 1, 1);

        Exception exception = assertThrows(BadRequestException.class, () -> {
            this.orderController.findOrders(startDate, endDate);
        });

        assertEquals("Date range is invalid.", exception.getMessage());
    }

    @Test
    void findOrdersWithStartDate() {
        LocalDate startDate = LocalDate.now();
        Set<OrderDto> orders = new HashSet<>();
        when(this.orderService.findOrders(startDate, null)).thenReturn(orders);

        ResponseEntity<Set<OrderDto>> response = this.orderController.findOrders(startDate, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void findOrdersWithEndDate() {
        LocalDate endDate = LocalDate.now();
        Set<OrderDto> orders = new HashSet<>();
        when(this.orderService.findOrders(null, endDate)).thenReturn(orders);

        ResponseEntity<Set<OrderDto>> response = this.orderController.findOrders(null, endDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

}