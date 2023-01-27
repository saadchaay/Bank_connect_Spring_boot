package com.bankconnect.controllers;

import com.bankconnect.dto.*;
import com.bankconnect.entities.Account;
import com.bankconnect.entities.Virement;
import com.bankconnect.helpers.*;
import com.bankconnect.entities.Transaction;
import com.bankconnect.helpers.Enum;
import com.bankconnect.services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("customer")
public class TransactionController {

    private final CustomerService cstService;
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final VirementService virementService;
    private final AuthenticatedUserInfo authUserInfo;
    private final InvoiceGenerate invoiceGenerate;

    public TransactionController(CustomerService cstService, TransactionService transactionService, AccountService accountService, VirementService virementService, AuthenticatedUserInfo authUserInfo, InvoiceGenerate invoiceGenerate) {
        this.cstService = cstService;
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.virementService = virementService;
        this.authUserInfo = authUserInfo;
        this.invoiceGenerate = invoiceGenerate;
    }

    @GetMapping("all")
    public ResponseEntity<List<Transaction>> transactions(){
        return ResponseEntity.ok(transactionService.getTransactionsByAccountId(1L));
    }

    @PostMapping("deposit")
    public ResponseEntity<String> deposit(
            @RequestBody DepotWithdrawRequest req){
        Account account = accountService.getAccountByNumber(Long.valueOf(req.getAccountNumber()));
        Double amount = req.getAmount();
        if(account != null) {
            return deposit(account, amount, String.valueOf(Enum.transactionType.Deposit)) ?
                    ResponseEntity.ok("Transaction done successfully"):ResponseEntity.status(400).body("Transaction failed");
        }else{
            return ResponseEntity.status(400).body("Account not found");
        }
    }

    @PostMapping("withdraw")
    public ResponseEntity<String> withdraw(
            @RequestBody DepotWithdrawRequest req,
            HttpServletRequest httpSerReq){
        Account account = accountService.getAccByCustomer(cstService.getCustomerByEmail(authUserInfo.getEmail(httpSerReq)));
        Double amount = req.getAmount();
        if(account != null && amount <= account.getBalance()) {
            return withdraw(account, amount, String.valueOf(Enum.transactionType.Withdrawal)) ?
                    ResponseEntity.ok("Transaction done successfully"):ResponseEntity.status(400).body("Transaction failed");
        }else{
            return ResponseEntity.status(400).body("Failed Transaction");
        }
    }

