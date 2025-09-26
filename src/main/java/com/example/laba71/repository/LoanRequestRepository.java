package com.example.laba71.repository;

import com.example.laba71.model.LoanRequest;
import com.example.laba71.model.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoanRequestRepository extends JpaRepository<LoanRequest, Long> {
    boolean existsByBookIdAndStatus(Long bookId, RequestStatus status);

    long countByUserIdAndStatus(Long userId, RequestStatus status);

    Optional<LoanRequest> findByIdAndStatus(Long id, RequestStatus status);

    Optional<LoanRequest> findFirstByBookIdAndStatusOrderByRequestedDueDateAsc(Long bookId, RequestStatus status);

    @Query("""
        select lr from LoanRequest lr
        join fetch lr.book b
        join fetch lr.user u
        where lr.status = :status
        order by lr.createdAt asc
    """)
    List<LoanRequest> findAllByStatusWithDetails(@Param("status") RequestStatus status);

    @Query("""
        select distinct lr.book.id
        from LoanRequest lr
        where lr.status = :status
    """)
    List<Long> findBookIdsByStatus(@Param("status") RequestStatus status);

    @Query("""
        select min(lr.requestedDueDate)
        from LoanRequest lr
        where lr.book.id = :bookId
          and lr.status = :status
    """)
    Optional<LocalDate> findEarliestRequestedDueDateForBook(@Param("bookId") Long bookId,
                                                            @Param("status") RequestStatus status);
}
