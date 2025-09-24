package com.example.laba71.repository;

import com.example.laba71.model.Loan;
import com.example.laba71.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("""
           select min(l.dueDate)
           from Loan l
           where l.book.id = :bookId
             and l.returnedAt is null
             and l.status in (com.example.laba71.model.LoanStatus.EXPECTED, 
                              com.example.laba71.model.LoanStatus.OVERDUE)
           """)
    Optional<LocalDate> findEarliestDueDateForBook(@Param("bookId") Long bookId);
    List<Loan> findByUser(User user);
}
