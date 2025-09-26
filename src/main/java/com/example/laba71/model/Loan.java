package com.example.laba71.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "loan", indexes = {
        @Index(name = "ix_loan_user", columnList = "user_id"),
        @Index(name = "ix_loan_book", columnList = "book_id"),
        @Index(name = "ix_loan_due", columnList = "due_date")
})
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name="planned_start_date")
    private LocalDate plannedStartDate;

    @NotNull
    @Column(name = "borrow_date")
    private LocalDate borrowDate;

    @NotNull
    @FutureOrPresent
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "returned_at")
    private LocalDate returnedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private LoanStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status", nullable = false)
    private RequestStatus requestStatus;

    @PrePersist
    void prePersist() {
        if (status == null) status = LoanStatus.EXPECTED;
    }
}
