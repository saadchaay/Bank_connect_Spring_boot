package com.bankconnect.controllers;

import com.bankconnect.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("agent")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accService;

    @GetMapping("accounts")
    public ResponseEntity<List> getAll(){
        return ResponseEntity.ok(accService.getAllAccounts());
    }
}
