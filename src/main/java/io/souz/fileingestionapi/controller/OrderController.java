package io.souz.fileingestionapi.controller;

import io.souz.fileingestionapi.dto.OrderDto;
import io.souz.fileingestionapi.exception.BadRequestException;
import io.souz.fileingestionapi.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Set;

@RestController
public class OrderController implements BaseController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderDto> findOrderById(@PathVariable Long id) {
        OrderDto order = this.orderService.findOrderById(id);

        return ResponseEntity.ok(order);
    }

    @GetMapping("/order")
    public ResponseEntity<Set<OrderDto>> findOrders(@RequestParam(required = false) LocalDate startDate,
                                                    @RequestParam(required = false) LocalDate endDate) {
        if ((startDate != null && endDate != null) && endDate.isBefore(startDate)) {
            throw new BadRequestException("invalid.date.range");
        }

        Set<OrderDto> orders = this.orderService.findOrders(startDate, endDate);

        return ResponseEntity.ok(orders);
    }

}
