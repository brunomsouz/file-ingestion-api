package io.souz.fileingestionapi.service;

import io.souz.fileingestionapi.domain.Order;
import io.souz.fileingestionapi.dto.OrderDto;
import io.souz.fileingestionapi.exception.NotFoundException;
import io.souz.fileingestionapi.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDto findOrderById(Long id) {
        Order order = this.orderRepository.getOrderById(id)
                .orElseThrow(() -> new NotFoundException("order.not.found"));

        return OrderDto.fromOrder(order);
    }

    public Set<OrderDto> findOrders(LocalDate startDate, LocalDate endDate) {
        Set<Order> orders = this.orderRepository.findOrdersByDateRange(startDate, endDate);

        return OrderDto.fromOrders(orders);
    }

}
