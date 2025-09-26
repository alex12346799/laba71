package com.example.laba71.service.imp;

import com.example.laba71.dto.LoanSearchFilterDto;
import com.example.laba71.dto.LoanViewDto;
import com.example.laba71.dto.PageDto;
import com.example.laba71.mapper.LoanMapper;
import com.example.laba71.model.Loan;
import com.example.laba71.model.LoanStatus;
import com.example.laba71.repository.BookRepository;
import com.example.laba71.repository.LoanRepository;
import com.example.laba71.service.AdminLoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminLoanServiceImpl implements AdminLoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final LoanMapper loanMapper;

    @Transactional
    public void markReturned(Long loanId, LocalDate returnedAt) {
        Loan loan = loanRepository.findById(loanId).orElseThrow();
        LocalDate effectiveReturnDate = returnedAt != null ? returnedAt : LocalDate.now();
        loan.setReturnedAt(effectiveReturnDate);
        loan.setStatus(LoanStatus.RETURNED);

        if (loan.getBook() != null) {
            var book = loan.getBook();
            int copies = book.getAvailableCopies() == null ? 0 : book.getAvailableCopies();
            book.setAvailableCopies(copies + 1);
            bookRepository.save(book);
        }

        loanRepository.save(loan);
    }

    @Override
    public PageDto<LoanViewDto> search(LoanSearchFilterDto f) {
        String card = (f.getLibraryCardNumber() != null && !f.getLibraryCardNumber().isBlank())
                ? f.getLibraryCardNumber().trim() : null;

        Long bookId = f.getBookId();

        LocalDate from = f.getFromDate();
        LocalDate to   = f.getToDate();
        if (from != null && to != null && to.isBefore(from)) {
            LocalDate tmp = from;
            from = to;
            to   = tmp;
        }

        String status = (f.getStatus() != null && !f.getStatus().isBlank())
                ? f.getStatus().trim().toUpperCase() : null;

        Sort sort = Sort.by(Sort.Direction.fromString(f.getSortDir()), f.getSortBy());
        List<Loan> all = loanRepository.findAll(sort);

        final LocalDate ff = from;
        final LocalDate tt = to;

        List<Loan> filtered = all.stream()
                .filter(l -> card == null ||
                        (l.getUser() != null &&
                                card.equalsIgnoreCase(l.getUser().getLibraryCardNumber())))
                .filter(l -> bookId == null ||
                        (l.getBook() != null && bookId.equals(l.getBook().getId())))
                .filter(l -> ff == null || !l.getBorrowDate().isBefore(ff))
                .filter(l -> tt == null || !l.getBorrowDate().isAfter(tt))
                .filter(l -> status == null ||
                        (l.getStatus() != null && l.getStatus().name().equalsIgnoreCase(status)))
                .toList();

        int page = Math.max(0, f.getPage());
        int size = Math.min(200, Math.max(1, f.getSize()));
        int fromIdx = Math.min(page * size, filtered.size());
        int toIdx   = Math.min(fromIdx + size, filtered.size());

        List<LoanViewDto> content = filtered.subList(fromIdx, toIdx)
                .stream()
                .map(loanMapper::toViewDto)
                .toList();

        int total = filtered.size();
        int totalPages = (int) Math.ceil(total / (double) size);
        boolean last = totalPages == 0 || page >= totalPages - 1;

        return PageDto.of(content, page, size, total, totalPages, last);
    }
}
