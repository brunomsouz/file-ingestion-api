package io.souz.fileingestionapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_order")
@Table(name = "user_order", indexes = {@Index(name = "idx_order_date", columnList = "date")})
public class Order {

    @Id
    private Long id;

    @Column
    private LocalDate date;

    @Column
    private BigDecimal total;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderProduct> products = new HashSet<>();

    public Order(Long id, LocalDate date, BigDecimal total) {
        this.id = id;
        this.date = date;
        this.total = total;
    }

    public void addProduct(OrderProduct product) {
        this.products.add(product);
    }
}


