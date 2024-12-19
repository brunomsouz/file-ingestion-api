package io.souz.fileingestionapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.souz.fileingestionapi.domain.Order;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public record OrderDto(
        @JsonProperty("order_id") Long id,
        String total,
        LocalDate date,
        Set<ProductDto> products) {

    public static Set<OrderDto> mapFromOrders(Set<Order> orders) {
        return orders.stream()
                .map(OrderDto::mapFromOrder)
                .collect(Collectors.toSet());
    }

    public static OrderDto mapFromOrder(Order order) {
        return new OrderDto(
                order.getId(),
                order.getTotal().toString(),
                order.getDate(),
                ProductDto.mapFromProducts(order.getProducts())
        );
    }

}
