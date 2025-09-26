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
                .orElseGet(() -> loanRequestRepository
                        .findEarliestRequestedDueDateForBook(book.getId(), RequestStatus.PENDING)
                        .orElse(null));
    }

    private boolean hasPendingRequest(Book book) {
        return loanRequestRepository.existsByBookIdAndStatus(book.getId(), RequestStatus.PENDING);
    }

    private boolean isAvailable(Book book) {
        Integer copies = book.getAvailableCopies();
        boolean hasCopies = copies != null && copies > 0;
        return hasCopies && !hasPendingRequest(book);
    }

    private BookListItemDto buildListItem(Book book) {
        LocalDate eta = isAvailable(book) ? null : findExpectedAvailableAt(book);
        BookListItemDto dto = bookMapper.toListItemDto(book, eta);
        dto.setAvailable(isAvailable(book));
        dto.setAvailableCopies(book.getAvailableCopies());
        dto.setTotalCopies(book.getTotalCopies());
        dto.setExpectedAvailableAt(eta);
        return dto;
    }

    private BookListItemDto buildCardDto(Book book) {
        LocalDate eta = isAvailable(book) ? null : findExpectedAvailableAt(book);
        BookListItemDto dto = bookMapper.toDto(book, eta);
        dto.setAvailable(isAvailable(book));
        dto.setAvailableCopies(book.getAvailableCopies());
        dto.setTotalCopies(book.getTotalCopies());
        dto.setExpectedAvailableAt(eta);
        return dto;
    }

    @Override
    public Page<BookListItemDto> search(String q, Integer year, Long categoryId, Pageable pageable) {
        Page<Book> page = bookRepository.search((q == null || q.isBlank()) ? null : q.trim(), year, categoryId, pageable);
        return page.map(this::buildListItem);
    }

    @Override
    public PageDto<BookListItemDto> getBookPage(Integer page, Integer size, Long categoryId) {
        var pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        var pageData = bookRepository.findPageWithCategory(categoryId, pageable);

        var content = pageData.getContent().stream()
                .map(this::buildCardDto)
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
    public PageDto<BookListItemDto> getBookPage(Integer page, Integer size, Long categoryId,
                                                String q, Integer year, String sort) {
        Sort sortOption = switch (sort == null ? "" : sort) {
            case "yearAsc" -> Sort.by(Sort.Direction.ASC, "publicationYear").and(Sort.by("title").ascending());
            case "yearDesc" -> Sort.by(Sort.Direction.DESC, "publicationYear").and(Sort.by("title").ascending());
            default -> Sort.by("title").ascending();
        };

        var pageable = PageRequest.of(page, size, sortOption);
        var pageData = bookRepository.findPageFiltered(categoryId,
                (q == null || q.isBlank()) ? null : q.trim(),
                year, pageable);

        var content = pageData.getContent().stream()
                .map(this::buildCardDto)
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
                .map(this::buildCardDto)
                .toList();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Книга не найдена"));
    }

    @Transactional
    @Override
    public void borrowBook(Long bookId, User user, LocalDate dueDate) {
        Book book = getBookById(bookId);
        if (!isAvailable(book)) {
            throw new IllegalStateException("Книга недоступна");
        }

        List<Loan> activeLoans = loanRepository.findByUserAndReturnedAtIsNull(user);
        if (activeLoans.size() >= 3) {
            throw new IllegalStateException("У читателя уже есть три активных книги");
        }

        Loan loan = Loan.builder()
                .user(user)
                .book(book)
                .borrowDate(LocalDate.now())
                .dueDate(dueDate)
                .build();

        loanRepository.save(loan);

        Integer copies = book.getAvailableCopies();
        book.setAvailableCopies(copies == null ? 0 : Math.max(0, copies - 1));
        bookRepository.save(book);
    }
}
