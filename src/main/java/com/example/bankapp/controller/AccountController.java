package com.example.bankapp.controller;

import com.example.bankapp.model.BankAccount;
import com.example.bankapp.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    // Create account
    @PostMapping
    public ResponseEntity<BankAccount> create(@RequestBody BankAccount account) {
        BankAccount created = service.createAccount(account);
        return ResponseEntity.ok(created);
    }



    // Get single account
    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> get(@PathVariable Long id) {
        BankAccount acc = service.getAccount(id);
        if (acc == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(acc);
    }



    // Get all accounts
    @GetMapping
    public Collection<BankAccount> getAll() {
        return service.getAllAccounts();
    }
    @GetMapping("/by-name/{name}")
    public ResponseEntity<BankAccount> getByName(@PathVariable String name) {
        BankAccount acc = service.getAccountByName(name);
        if (acc == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(acc);
    }


    // Deposit
    @PostMapping("/{id}/deposit")
    public ResponseEntity<BankAccount> deposit(@PathVariable Long id,
                                               @RequestParam double amount) {
        try {
            BankAccount acc = service.deposit(id, amount);
            return ResponseEntity.ok(acc);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Withdraw
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<BankAccount> withdraw(@PathVariable Long id,
                                                touc@RequestParam double amount) {
        try {
            BankAccount acc = service.withdraw(id, amount);
            return ResponseEntity.ok(acc);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

    }
}
