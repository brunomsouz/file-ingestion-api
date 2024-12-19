package io.souz.fileingestionapi.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderProductTest {

    @Test
    void createOrderProductWithValidData() {
        Order order = new Order(1L, LocalDate.now(), BigDecimal.TEN);
        Product product = new Product(1L);
        OrderProduct orderProduct = new OrderProduct(order, product, BigDecimal.valueOf(10.00));

        assertEquals(order, orderProduct.getOrder());
        assertEquals(product, orderProduct.getProduct());
        assertEquals(BigDecimal.valueOf(10.00), orderProduct.getValue());
    }

}