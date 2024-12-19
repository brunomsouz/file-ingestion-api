package io.souz.fileingestionapi.dto;

import io.souz.fileingestionapi.domain.OrderProduct;
import io.souz.fileingestionapi.domain.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductDtoTest {

    @Test
    void parseFromProducts() {
        Product product = new Product(1L);
        OrderProduct orderProduct = new OrderProduct(null, product, BigDecimal.valueOf(10.00));
        Set<OrderProduct> orderProducts = Set.of(orderProduct);

        Set<ProductDto> productDtos = ProductDto.fromProducts(orderProducts);

        assertEquals(1, productDtos.size());
        ProductDto productDto = productDtos.iterator().next();
        assertEquals(1L, productDto.id());
        assertEquals(BigDecimal.valueOf(10.00), productDto.value());
    }

    @Test
    void parseFromEmptySet() {
        Set<OrderProduct> orderProducts = new HashSet<>();

        Set<ProductDto> productDtos = ProductDto.fromProducts(orderProducts);

        assertEquals(0, productDtos.size());
    }

}