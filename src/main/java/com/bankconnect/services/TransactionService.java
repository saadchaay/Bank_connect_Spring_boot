package com.bankconnect.services;

import com.bankconnect.entities.Transaction;
import com.bankconnect.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
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

    public List<Transaction> getTransactionsByAccountId(Long id){
        return transactionRepository.findByAccount_Id(id);
    }
}