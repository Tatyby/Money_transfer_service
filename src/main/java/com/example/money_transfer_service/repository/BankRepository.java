package com.example.money_transfer_service.repository;

import com.example.money_transfer_service.model.Card;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@AllArgsConstructor
public class BankRepository {
    private final Map<String, Card> cards = new HashMap<>();
    private final AtomicInteger idOperation = new AtomicInteger();

    public void save(String id, Card card) {
        cards.put(id, card);
    }

    public int getId() {
        return idOperation.incrementAndGet();
    }

}
