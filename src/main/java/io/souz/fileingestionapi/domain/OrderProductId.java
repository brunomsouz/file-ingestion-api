package io.souz.fileingestionapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Setter
@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductId {

    @Column(name = "user_order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

}
