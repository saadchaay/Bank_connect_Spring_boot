package com.bankconnect.services;


import com.bankconnect.entities.Account;
import com.bankconnect.entities.Customer;
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

    public Account getAccountByNumber(Long accNmb){
        return accountRepository.getAccountByNumber(accNmb);
    }

    public void deleteAccountById(Long id) {
        if (getAccountById(id) != null) {
            accountRepository.deleteById(id);
        }
    }

    public void addToAccountById(double amount, Long id){
        double newBalance = getAccountById(id).getBalance() + amount;
        accountRepository.UpdateAccountBalance(newBalance,id);
    }

    public void subtractFromAccountById(double amount, Account acc){
        double newBalance = getAccountById(acc.getId()).getBalance() - amount;
        accountRepository.UpdateAccountBalance(newBalance, acc.getId());
    }

    public Account getAccByCustomer(Customer customer){
        return accountRepository.getAccountByCustomer(customer);
    }

}
