package com.bankconnect.repositories;

import com.bankconnect.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Request findByCustomerId(Long id);
}
