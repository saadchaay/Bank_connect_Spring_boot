package com.bankconnect.services;

import com.bankconnect.entities.Agent;
import com.bankconnect.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
}
