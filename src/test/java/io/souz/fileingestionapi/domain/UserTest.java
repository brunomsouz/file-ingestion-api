package io.souz.fileingestionapi.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    @Test
    void createUserWithValidData() {
        User user = new User(1L, "John Doe");

        assertEquals(1L, user.getId());
        assertEquals("John Doe", user.getName());
        assertTrue(user.getOrders().isEmpty());
    }

    @Test
    void addOrderToUser() {
        User user = new User(1L, "John Doe");
        Order order = new Order(1L, LocalDate.now(), BigDecimal.TEN);

        assertEquals(0, user.getOrders().size());
        user.addOrder(order);
        assertEquals(1, user.getOrders().size());
    }

}