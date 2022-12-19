package com.bankconnect.services;


import com.bankconnect.entities.Account;
import com.bankconnect.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account save(Account account){
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id){
        Optional account = accountRepository.findById(id);
        return account.isPresent() ? (Account) account.get() : null;
    }

    public void deleteAccountById(Long id) {
        if (getAccountById(id) != null) {
            accountRepository.deleteById(id);
        }
    }

    public void addTOAccountById(double amount, Long id){
        accountRepository.getById(id).setBalance(accountRepository.getById(id).getBalance() + amount);
    }


}
