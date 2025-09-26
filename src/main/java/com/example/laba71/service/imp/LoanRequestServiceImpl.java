package com.example.laba71.service.imp;

import com.example.laba71.dto.admin.LoanRequestAdminDto;
import com.example.laba71.model.Book;
import com.example.laba71.model.Loan;
import com.example.laba71.model.LoanRequest;
import com.example.laba71.model.LoanStatus;
import com.example.laba71.model.RequestStatus;
import com.example.laba71.model.User;
import com.example.laba71.repository.BookRepository;
import com.example.laba71.repository.LoanRepository;
import com.example.laba71.repository.LoanRequestRepository;
import com.example.laba71.repository.UserRepository;
import com.example.laba71.service.LoanRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanRequestServiceImpl implements LoanRequestService {
    private final LoanRequestRepository loanRequestRepository;
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public LoanRequest createRequest(Long bookId, String libraryCardNumber, LocalDate dueDate) {
        if (bookId == null) {
            throw new IllegalArgumentException("Не выбрана книга");
        }
        if (dueDate == null || dueDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Укажите корректную дату возврата");
        }
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Книга не найдена"));

        if (book.getAvailableCopies() == null || book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("Книга сейчас недоступна для выдачи");
        }

        User user = userRepository.findByLibraryCardNumber(libraryCardNumber)
                .orElseThrow(() -> new IllegalArgumentException("Читательский билет не найден"));

        long activeLoans = loanRepository.countByUserIdAndStatus(user.getId(), LoanStatus.EXPECTED)
                + loanRepository.countByUserIdAndStatus(user.getId(), LoanStatus.OVERDUE);
        long pendingRequests = loanRequestRepository.countByUserIdAndStatus(user.getId(), RequestStatus.PENDING);
        if (activeLoans + pendingRequests >= 3) {
            throw new IllegalStateException("У читателя уже есть 3 активные книги или заявки");
        }

        if (loanRequestRepository.existsByBookIdAndStatus(bookId, RequestStatus.PENDING)) {
            throw new IllegalStateException("По этой книге уже есть ожидающая заявка");
        }

        book.setAvailableCopies(Math.max(0, book.getAvailableCopies() - 1));
        bookRepository.save(book);

        LoanRequest request = new LoanRequest();
        request.setBook(book);
        request.setUser(user);
        request.setRequestedDueDate(dueDate);
        request.setStatus(RequestStatus.PENDING);
        request.setCreatedAt(LocalDateTime.now());
        return loanRequestRepository.save(request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanRequestAdminDto> getPendingRequests() {
        return loanRequestRepository.findAllByStatusWithDetails(RequestStatus.PENDING)
                .stream()
                .map(this::toAdminDto)
                .toList();
    }

    @Override
    @Transactional
    public void approve(Long requestId) {
        LoanRequest request = loanRequestRepository.findByIdAndStatus(requestId, RequestStatus.PENDING)
                .orElseThrow(() -> new IllegalArgumentException("Заявка не найдена или уже обработана"));

        User user = request.getUser();
        Book book = request.getBook();

        long activeLoans = loanRepository.countByUserIdAndStatus(user.getId(), LoanStatus.EXPECTED)
                + loanRepository.countByUserIdAndStatus(user.getId(), LoanStatus.OVERDUE);
        if (activeLoans >= 3) {
            throw new IllegalStateException("У читателя уже есть 3 активные книги");
        }

        Loan loan = Loan.builder()
                .user(user)
                .book(book)
                .borrowDate(LocalDate.now())
                .dueDate(request.getRequestedDueDate())
                .status(LoanStatus.EXPECTED)
                .build();
        loanRepository.save(loan);

        request.setStatus(RequestStatus.APPROVED);
        loanRequestRepository.save(request);
    }

    @Override
    @Transactional
    public void decline(Long requestId) {
        LoanRequest request = loanRequestRepository.findByIdAndStatus(requestId, RequestStatus.PENDING)
                .orElseThrow(() -> new IllegalArgumentException("Заявка не найдена или уже обработана"));

        Book book = request.getBook();
        if (book != null) {
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookRepository.save(book);
        }

        request.setStatus(RequestStatus.DECLINED);
        loanRequestRepository.save(request);
    }

    private LoanRequestAdminDto toAdminDto(LoanRequest request) {
        Book book = request.getBook();
        User user = request.getUser();
        return LoanRequestAdminDto.builder()
                .id(request.getId())
                .bookId(book != null ? book.getId() : null)
                .title(book != null ? book.getTitle() : null)
                .author(book != null ? book.getAuthor() : null)
                .category(book != null && book.getCategory() != null ? book.getCategory().getName() : null)
                .readerName(user != null ? buildUserName(user) : null)
                .libraryCardNumber(user != null ? user.getLibraryCardNumber() : null)
                .requestedDueDate(request.getRequestedDueDate())
                .createdAt(request.getCreatedAt())
                .build();
    }

    private String buildUserName(User user) {
        return Arrays.asList(user.getSurname(), user.getName(), user.getPatronymic())
                .stream()
                .filter(part -> part != null && !part.isBlank())
                .reduce((left, right) -> left + " " + right)
                .orElse(user.getName());
    }
}
