package br.com.compassuol.sp.challenge.msorders.entity;


import br.com.compassuol.sp.challenge.msproducts.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ElementCollection
    @Column(name = "product_ids")
    private List<Long> productIds;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(nullable = false)
    private String status;

}



