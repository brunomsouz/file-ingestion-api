package io.souz.fileingestionapi.service;

import io.souz.fileingestionapi.domain.Order;
import io.souz.fileingestionapi.dto.OrderDto;
import io.souz.fileingestionapi.exception.NotFoundException;
import io.souz.fileingestionapi.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        this.orderService = new OrderService(this.orderRepository);
    }

    @Test
    void findOrderByIdWithValidId() {
        Long orderId = 1L;
        Order order = new Order(1L, LocalDate.now(), BigDecimal.ZERO, new HashSet<>());
        when(this.orderRepository.getOrderById(orderId)).thenReturn(Optional.of(order));

        OrderDto result = this.orderService.findOrderById(orderId);

        assertNotNull(result);
    }

    @Test
    void findOrderByIdWithNonExistentId() {
        Long orderId = 999L;
        when(this.orderRepository.getOrderById(orderId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            this.orderService.findOrderById(orderId);
        });

        assertEquals("Order not found for given id.", exception.getMessage());
    }

    @Test
    void findOrdersWithValidDates() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        Set<Order> orders = new HashSet<>();
        when(this.orderRepository.findOrdersByDateRange(startDate, endDate)).thenReturn(orders);

        Set<OrderDto> result = this.orderService.findOrders(startDate, endDate);

        assertNotNull(result);
    }

}