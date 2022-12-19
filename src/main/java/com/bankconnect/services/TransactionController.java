package com.bankconnect.services;

import com.bankconnect.entities.Transaction;
import com.bankconnect.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TransactionController {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public Transaction save(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long id){
        Optional transaction = transactionRepository.findById(id);
        return transaction.isPresent()? (Transaction) transaction.get() : null;
    }
}