package com.example.laba71.service;

import com.example.laba71.dto.admin.LoanRequestAdminDto;
import com.example.laba71.model.LoanRequest;

import java.time.LocalDate;
import java.util.List;

public interface LoanRequestService {
    LoanRequest createRequest(Long bookId, String libraryCardNumber, LocalDate dueDate);

    void approve(Long requestId);

    void decline(Long requestId);

    List<LoanRequestAdminDto> getPendingRequests();
}
