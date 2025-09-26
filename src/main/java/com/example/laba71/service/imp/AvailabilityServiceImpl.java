package com.example.laba71.service.imp;

import com.example.laba71.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl {
    private final LoanRepository loanRepo;

}
