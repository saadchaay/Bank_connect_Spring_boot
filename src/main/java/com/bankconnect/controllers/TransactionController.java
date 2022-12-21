package com.bankconnect.controllers;

import com.bankconnect.dto.*;
import com.bankconnect.entities.Account;
import com.bankconnect.entities.Virement;
import com.bankconnect.helpers.AuthenticatedUserInfo;
import com.bankconnect.helpers.Enum;
import com.bankconnect.entities.Transaction;
import com.bankconnect.repositories.AccountRepository;
import com.bankconnect.services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
    public ResponseEntity<String> deposit(@RequestBody DepotWithdrawRequest req){
        Account account = accountService.getAccountByNumber(req.getAccountNumber());
        Double amount = req.getAmount();
        if(account != null) {
            return deposit(account, amount, String.valueOf(Enum.transactionType.Deposit)) ?
                    ResponseEntity.ok("Transaction done successfully"):ResponseEntity.status(400).body("Transaction failed");
        }else{
            return ResponseEntity.status(400).body("Account not found");
        }
    }

    @PostMapping("withdraw")
    public ResponseEntity<String> withdraw(@RequestBody DepotWithdrawRequest req, HttpServletRequest httpServletRequest){
        Account account = accountService.getAccByCustomer(cstService.getCustomerByEmail(authUserInfo.getEmail(httpServletRequest)));
        Double amount = req.getAmount();
        if(account != null && amount <= account.getBalance()) {
            return withdraw(account, amount, String.valueOf(Enum.transactionType.Withdrawal)) ?
                    ResponseEntity.ok("Transaction done successfully"):ResponseEntity.status(400).body("Transaction failed");
        }else{
            return ResponseEntity.status(400).body("Failed Transaction");
        }
    }

    @PostMapping("transfer")
    public ResponseEntity<String> transfer(@RequestBody DepotWithdrawRequest req, HttpServletRequest httpServletRequest) {
        Account account = accountService.getAccByCustomer(cstService.getCustomerByEmail(authUserInfo.getEmail(httpServletRequest)));
        Account recipientAccount = accountService.getAccountByNumber(req.getAccountNumber());
        Double amount = req.getAmount();

        if (recipientAccount != null && amount <= account.getBalance()) {
            Transaction transaction = new Transaction(
                    account.getId(), amount,
                    String.valueOf(Enum.transactionType.Transfer)
            );
            accountService.subtractFromAccountById(amount, account);
            accountService.addToAccountById(amount, recipientAccount.getId());

            if (transactionService.save(transaction) != null) {
                Virement virement = new Virement(
                        recipientAccount.getId(),
                        false,
                        transaction.getId());

                    return virementService.save(virement) != null ?
                            ResponseEntity.ok("Transfer done successfully"):
                            ResponseEntity.status(400).body("Transfer failed");
            } else
                return ResponseEntity.status(400).body("Transfer failed");
        }else{
            return ResponseEntity.status(400).body("Transfer failed");
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



    public boolean deposit(Account acc, Double amount, String transactionType){
            Long accountId = acc.getId();
            Transaction transaction = new Transaction(accountId, amount, transactionType);
            accountService.addToAccountById(amount, accountId);
            return transactionService.save(transaction) != null;
    }

    public boolean withdraw(Account acc, Double amount, String transactionType) {
        Transaction transaction = new Transaction(acc.getId(), amount, transactionType);
        Double totalPerDay = transactionService.getTransactionsPerDay(acc.getId())
                .stream().filter(trs -> Objects.equals(trs.getType(), Enum.transactionType.Withdrawal.toString()))
                .mapToDouble(Transaction::getAmount).sum() + amount;

        Double totalPerYear = transactionService.getTransactionsPerYear(acc.getId())
                .stream().filter(trs -> Objects.equals(trs.getType(), Enum.transactionType.Withdrawal.toString()))
                .mapToDouble(Transaction::getAmount).sum() + amount;

        if(Objects.equals(acc.getType(), Enum.accType.Standard.toString()) && (totalPerDay > 5000 || totalPerYear > 100000)){
            return false;
        }
        if(Objects.equals(acc.getType(), Enum.accType.Professional.toString()) && (totalPerDay > 10000 || totalPerYear > 200000)){
            return false;
        }
        accountService.subtractFromAccountById(amount, acc);
        return transactionService.save(transaction) != null;
    }

}
