package com.bankconnect.services;

import com.bankconnect.entities.Agent;
import com.bankconnect.helpers.Enum;
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
import java.util.Optional;

@Service
@Repository
public class AgentService {

    private final AgentRepository agentRepository;

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
        Agent user = agentRepository.findAll()
                .stream()
                .filter(u -> (u.getEmail()).equals(email))
                .findFirst()
                .orElse(null);
        return user != null ? new User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(Enum.role.AGENT.toString()))) : null ;
    }
}
