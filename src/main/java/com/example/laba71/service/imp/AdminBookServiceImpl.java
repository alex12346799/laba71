package com.example.laba71.service.imp;

import com.example.laba71.dto.BookListItemDto;
import com.example.laba71.dto.PageDto;
import com.example.laba71.model.Book;
import com.example.laba71.repository.BookRepository;
import com.example.laba71.repository.LoanRepository;
import com.example.laba71.service.AdminBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AdminBookServiceImpl implements AdminBookService {
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    @Override
    public PageDto<BookListItemDto> findBooks(String q, Integer year, Long categoryId,
                                              int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        var p = bookRepository.findPageFiltered(
                categoryId,
                (q == null || q.isBlank()) ? null : q.trim(),
                year,
                pageable
        );

        var ids = p.getContent().stream().map(Book::getId).toList();
        var busyIds = new HashSet<>(loanRepository.findBookIdsWithActiveLoans());

        var content = p.getContent().stream().map(b -> {
            boolean available = !busyIds.contains(b.getId());
            LocalDate eta = null;
            if (!available) {
                eta = loanRepository.findEarliestDueDateForBook(b.getId()).orElse(null);
            }

            return BookListItemDto.builder()
                    .id(b.getId())
                    .title(b.getTitle())
                    .author(b.getAuthor())
                    .imageUrl(b.getImageUrl())
                    .categoryName(b.getCategory() != null ? b.getCategory().getName() : null)
                    .publicationYear(b.getPublicationYear())
                    .available(available)
                    .expectedAvailableAt(eta)
                    .build();
        }).toList();

        return PageDto.<BookListItemDto>builder()
                .content(content)
                .page(p.getNumber())
                .size(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .last(p.isLast())
                .build();
    }
}
