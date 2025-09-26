package com.example.laba71.service;

import com.example.laba71.dto.LoanSearchFilterDto;
import com.example.laba71.dto.LoanViewDto;
import com.example.laba71.dto.PageDto;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface AdminLoanService {
    PageDto<LoanViewDto> search(LoanSearchFilterDto f);

    void markReturned(Long loanId, LocalDate returnedAt);

    List<LoanViewDto> getPendingLoans();

    @Transactional
    void markApproved(Long loanId);

    @Transactional
    void markRejected(Long loanId);
}
