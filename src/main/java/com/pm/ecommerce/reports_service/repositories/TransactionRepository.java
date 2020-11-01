package com.pm.ecommerce.reports_service.repositories;

import com.pm.ecommerce.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
}
