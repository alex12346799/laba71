package com.example.laba71.service;

import com.example.laba71.model.Loan;
import com.example.laba71.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoanService {
    List<Loan> findByUser(User user);
    void handover(Long loanId, LocalDate when);       // EXPECTED -> ACTIVE
    void markReturned(Long loanId, LocalDate when);   // ACTIVE -> RETURNED
    Optional<Loan> findActiveByBook(Long bookId);
    Optional<Loan> findExpectedByBook(Long bookId);
}
