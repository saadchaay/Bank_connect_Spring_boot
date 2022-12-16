package com.bankconnect.repositories;

import com.bankconnect.entities.Virement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VirementRepository extends JpaRepository<Virement,Long> {
}
