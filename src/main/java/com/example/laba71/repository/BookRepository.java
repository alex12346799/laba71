package com.example.laba71.repository;

import com.example.laba71.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(
            value = """
        select distinct b
        from Book b
        join fetch b.category c
        where (:categoryId is null or c.id = :categoryId)
      """,
            countQuery = """
        select count(b)
        from Book b
        join b.category c
        where (:categoryId is null or c.id = :categoryId)
      """
    )
    Page<Book> findPageWithCategory(@Param("categoryId") Long categoryId, Pageable pageable);


    @Query(
            value = """
    select distinct b
    from Book b
    join fetch b.category c
    where (:categoryId is null or c.id = :categoryId)
      and (
          (:q is null)
          or lower(b.title) like lower(concat('%', :q, '%'))
          or lower(b.author) like lower(concat('%', :q, '%'))
      )
      and (:year is null or b.publicationYear = :year)
  """,
            countQuery = """
    select count(b)
    from Book b
    join b.category c
    where (:categoryId is null or c.id = :categoryId)
      and (
          (:q is null)
          or lower(b.title) like lower(concat('%', :q, '%'))
          or lower(b.author) like lower(concat('%', :q, '%'))
      )
      and (:year is null or b.publicationYear = :year)
  """
    )
    Page<Book> findPageFiltered(
            @Param("categoryId") Long categoryId,
            @Param("q") String q,
            @Param("year") Integer year,
            Pageable pageable
    );

    @Query("""
           select b from Book b
             join fetch b.category c
           where (:categoryId is null or c.id = :categoryId)
             and (:q is null or lower(b.title) like lower(concat('%', :q, '%')) or lower(b.author) like lower(concat('%', :q, '%')))
             and (:publicationYear is null or b.publicationYear = :publicationYear)
           """)
    Page<Book> search(@Param("q") String q,
                      @Param("publicationYear") Integer year,
                      @Param("categoryId") Long categoryId,
                      Pageable pageable);
}
