package com.bk.demo.transaction.repository;

import com.bk.demo.transaction.model.CreditTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditTransactionRepository extends JpaRepository<CreditTransaction, Long> {
}
