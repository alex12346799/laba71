package com.example.laba71.repository;

import com.example.laba71.model.Book;
import com.example.laba71.model.Loan;
import com.example.laba71.model.LoanStatus;
import com.example.laba71.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUser(User user);

    Optional<Loan> findTopByBookAndReturnedAtIsNullOrderByDueDateAsc(Book book);

    @Query("select l.dueDate from Loan l " +
            "where l.book = :book and l.returnedAt is null " +
            "order by l.dueDate asc")
    Optional<LocalDate> findEarliestDueDateByBook(@Param("book") Book book);


    @Query("""
  select count(l) from Loan l
  where l.book.id = :bookId and l.returnedAt is null
""")
    long countActiveByBookId(@Param("bookId") Long bookId);


    @Query("""
       select distinct l.book.id
       from Loan l
       where l.returnedAt is null
       """)
    List<Long> findBookIdsWithActiveLoans();

    @Query("""
       select min(l.dueDate)
       from Loan l
       where l.book.id = :bookId
         and l.returnedAt is null
       """)
    Optional<LocalDate> findEarliestDueDateForBook(@Param("bookId") Long bookId);


    Optional<Loan> findFirstByBookIdAndStatusOrderByDueDateDesc(Long bookId, LoanStatus status);   // активная
    Optional<Loan> findFirstByBookIdAndStatusOrderByPlannedStartDateDesc(Long bookId, LoanStatus status);
    boolean existsByUserIdAndStatusIn(Long userId, Collection<LoanStatus> statuses);
    long countByUserIdAndStatus( Long userId, LoanStatus status );
}
