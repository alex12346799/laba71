package com.example.laba71.service.imp;

import com.example.laba71.mapper.LoanMapper;
import com.example.laba71.model.Book;
import com.example.laba71.model.Loan;
import com.example.laba71.model.User;
import com.example.laba71.repository.LoanRepository;
import com.example.laba71.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class LoanServiceImp implements LoanService {
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;

    public LocalDate findExpectedAvailableAt(Book book) {
        return loanRepository.findTopByBookAndReturnedAtIsNullOrderByDueDateAsc(book)
                .map(loanMapper::getDueDate)
                .orElse(null);
    }

    @Override
    public List<Loan> findByUser(User user) {
        return loanRepository.findByUser(user);
    }

@Override
public List<Loan> getLoansByBookId(Long bookId) {
        return loanRepository.findByBookId(bookId); // предполагается, что есть метод в репозитории
    }


}


