package com.example.laba71.service.imp;

import com.example.laba71.dto.BookListItemDto;
import com.example.laba71.dto.PageDto;
import com.example.laba71.mapper.BookMapper;
import com.example.laba71.model.Book;
import com.example.laba71.model.Loan;
import com.example.laba71.model.RequestStatus;
import com.example.laba71.model.User;
import com.example.laba71.repository.BookRepository;
import com.example.laba71.repository.LoanRequestRepository;
import com.example.laba71.repository.LoanRepository;
import com.example.laba71.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final LoanRequestRepository loanRequestRepository;

    @Override
    public LocalDate findExpectedAvailableAt(Book book) {
        return loanRepository.findTopByBookAndReturnedAtIsNullOrderByDueDateAsc(book)
                .map(Loan::getDueDate)
                .orElse(null);

    }

    private boolean isAvailable(Book b) {
        Integer ac = b.getAvailableCopies();
        return ac != null && ac > 0;
    }

    private LocalDate etaIfNeeded(Book b) {
        return isAvailable(b) ? null : findExpectedAvailableAt(b);
    }

    public BookListItemDto toListItem(Book book) {
        return bookMapper.toListItemDto(book, etaIfNeeded(book));
    }

    @Override
    public Page<BookListItemDto> search(String q, Integer year, Long categoryId, Pageable pageable) {
        var page = bookRepository.search((q == null || q.isBlank()) ? null : q.trim(), year, categoryId, pageable);
        return page.map(this::toListItem);
    }

    @Override
    public PageDto<BookListItemDto> getBookPage(Integer page, Integer size, Long categoryId) {
        var pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        var pageData = bookRepository.findPageWithCategory(categoryId, pageable);

        var content = pageData.getContent().stream()
                .map(book -> bookMapper.toDto(book, isAvailable(book) ? null : findExpectedAvailableAt(book)))
                .toList();

        return PageDto.<BookListItemDto>builder()
                .content(content)
                .page(pageData.getNumber())
                .size(pageData.getSize())
                .totalElements(pageData.getTotalElements())
                .totalPages(pageData.getTotalPages())
                .last(pageData.isLast())
                .build();
    }

    @Override
    public PageDto<BookListItemDto> getBookPage(
            Integer page, Integer size, Long categoryId,
            String q, Integer year, String sort) {

        Sort s = switch (sort == null ? "" : sort) {
            case "yearAsc" -> Sort.by(Sort.Direction.ASC, "publicationYear").and(Sort.by("title").ascending());
            case "yearDesc" -> Sort.by(Sort.Direction.DESC, "publicationYear").and(Sort.by("title").ascending());
            default -> Sort.by("title").ascending();
        };

        var pageable = PageRequest.of(page, size, s);
        var pageData = bookRepository.findPageFiltered(categoryId,
                (q == null || q.isBlank()) ? null : q.trim(),
                year, pageable);

        var content = pageData.getContent().stream()
                .map(book -> bookMapper.toDto(book, isAvailable(book) ? null : findExpectedAvailableAt(book)))
                .toList();

        return PageDto.<BookListItemDto>builder()
                .content(content)
                .page(pageData.getNumber())
                .size(pageData.getSize())
                .totalElements(pageData.getTotalElements())
                .totalPages(pageData.getTotalPages())
                .last(pageData.isLast())
                .build();
    }

    @Override
    public List<BookListItemDto> getBookList() {
        return bookRepository.findAllWithCategory().stream()
                .map(book -> bookMapper.toDto(book, isAvailable(book) ? null : findExpectedAvailableAt(book)))
                .toList();
    }

    //
    // @Override
    // @Transactional
    // public void borrowBook(Long bookId, User user) {
    // Book book = bookRepository.findById(bookId).orElseThrow(()->new
    // RuntimeException("ÐšÐ½Ð¸Ð³Ð° Ð½Ðµ Ð½Ð°Ð¹Ð´ÐµÐ½Ð°"));
    // Loan loan = Loan.builder()
    // .user(user)
    // .book(book)
    // .borrowDate(LocalDate.now())
    // .dueDate(LocalDate.now().plusWeeks(2))
    // .status(LoanStatus.EXPECTED)
    // .build();
    // loanRepository.save(loan);
    //
    // }
    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Книга не найдена"));
    }

    @Transactional
    @Override
    public void borrowBook(Long bookId, User user, LocalDate dueDate) {
        Book book = getBookById(bookId);

        if (book.getAvailableCopies() == null || book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("Книга недоступна");
        }

        List<Loan> activeLoans = loanRepository.findByUserAndReturnedAtIsNull(user);
        if (activeLoans.size() >= 3) {
            throw new IllegalStateException("Нельзя взять более 3-х книг одновременно");
        }

        Loan loan = Loan.builder()
                .user(user)
                .book(book)
                .borrowDate(LocalDate.now())
                .dueDate(dueDate)
                .build();

        loanRepository.save(loan);

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
    }

}
