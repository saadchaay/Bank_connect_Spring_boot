package com.bankconnect.repositories;

import com.bankconnect.entities.Agent;
import com.bankconnect.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findByEmail(String email);

}
