package org.example.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import org.example.enums.OrderType;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "orders")
public class Order extends PanacheEntity {
    public String item;
    public int quantity;
    public int price;
    public OrderType type;
    public LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    public Trade transaction;

    public boolean isFulfilled() {
        return this.transaction != null;
    }
}
