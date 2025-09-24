package com.example.laba71.repository;

import com.example.laba71.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    @Query("select b from Book b join fetch b.category")
    List<Book> findAllWithCategory();

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("update Book b set b.imageUrl = :image where b.id = :id")
    int saveBookImage(@Param("image") String image, @Param("id") Long id);

    @Query("select b.imageUrl from Book b where b.id = :id")
    Optional<String> getImageById(@Param("id") Long id);
}
