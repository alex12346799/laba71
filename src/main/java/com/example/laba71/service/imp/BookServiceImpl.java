package com.example.laba71.service.imp;

import com.example.laba71.dto.BookListItemDto;
import com.example.laba71.mapper.BookMapper;
import com.example.laba71.model.Book;
import com.example.laba71.model.Loan;
import com.example.laba71.model.LoanStatus;
import com.example.laba71.model.User;
import com.example.laba71.repository.BookRepository;
import com.example.laba71.repository.LoanRepository;
import com.example.laba71.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    @Override
    public List<BookListItemDto> getBookList() {
        return bookRepository.findAllWithCategory().stream()
                .map(book -> {
                    LocalDate expectedDate = null;
                    if (book.getAvailableCopies() != null && book.getAvailableCopies() == 0) {
                        expectedDate = loanRepository.findEarliestDueDateForBook(book.getId())
                                .orElse(null);
                    }
                    return bookMapper.toDto(book, expectedDate);
                })
                .toList();    }

    @Override
    @Transactional
    public void borrowBook(Long bookId, User user) {
        Book book = bookRepository.findById(bookId).orElseThrow(()->new RuntimeException("Книга не найдена"));
        Loan loan = Loan.builder()
                .user(user)
                .book(book)
                .borrowDate(LocalDate.now())
                .dueDate(LocalDate.now().plusWeeks(2))
                .status(LoanStatus.EXPECTED)
                .build();
        loanRepository.save(loan);

    }
}