    @PostMapping("transfer")
    public ResponseEntity<String> transfer(
            @RequestBody DepotWithdrawRequest req,
            HttpServletRequest httpSerReq) {

        Account account = accountService.getAccByCustomer(cstService.getCustomerByEmail(authUserInfo.getEmail(httpSerReq)));
        Account recipientAccount = accountService.getAccountByNumber(Long.valueOf(req.getAccountNumber()));
        double amount = req.getAmount();

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
                            ResponseEntity.status(400).body("Transfer failed1");
            } else
                return ResponseEntity.status(400).body("Transfer failed2");
        }else{
            return ResponseEntity.status(400).body("Transfer failed3");
        }
    }

    @PostMapping("online-payment")
    public ResponseEntity<String> createTransaction(
            @RequestBody OnlinePaymentRequest request,
            HttpServletRequest httpSerReq){
        Account account = accountService.getAccByCustomer(cstService.getCustomerByEmail(authUserInfo.getEmail(httpSerReq)));
        Transaction transaction = new Transaction(
                account.getId(),
                Double.valueOf(request.getAmount()),
                Enum.transactionType.OnlinePayment.toString()
        );
        if(Objects.equals(request.getDotationType()
                , String.valueOf(Enum.dotationType.National)))
            return onlineBillsPayment(account, transaction, 15000.0) ?
                    ResponseEntity.ok("Online payment done successfully.") :
                    ResponseEntity.status(400).body("Online payment Failed, Try again.") ;
        else return onlineBillsPayment(account, transaction, 100000.0) ?
                ResponseEntity.ok("Online payment done successfully.") :
                ResponseEntity.status(400).body("Online payment Failed, Try again.") ;
    }

    @PostMapping("bill-payment")
    public ResponseEntity<String> billsPayment(
            @RequestBody OnlinePaymentRequest req,
            HttpServletRequest httpSerReq){
        Account account = accountService.getAccByCustomer(cstService.getCustomerByEmail(authUserInfo.getEmail(httpSerReq)));
        System.out.println("customer name: "+account.getCustomer().getName());
        Transaction transaction = new Transaction(
                account.getId(),
                Double.valueOf(req.getAmount()),
                Enum.transactionType.BillPayment.toString()
        );

        String billType = req.getBill();
        String refBill = "";
        switch (billType){
            case "Water" -> refBill = "Wtr-"+RandomCode.generate();
            case "Electricity" -> refBill = "Elect-"+RandomCode.generate();
            case "Phone" -> refBill = "tel-"+RandomCode.generate();
        }
        Facture facture = new Facture(account, RandomCode.generate(), refBill, billType, Double.valueOf(req.getAmount()));
        System.out.println("facture: "+ facture);
        try {
            invoiceGenerate.create(facture);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return onlineBillsPayment(account, transaction, 15000.0) ?
                ResponseEntity.ok("Your bill has payed Successfully.") :
                ResponseEntity.status(400).body("Payment failed, try again.");
    }

    public boolean deposit(Account acc, Double amount, String transactionType){
            Long accountId = acc.getId();
            Transaction transaction = new Transaction(accountId, amount, transactionType);
            accountService.addToAccountById(amount, accountId);
            return transactionService.save(transaction) != null;
    }

    public boolean withdraw(Account acc, Double amount, String transactionType) {
        Transaction transaction = new Transaction(acc.getId(), amount, transactionType);
        double totalPerDay = getTotalPerDay(acc, amount, Enum.transactionType.Withdrawal.toString());
        double totalPerYear = getTotalPerYear(acc, amount, Enum.transactionType.Withdrawal.toString());
        if(Objects.equals(acc.getType(), Enum.accType.Standard.toString())
                && (totalPerDay > 5000 || totalPerYear > 100000)){
            return false;
        }
        if(Objects.equals(acc.getType(), Enum.accType.Professional.toString())
                && (totalPerDay > 100000 || totalPerYear > 200000)){
            return false;
        }
        accountService.subtractFromAccountById(amount, acc);
        return transactionService.save(transaction) != null;
    }

    public boolean onlineBillsPayment(Account acc, Transaction trs, Double plf){
        double totalPerDay = getTotalPerDay(acc, trs.getAmount(), Enum.transactionType.OnlinePayment.toString())
                + getTotalPerDay(acc, trs.getAmount(), Enum.transactionType.BillPayment.toString());
        double totalPerYear = getTotalPerYear(acc, trs.getAmount(), Enum.transactionType.OnlinePayment.toString())
                + getTotalPerYear(acc, trs.getAmount(), Enum.transactionType.BillPayment.toString());
        if(Objects.equals(acc.getType(), String.valueOf(Enum.accType.Standard))
                && (totalPerDay > 5000 || totalPerYear > 100000))
            return false;
        if(Objects.equals(acc.getType(), String.valueOf(Enum.accType.Professional))
                && (totalPerDay > plf || totalPerYear > plf))
            return false;
        accountService.subtractFromAccountById(trs.getAmount(), acc);
        return transactionService.save(trs) != null;
    }

    public Double getTotalPerDay(Account acc, Double amount, String trsType){
        return transactionService.getTransactionsPerDay(acc.getId())
                .stream().filter(trs -> Objects.equals(trs.getType(), trsType))
                .mapToDouble(Transaction::getAmount).sum() + amount;
    }

    public Double getTotalPerYear(Account acc, Double amount, String trsType){
        return transactionService.getTransactionsPerYear(acc.getId())
                .stream().filter(trs -> Objects.equals(trs.getType(), trsType))
                .mapToDouble(Transaction::getAmount).sum() + amount;
    }

}
