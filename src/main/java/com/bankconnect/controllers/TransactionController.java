package com.bankconnect.controllers;

import com.bankconnect.services.AgentService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("customer")
public class TransactionController {

    private final AgentService agentService;

    public TransactionController(AgentService agentService) {
        this.agentService = agentService;
    }

    @GetMapping("all")
    public ResponseEntity<List> transactions(){
        return ResponseEntity.ok(agentService.listAll());
    }
}
