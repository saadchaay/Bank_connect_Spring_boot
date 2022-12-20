package com.bankconnect.controllers;

import com.bankconnect.dto.TransferRequest;
import com.bankconnect.dto.WithrawRequest;
import com.bankconnect.entities.Account;
import com.bankconnect.entities.Virement;
import com.bankconnect.helpers.Enum;
import com.bankconnect.dto.DepositRequest;
import com.bankconnect.entities.Transaction;
import com.bankconnect.repositories.AccountRepository;
import com.bankconnect.services.AccountService;
import com.bankconnect.dto.TransactionRequest;
import com.bankconnect.services.AgentService;
import com.bankconnect.services.TransactionService;
import com.bankconnect.services.VirementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customer")
public class TransactionController {

    private final AgentService agentService;
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final VirementService virementService;

    public TransactionController(AgentService agentService, TransactionService transactionService, AccountRepository accountRepository, AccountService accountService, VirementService virementService) {
        this.agentService = agentService;
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.virementService = virementService;
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

    @PostMapping("withraw")
    public ResponseEntity<String> withraw(@RequestBody WithrawRequest req){
        Account account = accountService.getAccountByNumber(req.getAccountNumber());
        if(account != null) {
            Long accountId = account.getId();
            double amount = req.getAmount();
            if(amount <= account.getBalance()) {
                Transaction transaction = new Transaction(accountId, amount, String.valueOf(Enum.transactionType.Withdrawal));
                accountService.substractFromAccountById(amount, accountId);
                if (transactionService.save(transaction) != null) {
                    return ResponseEntity.ok("Transaction done successfully");
                } else
                    return ResponseEntity.status(400).body("Transaction failed");
            }else
                return ResponseEntity.status(400).body("Insufficient balance");
        }else{
            return ResponseEntity.status(400).body("Account not found");
        }
    }

    @PostMapping("transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest req){
        Account account = accountService.getAccountById(req.getAccountId());
        Account recipientAccount = accountService.getAccountByNumber(req.getRecipientAccountNumber());

        if (account != null && recipientAccount != null) {
            Long accountId = account.getId();
            Long recipientAccountId = recipientAccount.getId();
            double amount = req.getAmount();
            if(amount <= account.getBalance()) {
                Transaction transaction = new Transaction(accountId, amount, String.valueOf(Enum.transactionType.Transfer));
                accountService.substractFromAccountById(amount, accountId);
                accountService.addToAccountById(amount, recipientAccountId);
                if (transactionService.save(transaction) != null) {
                    Virement virement = new Virement(recipientAccountId, false, transaction.getId());
                    if (virementService.save(virement) != null){
                        return ResponseEntity.ok("Transfer done successfully");
                    } else
                        return ResponseEntity.status(400).body("Transfer failed");
                } else
                    return ResponseEntity.status(400).body("Transfer failed");
            }else{
                return ResponseEntity.status(400).body("Insufficient balance");
            }
        }else{
            return ResponseEntity.status(400).body("Account not found");
        }
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
