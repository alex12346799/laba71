package com.example.laba71.service.imp;

import com.example.laba71.mapper.LoanMapper;
import com.example.laba71.model.Book;
import com.example.laba71.model.Loan;
import com.example.laba71.model.LoanStatus;
import com.example.laba71.model.User;
import com.example.laba71.repository.BookRepository;
import com.example.laba71.repository.LoanRepository;
import com.example.laba71.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanServiceImp implements LoanService {
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final BookRepository bookRepository;

//    public LocalDate findExpectedAvailableAt(Book book) {
//        return loanRepository.findTopByBookAndReturnedAtIsNullOrderByDueDateAsc(book)
//                .map(loanMapper::getDueDate)
//                .orElse(null);
//    }

    @Override
    public List<Loan> findByUser(User user) {
        return loanRepository.findByUser(user);
    }

    @Override
    public List<Loan> getLoansByBookId(Long bookId) {
        return loanRepository.findByBookId(bookId); // предполагается, что есть метод в репозитории
    }


    @Override
    public void handover(Long loanId, LocalDate when) {

    }

    @Override
    public void markReturned(Long loanId, LocalDate when) {

    }

    @Override
    public Optional<Loan> findActiveByBook(Long bookId) {
        return Optional.empty();
    }

    @Override
    public Optional<Loan> findExpectedByBook(Long bookId) {
        return Optional.empty();
    }

    @Override
    public void createLoan(Long bookId, User user, LocalDate dueDate) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Книга не найдена"));
        Loan loan = Loan.builder()
                .book(book)
                .user(user)
                .borrowDate(LocalDate.now())
                .dueDate(dueDate)
                .status(LoanStatus.EXPECTED)
                .build();
        loanRepository.save(loan);

    }
}


