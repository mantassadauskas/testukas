package org.example.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String item;
    public int quantity;
    public LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    public User buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    public User seller;

    @OneToOne
    @JoinColumn(name = "buy_order_id")
    public Order buyOrder;

    @OneToOne
    @JoinColumn(name = "sell_order_id")
    public Order sellOrder;
}
