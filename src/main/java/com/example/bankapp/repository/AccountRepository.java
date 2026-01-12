package com.example.bankapp.repository;

import com.example.bankapp.model.BankAccount;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AccountRepository {

    private final Map<Long, BankAccount> accounts = new HashMap<>();

    public BankAccount save(BankAccount account) {
        accounts.put(account.getId(), account);
        return account;
    }

    public BankAccount findById(Long id) {
        return accounts.get(id);
    }

    public Collection<BankAccount> findAll() {
        return accounts.values();
    }

    public boolean existsById(Long id) {
        return accounts.containsKey(id);
    }

    public BankAccount findByHolderName(String holderName) {
        return accounts.values()
                .stream()
                .filter(acc -> acc.getHolderName().equals(holderName))
                .findFirst()
                .orElse(null);
    }
}
