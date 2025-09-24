package com.example.laba71.repository;

import com.example.laba71.model.Loan;
import com.example.laba71.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUser(User user);
}
