package com.bankconnect.services;

import com.bankconnect.entities.Transaction;
import com.bankconnect.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private Calendar calendar = new GregorianCalendar();
    private Calendar calendarCompare = new GregorianCalendar();

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
        calendarCompare.setTime(new Date());
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

    public List<Transaction> getTransactionsByAccountId(Long accountId){
        return getAllTransactions()
                .stream()
                .filter(trn -> Objects.equals(trn.getAccountId(), accountId))
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransactionsPerYear(Long accountId){
        return transactionRepository.getTransactionsPerYear(accountId, calendarCompare.get(Calendar.YEAR));
    }

    public List<Transaction> getTransactionsPerDay(Long accountId){
        System.out.println("Day: " + calendarCompare.get(Calendar.DAY_OF_MONTH));
        return transactionRepository.getTransactionsPerDay(accountId, calendarCompare.get(Calendar.DAY_OF_MONTH));
    }

}