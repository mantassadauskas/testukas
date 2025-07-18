package org.example.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.entity.Trade;

@ApplicationScoped
public class TradeRepository implements PanacheRepository<Trade> {
}
