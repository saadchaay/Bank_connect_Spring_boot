package com.bankconnect.controllers;

import com.bankconnect.entities.Account;
import com.bankconnect.helpers.Enum;
import com.bankconnect.repositories.AccountRepository;
import com.bankconnect.services.AccountService;
import com.bankconnect.services.AgentService;
import com.bankconnect.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class TransactionController {

    private final AgentService agentService;
    private final TransactionService transactionService;
    private final AccountService accountService;

    public TransactionController(AgentService agentService, TransactionService transactionService, AccountRepository accountRepository, AccountService accountService, VirementService virementService) {
        this.agentService = agentService;
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping("all")
    public ResponseEntity<List> transactions(){
        return ResponseEntity.ok(transactionService.getTransactionsByAccountId(1L));
    }

    @PostMapping("deposit")
    public ResponseEntity<String> deposit(@RequestBody DepositRequest req){
        Account account = accountService.getAccountByNumber(req.getAccountNumber());
        if(account != null) {
            Long accountId = account.getId();
            double amount = req.getAmount();
            Transaction transaction = new Transaction(accountId, amount, String.valueOf(Enum.transactionType.Deposit));
            accountService.addToAccountById(amount, accountId);
            if(transactionService.save(transaction) != null){
                return ResponseEntity.ok("Transaction done successfully");
            }else
                return ResponseEntity.status(400).body("Transaction failed");

        }else{
            return ResponseEntity.status(400).body("Account not found");
        }
    }

}
