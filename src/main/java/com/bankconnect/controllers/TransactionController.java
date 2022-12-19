package com.bankconnect.controllers;

import com.bankconnect.services.AgentService;
import com.bankconnect.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("transactions")
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
}
