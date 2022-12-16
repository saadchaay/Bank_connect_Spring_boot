package com.bankconnect.repositories;

import com.bankconnect.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
    public Account getById( Long id);
}
