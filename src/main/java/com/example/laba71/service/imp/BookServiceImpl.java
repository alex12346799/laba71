package com.example.laba71.service.imp;

import com.example.laba71.dto.BookListItemDto;
import com.example.laba71.dto.PageDto;
import com.example.laba71.mapper.BookMapper;
import com.example.laba71.model.Book;
import com.example.laba71.model.Loan;
import com.example.laba71.repository.BookRepository;
import com.example.laba71.repository.LoanRepository;
import com.example.laba71.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    @Override
    public LocalDate findExpectedAvailableAt(Book book) {
        return loanRepository.findTopByBookAndReturnedAtIsNullOrderByDueDateAsc(book)
                .map(Loan::getDueDate)
                .orElse(null);
        // или: return loanRepository.findEarliestDueDateByBook(book).orElse(null);
    }
    private boolean isAvailable(Book b) {
        Integer ac = b.getAvailableCopies();
        return ac != null && ac > 0;
    }

    private LocalDate etaIfNeeded(Book b) {
        return isAvailable(b) ? null : findExpectedAvailableAt(b);
    }

    // пример сборки DTO
    public BookListItemDto toListItem(Book book) {
        return bookMapper.toListItemDto(book, etaIfNeeded(book));
    }

    @Override
    public Page<BookListItemDto> search(String q, Integer year, Long categoryId, Pageable pageable) {
        var page = bookRepository.search((q==null||q.isBlank())?null:q.trim(), year, categoryId, pageable);
        return page.map(bookMapper::toListItemDto);
    }
    @Override
    public PageDto<BookListItemDto> getBookPage(Integer page, Integer size, Long categoryId) {
        var pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        var pageData = bookRepository.findPageWithCategory(categoryId, pageable);

        var content = pageData.getContent().stream()
                .map(book -> {
                    LocalDate expected = null;
                    if (book.getAvailableCopies() != null && book.getAvailableCopies() == 0) {
                        expected = loanRepository.findEarliestDueDateForBook(book.getId()).orElse(null);
                    }
                    return bookMapper.toDto(book, expected);
                })
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
            case "yearAsc"  -> Sort.by(Sort.Direction.ASC,  "publicationYear").and(Sort.by("title").ascending());
            case "yearDesc" -> Sort.by(Sort.Direction.DESC, "publicationYear").and(Sort.by("title").ascending());
            default         -> Sort.by("title").ascending();
        };

        var pageable = PageRequest.of(page, size, s);
        var pageData = bookRepository.findPageFiltered(categoryId,
                (q == null || q.isBlank()) ? null : q.trim(),
                year, pageable);

        var content = pageData.getContent().stream()
                .map(book -> {
                    LocalDate expected = null;
                    if (book.getAvailableCopies() != null && book.getAvailableCopies() == 0) {
                        expected = loanRepository.findEarliestDueDateForBook(book.getId()).orElse(null);
                    }
                    return bookMapper.toDto(book, expected);
                })
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
                .map(book -> {
                    LocalDate expectedDate = null;
                    if (book.getAvailableCopies() != null && book.getAvailableCopies() == 0) {
                        expectedDate = loanRepository.findEarliestDueDateForBook(book.getId())
                                .orElse(null);
                    }
                    return bookMapper.toDto(book, expectedDate);
                })
                .toList();    }
}
