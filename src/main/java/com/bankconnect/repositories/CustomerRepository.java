package com.bankconnect.repositories;

import com.bankconnect.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer,Long> {

    @Modifying
    @Transactional
    @Query("update Customer c set c.status = :status where c.id = :customer")
    void activateAccount(@Param("status") boolean status, @Param("customer") Long id);
}
