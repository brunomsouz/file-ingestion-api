package io.souz.fileingestionapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.souz.fileingestionapi.domain.User;

import java.util.Set;

public record UserDto(
        @JsonProperty("user_id") Long id,
        String name,
        Set<OrderDto> orders
) {

    public static UserDto fromUser(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                OrderDto.fromOrders(user.getOrders()));
    }

}
