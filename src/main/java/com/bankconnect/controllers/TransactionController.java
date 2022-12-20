package com.bankconnect.controllers;

import com.bankconnect.dto.TransferRequest;
import com.bankconnect.dto.WithrawRequest;
import com.bankconnect.entities.Account;
import com.bankconnect.entities.Virement;
import com.bankconnect.helpers.AuthenticatedUserInfo;
import com.bankconnect.helpers.Enum;
import com.bankconnect.dto.DepositRequest;
import com.bankconnect.entities.Transaction;
import com.bankconnect.repositories.AccountRepository;
import com.bankconnect.services.*;
import com.bankconnect.dto.TransactionRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class TransactionController {

    private final AgentService agentService;
    private final CustomerService cstService;
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final VirementService virementService;
    private final AuthenticatedUserInfo authUserInfo;

    public TransactionController(AgentService agentService, CustomerService cstService, TransactionService transactionService, AccountRepository accountRepository, AccountService accountService, VirementService virementService, AuthenticatedUserInfo authUserInfo) {
        this.agentService = agentService;
        this.cstService = cstService;
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.virementService = virementService;
        this.authUserInfo = authUserInfo;
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

    @PostMapping("withdraw")
    public ResponseEntity<String> withdraw(@RequestBody WithrawRequest req){
        Account account = accountService.getAccountByNumber(req.getAccountNumber());
        if(account != null) {
            Long accountId = account.getId();
            double amount = req.getAmount();
            if(amount <= account.getBalance()) {
                Transaction transaction = new Transaction(accountId, amount, String.valueOf(Enum.transactionType.Withdrawal));
                accountService.subtractFromAccountById(amount, accountId);
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
    public ResponseEntity<String> transfer(@RequestBody TransferRequest req, HttpServletRequest httpServletRequest){
//        Customer customer = ;
        Account account = accountService.getAccByCustomer(cstService.getCustomerByEmail(authUserInfo.getEmail(httpServletRequest)));
        Account recipientAccount = accountService.getAccountByNumber(req.getRecipientAccountNumber());
        System.out.println(account.getCustomer().getEmail());
        if (account != null && recipientAccount != null) {
            Long accountId = account.getId();
            Long recipientAccountId = recipientAccount.getId();
            double amount = req.getAmount();
            if(amount <= account.getBalance()) {
                Transaction transaction = new Transaction(accountId, amount, String.valueOf(Enum.transactionType.Transfer));
                accountService.subtractFromAccountById(amount, accountId);
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
