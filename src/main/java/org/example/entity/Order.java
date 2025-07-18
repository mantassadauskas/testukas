package org.example.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String item;

    public Integer quantity;

    public LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;
}
