package com.example.laba71.mappers;

import com.example.laba71.dto.LoanAdminUpdateDto;
import com.example.laba71.dto.LoanViewDto;
import com.example.laba71.model.Loan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    LoanViewDto toDto(Loan loan);

    Loan toEntity(LoanAdminUpdateDto loan);
}
