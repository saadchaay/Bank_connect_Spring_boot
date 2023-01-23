package com.bankconnect.repositories;

import com.bankconnect.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query("select trn from Transaction trn where trn.accountId = :account and year(trn.created) = :year")
    List<Transaction> getTransactionsPerYear(@Param("account") Long account ,@Param("year") int year);

    @Query("select trn from Transaction trn where trn.accountId = :account and day (trn.created) = :day")
    List<Transaction> getTransactionsPerDay(@Param("account") Long account ,@Param("day") int day);

}
