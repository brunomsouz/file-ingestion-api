package io.souz.fileingestionapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private Long id;

    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Set<Order> orders = new HashSet<>();

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

}
