package com.example.service;

// AccountService.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;

    public Account register(Account account) {
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        return accountRepository.save(account);
    }

    public Account login(Account account) {
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount != null && existingAccount.getPassword().equals(account.getPassword())) {
            return existingAccount;
        }
        throw new RuntimeException("Invalid credentials");
    }
}

