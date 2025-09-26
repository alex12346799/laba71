package com.example.laba71.service;

import com.example.laba71.dto.LoanSearchFilterDto;
import com.example.laba71.dto.LoanViewDto;
import com.example.laba71.dto.PageDto;

import java.time.LocalDate;

public interface AdminLoanService {
    PageDto<LoanViewDto> search(LoanSearchFilterDto f);

    void markReturned(Long loanId, LocalDate returnedAt);
}
