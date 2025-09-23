package com.example.laba71.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "book", indexes = {
        @Index(name = "ix_book_title", columnList = "title"),
        @Index(name = "ix_book_author", columnList = "author")
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String author;

    @Column(length = 512)
    private String imageUrl;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @PositiveOrZero
    @Column(nullable = false)
    private Integer totalCopies;

    @PositiveOrZero @Column(nullable = false)
    private Integer availableCopies;
}
