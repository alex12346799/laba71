package com.example.laba71.repository;

import com.example.laba71.model.Book;
import com.example.laba71.model.Loan;
import com.example.laba71.model.LoanStatus;
import com.example.laba71.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
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

    Optional<Loan> findTopByBookAndReturnedAtIsNullOrderByDueDateAsc(Book book);

    @Query("select l.dueDate from Loan l " +
            "where l.book = :book and l.returnedAt is null " +
            "order by l.dueDate asc")
    Optional<LocalDate> findEarliestDueDateByBook(@Param("book") Book book);



    List<Loan> findByUserAndReturnedAtIsNull(User user);

    List<Loan> findByBookId(Long bookId);
}
