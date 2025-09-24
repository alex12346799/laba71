package com.example.laba71.service;

import com.example.laba71.model.Loan;
import com.example.laba71.model.User;

import java.util.List;

public interface LoanService {
    List<Loan> findByUser(User user);
}
