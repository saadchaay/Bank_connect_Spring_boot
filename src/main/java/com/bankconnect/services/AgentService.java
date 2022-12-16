package com.bankconnect.services;

import com.bankconnect.entities.Agent;
import com.bankconnect.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Repository
public class AgentService {

    private final AgentRepository agentRepository;
    private final static List<UserDetails> APPLICATION_USERS = Arrays.asList(
            new User(
                    "agent@bank-connect.com",
                    "password",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_AGENT"))
            ),
            new User(
                    "customer@bank-connect.com",
                    "password",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
            )
    );

    public AgentService(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    public Agent save(Agent agent){
        return agentRepository.save(agent);
    }

    public List<Agent> listAll(){
        return agentRepository.findAll();
    }

    public UserDetails findByEmail(String email){
        return APPLICATION_USERS
                .stream()
                .filter(u -> u.getUsername().equals(email))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("No user was found"));
    }
}
