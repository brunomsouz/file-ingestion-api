package io.souz.fileingestionapi.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductTest {

    @Test
    void createProductWithValidId() {
        Product product = new Product(1L);

        assertEquals(1L, product.getId());
        assertTrue(product.getOrders().isEmpty());
    }

}