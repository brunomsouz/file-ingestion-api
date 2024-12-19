package io.souz.fileingestionapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.souz.fileingestionapi.domain.OrderProduct;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public record ProductDto(
        @JsonProperty("product_id") Long id,
        BigDecimal value
) {

    public static Set<ProductDto> mapFromProducts(Set<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(op -> new ProductDto(
                        op.getProduct().getId(),
                        op.getValue()
                ))
                .collect(Collectors.toSet());
    }

}
