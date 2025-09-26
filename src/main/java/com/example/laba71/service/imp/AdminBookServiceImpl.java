package com.example.laba71.service.imp;

import com.example.laba71.dto.BookListItemDto;
import com.example.laba71.dto.PageDto;
import com.example.laba71.model.Book;
import com.example.laba71.model.RequestStatus;
import com.example.laba71.repository.BookRepository;
import com.example.laba71.repository.LoanRepository;
import com.example.laba71.repository.LoanRequestRepository;
import com.example.laba71.service.AdminBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AdminBookServiceImpl implements AdminBookService {
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final LoanRequestRepository loanRequestRepository;

    @Override
    public PageDto<BookListItemDto> findBooks(String q, Integer year, Long categoryId,
                                              int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        var pageData = bookRepository.findPageFiltered(
                categoryId,
                (q == null || q.isBlank()) ? null : q.trim(),
                year,
                pageable
        );

        var content = pageData.getContent().stream().map(book -> {
            boolean hasCopies = book.getAvailableCopies() != null && book.getAvailableCopies() > 0;
            boolean hasPending = loanRequestRepository.existsByBookIdAndStatus(book.getId(), RequestStatus.PENDING);
            boolean available = hasCopies && !hasPending;

            LocalDate eta = null;
            if (!available) {
                eta = loanRepository.findEarliestDueDateForBook(book.getId()).orElseGet(() ->
                        loanRequestRepository.findEarliestRequestedDueDateForBook(book.getId(), RequestStatus.PENDING)
                                .orElse(null)
                );
            }

            return BookListItemDto.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .imageUrl(book.getImageUrl())
                    .categoryName(book.getCategory() != null ? book.getCategory().getName() : null)
                    .publicationYear(book.getPublicationYear())
                    .available(available)
                    .expectedAvailableAt(eta)
                    .availableCopies(book.getAvailableCopies())
                    .totalCopies(book.getTotalCopies())
                    .build();
        }).toList();

        return PageDto.<BookListItemDto>builder()
                .content(content)
                .page(pageData.getNumber())
                .size(pageData.getSize())
                .totalElements(pageData.getTotalElements())
                .totalPages(pageData.getTotalPages())
                .last(pageData.isLast())
                .build();
    }
}
