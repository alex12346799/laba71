package com.example.laba71.mapper;

import com.example.laba71.dto.LoanDto;
import com.example.laba71.dto.LoanViewDto;
import com.example.laba71.model.Loan;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class LoanMapper {

    public LocalDate getDueDate(Loan loan) {
        return loan == null ? null : loan.getDueDate();
    }

    public LoanDto toDto(Loan loan) {
        if (loan == null) {
            return null;
        }
        LoanDto loanDto = new LoanDto();
        loanDto.setId(loan.getId());
        loanDto.setBookId(loan.getBook().getId());
        loanDto.setUserId(loan.getUser().getId());
        loanDto.setDueDate(loan.getDueDate());
        loanDto.setStatus(loan.getStatus());
        loanDto.setBorrowDate(loan.getBorrowDate());
        loanDto.setDueDate(loan.getDueDate());
        return loanDto;
    }

    public LoanViewDto toViewDto(Loan l) {
        String readerName = null;
        String card = null;
        if (l.getUser() != null) {
            var u = l.getUser();
            readerName = Stream.of(u.getSurname(), u.getName(), u.getPatronymic())
                    .filter(Objects::nonNull)
                    .filter(s -> !s.isBlank())
                    .collect(Collectors.joining(" "));
            if (readerName.isBlank()) readerName = u.getName();
            card = u.getLibraryCardNumber();
        }

        return LoanViewDto.builder()
                .loanId(l.getId())
                .bookId(l.getBook() != null ? l.getBook().getId() : null)
                .title(l.getBook() != null ? l.getBook().getTitle() : null)
                .author(l.getBook() != null ? l.getBook().getAuthor() : null)
                .borrowDate(l.getBorrowDate())
                .dueDate(l.getDueDate())
                .returnedAt(l.getReturnedAt())
                .status(l.getStatus() != null ? l.getStatus().name() : null)
                .readerName(readerName)
                .libraryCardNumber(card)
                .build();
    }
}
