package com.example.laba71.mappers;

import com.example.laba71.dto.*;
import com.example.laba71.model.Book;
import com.example.laba71.model.Loan;
import com.example.laba71.model.User;
import org.mapstruct.*;

@Mapper(config = CentralMapperConfig.class, uses = {BookMapper.class, UserMapper.class})
public interface LoanMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "borrowDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "returnedAt", ignore = true)
    @Mapping(
            target = "status",
            expression = "java(com.example.laba71.model.LoanStatus.EXPECTED)"
    )    Loan fromCreateDto(LoanRequestDto dto);

    @Mapping(target = "loanId", source = "id")
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "title",  source = "book.title")
    @Mapping(target = "author", source = "book.author")
    @Mapping(target = "status", expression = "java(loan.getStatus().name())")
    LoanViewDto toViewDto(Loan loan);

    @Mapping(target = "loan", expression = "java(toLoanRequestDto(loan))")
    @Mapping(target = "bookInfo", source = "book")
    LoanWithBookDto toWithBookDto(Loan loan);

    default LoanRequestDto toLoanRequestDto(Loan loan) {
        LoanRequestDto dto = new LoanRequestDto();
        dto.setLibraryCardNumber(loan.getUser() != null ? loan.getUser().getLibraryCardNumber() : null);
        dto.setBookId(loan.getBook() != null ? loan.getBook().getId() : null);
        dto.setDueDate(loan.getDueDate());
        return dto;
    }

    default Loan attach(Loan loan, User user, Book book) {
        loan.setUser(user);
        loan.setBook(book);
        return loan;
    }
}
