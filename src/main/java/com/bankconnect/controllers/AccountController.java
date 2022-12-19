package com.bankconnect.controllers;

import com.bankconnect.entities.Customer;
import com.bankconnect.helpers.Enum;
import com.bankconnect.helpers.SendSMS;
import com.bankconnect.services.AccountService;
import com.bankconnect.services.CustomerService;
import com.bankconnect.services.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("agent")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('AGENT')")
public class AccountController {

    private final AccountService accService;
    private final CustomerService cstService;
    private final RequestService reqService;

    @Value("${twilio.sID}")
    private String sID_Twilio;

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
        System.out.println(sID_Twilio);
        return ResponseEntity.ok(reqService.listAll()
                .stream()
                .filter(request -> (!request.getCustomer().getStatus()))
                .collect(Collectors.toList()));
    }
}
