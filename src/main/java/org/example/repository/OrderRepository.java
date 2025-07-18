package org.example.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.entity.Order;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {
}
