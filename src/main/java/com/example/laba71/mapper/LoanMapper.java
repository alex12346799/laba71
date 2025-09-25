package com.example.laba71.mapper;

import com.example.laba71.model.Loan;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LoanMapper {

    public LocalDate getDueDate(Loan loan) {
        if (loan == null) {
            return null;
        }
        return loan.getDueDate();
    }
}
