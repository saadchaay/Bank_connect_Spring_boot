package com.bankconnect.controllers;

import com.bankconnect.dto.TransactionRequest;
import com.bankconnect.entities.Transaction;
import com.bankconnect.services.AgentService;
import com.bankconnect.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customer")
public class TransactionController {

    private final AgentService agentService;
    private final TransactionService transactionService;


    public TransactionController(AgentService agentService, TransactionService transactionService) {
        this.agentService = agentService;
        this.transactionService = transactionService;
    }

    @GetMapping("all")
    public ResponseEntity<List> transactions(){
        return ResponseEntity.ok(transactionService.getTransactionsByAccountId(1L));
    }

    @PostMapping("online-payment")
    public ResponseEntity<List> createTransaction(
            @RequestBody TransactionRequest request
            ){
        Transaction transaction = new Transaction();
        transaction.setAccountId(Long.valueOf(request.getAccountId()));
        transaction.setAmount(Double.valueOf(request.getAmount()));
        transaction.setType(request.getTransactionType());
        if(transactionService.save(transaction) != null){
            return ResponseEntity.ok(transactionService.getTransactionsPerDay(Long.valueOf(request.getAccountId())));
        }
        return ResponseEntity.status(400).build();
    }



//    public boolean isValidWithdrawal
}
