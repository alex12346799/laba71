package com.example.laba71.service.imp;

import com.example.laba71.model.Loan;
import com.example.laba71.model.User;
import com.example.laba71.repository.LoanRepository;
import com.example.laba71.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class LoanServiceImp implements LoanService {
    private final LoanRepository loanRepository;
    @Override
    public List<Loan> findByUser(User user) {
        return loanRepository.findByUser(user);
    }
}
