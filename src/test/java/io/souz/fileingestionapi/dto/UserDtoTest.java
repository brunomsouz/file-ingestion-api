package io.souz.fileingestionapi.dto;

import io.souz.fileingestionapi.domain.Order;
import io.souz.fileingestionapi.domain.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDtoTest {

    @Test
    void fromUser() {
        Order order = new Order(1L, LocalDate.now(), BigDecimal.TEN, new HashSet<>());
        Set<Order> orders = Set.of(order);
        User user = new User(1L, "John Doe", orders);

        UserDto userDto = UserDto.fromUser(user);

        assertEquals(1L, userDto.id());
        assertEquals("John Doe", userDto.name());
        assertEquals(1, userDto.orders().size());
    }

    @Test
    void fromUserWithNoOrders() {
        User user = new User(1L, "John Doe", new HashSet<>());

        UserDto userDto = UserDto.fromUser(user);

        assertEquals(1L, userDto.id());
        assertEquals("John Doe", userDto.name());
        assertEquals(0, userDto.orders().size());
    }

}