package com.example.money_transfer_service.repository;

import com.example.money_transfer_service.model.Amount;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class BankRepository {
    private final Map<String, String> idAndCode = new HashMap<>();
    private final AtomicInteger idOperation = new AtomicInteger();
    private final Amount balanceFromCardExample = new Amount(1000, "RUR");
    private final Amount balanceToCardExample = new Amount(5000, "RUR");
    private final Map<String, Integer> balanceFromCards=new ConcurrentHashMap<>();
    private final Map<String, Integer> balanceToCards=new ConcurrentHashMap<>();
    private final Map<String, String> numberToCard=new ConcurrentHashMap<>();
    private final Map<String, String> numberFromCard=new ConcurrentHashMap<>();

    public int getId() {
        return idOperation.incrementAndGet();
    }

    public int getAmountFromValue() {
        return balanceFromCardExample.value();
    }

    public int getAmountToValue() {
        return balanceToCardExample.value();
    }

    public String getCode(String id) {
        return idAndCode.get(id);

    }
    public void saveBalanceFromCard(String id, int balanceFromCard){
        balanceFromCards.put(id,balanceFromCard);

    }
    public int getBalanceFromCard(String id){
       return balanceFromCards.get(id);
    }

    public void saveBalanceToCard(String id, int balanceToCard) {
        balanceToCards.put(id,balanceToCard);
    }
    public int getBalanceToCard(String id){
        return balanceToCards.get(id);
    }

    public void saveNumberToCard(String id, String numberCard) {
        numberToCard.put(id,numberCard);
    }

    public void saveNumberFromCard(String id, String numberCard) {
        numberFromCard.put(id,numberCard);
    }

    public String getNumberFromCard(String id){
        return numberFromCard.get(id);
    }
    public String getNumberToCard(String id){
        return numberToCard.get(id);
    }
}


