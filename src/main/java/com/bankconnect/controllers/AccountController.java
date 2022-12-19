package com.bankconnect.controllers;

import com.bankconnect.entities.Customer;
import com.bankconnect.services.AccountService;
import com.bankconnect.services.CustomerService;
import com.bankconnect.services.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping("activate/{accountId}")
    public ResponseEntity<String> activateAccount(@PathVariable String accountId){
        Customer customer = cstService.getCustomerById(Long.valueOf(accountId));
        if(customer != null){
            if(!customer.getStatus()){
                cstService.activateAccount(Long.valueOf(accountId));
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
}
