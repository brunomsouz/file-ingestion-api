package io.souz.fileingestionapi.dto;

import io.souz.fileingestionapi.domain.Order;
import io.souz.fileingestionapi.domain.OrderProduct;
import io.souz.fileingestionapi.domain.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderDtoTest {

    @Test
    void parseFromSingleOrder() {
        Product product = new Product(1L);
        Order order = new Order(1L, LocalDate.now(), BigDecimal.TEN);
        OrderProduct orderProduct = new OrderProduct(null, product, BigDecimal.valueOf(10.00));
        order.addProduct(orderProduct);

        OrderDto orderDto = OrderDto.fromOrder(order);
        assertEquals(1L, orderDto.id());
        assertEquals(LocalDate.now(), orderDto.date());
        assertEquals(BigDecimal.TEN.toString(), orderDto.total());
        assertEquals(1, orderDto.products().size());
    }

    @Test
    void parseFromOrders() {
        Product product = new Product(1L);
        Order order = new Order(1L, LocalDate.now(), BigDecimal.TEN);
        OrderProduct orderProduct = new OrderProduct(null, product, BigDecimal.valueOf(10.00));
        order.addProduct(orderProduct);
        Set<Order> orders = Set.of(order);

        Set<OrderDto> orderDtos = OrderDto.fromOrders(orders);

        assertEquals(1, orderDtos.size());

        OrderDto orderDto = orderDtos.iterator().next();
        assertEquals(1L, orderDto.id());
        assertEquals(LocalDate.now(), orderDto.date());
        assertEquals(BigDecimal.TEN.toString(), orderDto.total());
        assertEquals(1, orderDto.products().size());
    }

    @Test
    void parseFromEmptySet() {
        Set<Order> orders = new HashSet<>();

        Set<OrderDto> orderDtos = OrderDto.fromOrders(orders);

        assertEquals(0, orderDtos.size());
    }

}