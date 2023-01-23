package com.bankconnect.controllers;

import com.bankconnect.entities.Account;
import com.bankconnect.entities.Customer;
import com.bankconnect.services.AccountService;
import com.bankconnect.services.CustomerService;
import com.bankconnect.services.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("agent")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accService;
    private final CustomerService cstService;
    private final RequestService reqService;

    @GetMapping("accounts")
    public ResponseEntity<List> getAll(){
        return ResponseEntity.ok(accService.getAllAccounts());
    }

    @GetMapping("activate/{accountId}")
    public ResponseEntity<String> activateAccount(@PathVariable String accountId){
        Customer customer = cstService.getCustomerById(Long.valueOf(accountId));
        if(customer != null){
            if(!customer.getStatus()){
                cstService.activateAccount(Long.valueOf(accountId));
                createAccount(customer);
                return ResponseEntity.ok("Activation an account has been successfully.");
            }
        }
        return ResponseEntity.status(400).body("Failed to activate this account, try again!");
    }

    @GetMapping("requests")
    public ResponseEntity<List> getAllPendingAccount(){
        return ResponseEntity.ok(reqService.listAll()
                .stream()
                .filter(request -> (!request.getCustomer().getStatus()))
                .collect(Collectors.toList()));
    }


    public void createAccount(Customer customer){
        Account account = new Account();
        account.setBalance(0.0);
        account.setCustomerId(customer.getId());
        account.setNumber(Long.parseLong(generateRIB()));
        String accType = reqService.getRequestByCustomerId(customer.getId()).getTypeAccount();
        account.setType(accType);

        accService.save(account);
    }

    public String generateRIB(){
        Random random = new Random();
        Long number = Math.abs(random.nextLong());
        List<Account> lst;
        String ribNumber;
        System.out.println(number);
        do {
            ribNumber = String.format("%024d", number);
            String finalRibNumber = ribNumber;
            lst = accService.getAllAccounts().stream()
                    .filter(a -> a.getNumber() == Long.parseLong(finalRibNumber))
                    .collect(Collectors.toList());
        }while (lst.size() > 0);
        System.out.println(ribNumber);
        return ribNumber;
    }
}
