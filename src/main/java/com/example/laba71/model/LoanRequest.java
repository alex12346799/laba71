package com.example.laba71.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "loan_request",
        indexes = {
                @Index(name="ix_req_book", columnList="book_id"),
                @Index(name="ix_req_user", columnList="user_id"),
                @Index(name="ix_req_status", columnList="status")
        })
public class LoanRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="book_id")
    private Book book;

    @Column(name="requested_due_date", nullable=false)
    private LocalDate requestedDueDate;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false, length=20)
    private RequestStatus status;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;

    @PrePersist void pre() {
        createdAt = LocalDateTime.now();
        if (status == null) status = RequestStatus.PENDING;
    }
}
