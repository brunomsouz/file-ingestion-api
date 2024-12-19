package io.souz.fileingestionapi.service;

import io.souz.fileingestionapi.domain.Order;
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

    public Order findOrderById(Long id) {
        return this.orderRepository.getOrderById(id)
                .orElseThrow(() -> new NotFoundException("order.not.found"));
    }

    public Set<Order> findOrders(LocalDate startDate, LocalDate endDate) {
        return this.orderRepository.findOrdersByDateRange(startDate, endDate);
    }

}
