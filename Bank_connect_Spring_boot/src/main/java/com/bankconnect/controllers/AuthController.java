package com.bankconnect.controllers;

import com.bankconnect.entities.Agent;
import com.bankconnect.entities.Customer;
import com.bankconnect.helpers.AgentLoggedIn;
import com.bankconnect.helpers.JwtUtils;
import com.bankconnect.dto.AuthenticationRequest;
import com.bankconnect.helpers.LoggedIn;
import com.bankconnect.services.AgentService;
import com.bankconnect.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AgentService agentService;
    private final CustomerService customerService;
    private final JwtUtils jwtUtils;

    @PostMapping("agent")
    public ResponseEntity<Object> agentAuthentication(
            @RequestBody AuthenticationRequest request
    ){
        setAuthenticationManager(request.getEmail(), request.getPassword());
        final UserDetails user = agentService.findByEmail(request.getEmail());
        if(user != null){
            AgentLoggedIn loggedIn = new AgentLoggedIn();
            Agent agent = agentService.getAgentByEmail(user.getUsername());
            loggedIn.setAgent(agent);
            loggedIn.setToken(jwtUtils.generateToken(user));
            return ResponseEntity.ok(loggedIn);
        }
        return ResponseEntity.status(400).body("Some error has occurred");
    }

    @PostMapping("customer")
    public ResponseEntity<Object> customerAuthentication(
            @RequestBody AuthenticationRequest request
    ){
        setAuthenticationManager(request.getEmail(), request.getPassword());
        final UserDetails userCustomer = customerService.findByEmail(request.getEmail());
        if(userCustomer != null){
            LoggedIn loggedIn = new LoggedIn();
            Customer customer = customerService.getCustomerByEmail(request.getEmail());
            loggedIn.setCustomer(customer);
            loggedIn.setToken(jwtUtils.generateToken(userCustomer));
            return ResponseEntity.ok(loggedIn);
        }
        return ResponseEntity.status(400).body("Some error has occurred");
    }

    public void setAuthenticationManager(String email, String password){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
    }

}
