package io.souz.fileingestionapi.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderTest {

    @Test
    void createOrder() {
        Order order = new Order(1L, LocalDate.now(), BigDecimal.TEN);

        assertEquals(1L, order.getId());
        assertEquals(LocalDate.now(), order.getDate());
        assertEquals(BigDecimal.TEN, order.getTotal());
        assertTrue(order.getProducts().isEmpty());
    }

    @Test
    void addProductIncreasesProductSetSize() {
        Order order = new Order(1L, LocalDate.now(), BigDecimal.TEN);
        Product product = new Product(1L);
        OrderProduct orderProduct = new OrderProduct(null, product, BigDecimal.valueOf(10.00));

        assertEquals(0, order.getProducts().size());
        order.addProduct(orderProduct);
        assertEquals(1, order.getProducts().size());
    }

}