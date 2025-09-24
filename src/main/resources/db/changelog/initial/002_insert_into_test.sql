INSERT INTO category(name)
VALUES ('Programming'),
       ('Fantasy'),
       ('Science Fiction'),
       ('Classics'),
       ('History'),
       ('Biography'),
       ('Philosophy'),
       ('Psychology'),
       ('Productivity'),
       ('Mystery');

INSERT INTO users(name, surname, patronymic, address, passport_number, library_card_number, password, enabled,
                  role_name)
VALUES ('Reader1', 'Ivanov', 'Ivanovich', 'Bishkek', 'RPPN0001', 'R0001',
        '$2a$10$Iitv.3WtSkUwWMlrfhM4quNPIZgSSNFvoJIRZrADASoE0eqZtMdsu', TRUE, 'ROLE_READER'),
       ('Reader2', 'Petrov', 'Petrovich', 'Bishkek', 'RPPN0002', 'R0002',
        '$2a$10$Iitv.3WtSkUwWMlrfhM4quNPIZgSSNFvoJIRZrADASoE0eqZtMdsu', TRUE, 'ROLE_READER'),
       ('Reader3', 'Sidorov', 'Sidorovich', 'Osh', 'RPPN0003', 'R0003',
        '$2a$10$Iitv.3WtSkUwWMlrfhM4quNPIZgSSNFvoJIRZrADASoE0eqZtMdsu', TRUE, 'ROLE_READER'),
       ('Reader4', 'Abdullaev', 'Kanybekovich', 'Karakol', 'RPPN0004', 'R0004',
        '$2a$10$Iitv.3WtSkUwWMlrfhM4quNPIZgSSNFvoJIRZrADASoE0eqZtMdsu', TRUE, 'ROLE_READER'),
       ('Reader5', 'Tursunov', 'Bakytovich', 'Talas', 'RPPN0005', 'R0005',
        '$2a$10$Iitv.3WtSkUwWMlrfhM4quNPIZgSSNFvoJIRZrADASoE0eqZtMdsu', TRUE, 'ROLE_READER'),
       ('Reader6', 'Usenov', 'Maratovich', 'Naryn', 'RPPN0006', 'R0006',
        '$2a$10$Iitv.3WtSkUwWMlrfhM4quNPIZgSSNFvoJIRZrADASoE0eqZtMdsu', TRUE, 'ROLE_READER'),
       ('Reader7', 'Kozhoev', 'Azamatovich', 'Batken', 'RPPN0007', 'R0007',
        '$2a$10$Iitv.3WtSkUwWMlrfhM4quNPIZgSSNFvoJIRZrADASoE0eqZtMdsu', TRUE, 'ROLE_READER'),
       ('Reader8', 'Satybaldi', 'Tilekovich', 'Tokmok', 'RPPN0008', 'R0008',
        '$2a$10$Iitv.3WtSkUwWMlrfhM4quNPIZgSSNFvoJIRZrADASoE0eqZtMdsu', TRUE, 'ROLE_READER'),
       ('Reader9', 'Zhumabaev', 'Erkinovich', 'Kant', 'RPPN0009', 'R0009',
        '$2a$10$Iitv.3WtSkUwWMlrfhM4quNPIZgSSNFvoJIRZrADASoE0eqZtMdsu', TRUE, 'ROLE_READER'),
       ('Reader10', 'Omurov', 'Aidarovich', 'Balykchy', 'RPPN0010', 'R0010',
        '$2a$10$Iitv.3WtSkUwWMlrfhM4quNPIZgSSNFvoJIRZrADASoE0eqZtMdsu', TRUE, 'ROLE_READER'),
       ('Admin1', 'Adminov', 'Sys', 'Bishkek', 'ADMN0001', 'A0001',
        '$2a$10$Iitv.3WtSkUwWMlrfhM4quNPIZgSSNFvoJIRZrADASoE0eqZtMdsu', TRUE, 'ROLE_ADMIN'),
       ('Admin2', 'Root', 'Sys', 'Bishkek', 'ADMN0002', 'A0002',
        '$2a$10$Iitv.3WtSkUwWMlrfhM4quNPIZgSSNFvoJIRZrADASoE0eqZtMdsu', TRUE, 'ROLE_ADMIN');


INSERT INTO book(title, author, image_url, category_id, total_copies, available_copies)
VALUES ('Clean Code', 'Robert C. Martin', 'image1.jpg', (SELECT id FROM category WHERE name = 'Programming'), 8, 5),
       ('The Pragmatic Programmer', 'Andrew Hunt; David Thomas', 'image2.jpg',
        (SELECT id FROM category WHERE name = 'Programming'), 7, 3),
       ('Effective Java', 'Joshua Bloch', 'image3.jpg', (SELECT id FROM category WHERE name = 'Programming'), 6, 2),
       ('Refactoring', 'Martin Fowler', 'image4.jpg', (SELECT id FROM category WHERE name = 'Programming'), 6, 0),
       ('Design Patterns', 'Erich Gamma; Richard Helm; Ralph Johnson; John Vlissides', 'image5.jpg',
        (SELECT id FROM category WHERE name = 'Programming'), 5, 1),
       ('Clean Architecture', 'Robert C. Martin', 'image6.jpg', (SELECT id FROM category WHERE name = 'Programming'), 5,
        2),
       ('Working Effectively with Legacy Code', 'Michael Feathers', 'image7.jpg',
        (SELECT id FROM category WHERE name = 'Programming'), 4, 1),
       ('Patterns of Enterprise Application Architecture', 'Martin Fowler', 'image8.jpg',
        (SELECT id FROM category WHERE name = 'Programming'), 4, 0),
       ('Domain-Driven Design', 'Eric Evans', 'image9.jpg', (SELECT id FROM category WHERE name = 'Programming'), 5, 2),
       ('Java Concurrency in Practice', 'Brian Goetz', 'image10.jpg',
        (SELECT id FROM category WHERE name = 'Programming'), 5, 1),

       ('Head First Design Patterns', 'Eric Freeman; Elisabeth Robson', 'image11.jpg',
        (SELECT id FROM category WHERE name = 'Programming'), 6, 3),
       ('You Don''t Know JS', 'Kyle Simpson', 'image12.jpg', (SELECT id FROM category WHERE name = 'Programming'), 6,
        4),
       ('Introduction to Algorithms', 'Thomas H. Cormen et al.', 'image13.jpg',
        (SELECT id FROM category WHERE name = 'Programming'), 5, 0),

       ('The Lord of the Rings', 'J. R. R. Tolkien', 'image14.jpg', (SELECT id FROM category WHERE name = 'Fantasy'),
        10, 4),
       ('The Hobbit', 'J. R. R. Tolkien', 'image15.jpg', (SELECT id FROM category WHERE name = 'Fantasy'), 8, 1),
       ('Harry Potter and the Sorcerer''s Stone', 'J. K. Rowling', 'image16.jpg',
        (SELECT id FROM category WHERE name = 'Fantasy'), 9, 2),
       ('The Name of the Wind', 'Patrick Rothfuss', 'image17.jpg', (SELECT id FROM category WHERE name = 'Fantasy'), 6,
        0),
       ('Mistborn', 'Brandon Sanderson', 'image18.jpg', (SELECT id FROM category WHERE name = 'Fantasy'), 7, 3),

       ('Dune', 'Frank Herbert', 'image19.jpg', (SELECT id FROM category WHERE name = 'Science Fiction'), 8, 2),
       ('Foundation', 'Isaac Asimov', 'image20.jpg', (SELECT id FROM category WHERE name = 'Science Fiction'), 7, 1),
       ('Neuromancer', 'William Gibson', 'image21.jpg', (SELECT id FROM category WHERE name = 'Science Fiction'), 6, 2),
       ('Ender''s Game', 'Orson Scott Card', 'image22.jpg', (SELECT id FROM category WHERE name = 'Science Fiction'), 6,
        3),
       ('Snow Crash', 'Neal Stephenson', 'image23.jpg', (SELECT id FROM category WHERE name = 'Science Fiction'), 5, 0),

       ('1984', 'George Orwell', 'image24.jpg', (SELECT id FROM category WHERE name = 'Classics'), 8, 3),
       ('Brave New World', 'Aldous Huxley', 'image25.jpg', (SELECT id FROM category WHERE name = 'Classics'), 7, 2),
       ('To Kill a Mockingbird', 'Harper Lee', 'image26.jpg', (SELECT id FROM category WHERE name = 'Classics'), 8, 4),
       ('The Great Gatsby', 'F. Scott Fitzgerald', 'image27.jpg', (SELECT id FROM category WHERE name = 'Classics'), 7,
        1),
       ('Crime and Punishment', 'Fyodor Dostoevsky', 'image28.jpg', (SELECT id FROM category WHERE name = 'Classics'),
        9, 2),
       ('War and Peace', 'Leo Tolstoy', 'image29.jpg', (SELECT id FROM category WHERE name = 'Classics'), 6, 1),
       ('Pride and Prejudice', 'Jane Austen', 'image30.jpg', (SELECT id FROM category WHERE name = 'Classics'), 7, 2),
       ('Moby-Dick', 'Herman Melville', 'image31.jpg', (SELECT id FROM category WHERE name = 'Classics'), 5, 1),
       ('The Catcher in the Rye', 'J. D. Salinger', 'image32.jpg', (SELECT id FROM category WHERE name = 'Classics'), 6,
        3),
       ('The Little Prince', 'Antoine de Saint-Exupéry', 'image33.jpg',
        (SELECT id FROM category WHERE name = 'Classics'), 8, 5),

       ('Sapiens', 'Yuval Noah Harari', 'image34.jpg', (SELECT id FROM category WHERE name = 'History'), 7, 2),
       ('Homo Deus', 'Yuval Noah Harari', 'image35.jpg', (SELECT id FROM category WHERE name = 'History'), 6, 1),
       ('Guns, Germs, and Steel', 'Jared Diamond', 'image36.jpg', (SELECT id FROM category WHERE name = 'History'), 6,
        2),

       ('Steve Jobs', 'Walter Isaacson', 'image37.jpg', (SELECT id FROM category WHERE name = 'Biography'), 6, 2),
       ('Elon Musk', 'Walter Isaacson', 'image38.jpg', (SELECT id FROM category WHERE name = 'Biography'), 6, 3),
       ('Educated', 'Tara Westover', 'image39.jpg', (SELECT id FROM category WHERE name = 'Biography'), 5, 1),

       ('Meditations', 'Marcus Aurelius', 'image40.jpg', (SELECT id FROM category WHERE name = 'Philosophy'), 7, 4),
       ('Beyond Good and Evil', 'Friedrich Nietzsche', 'image41.jpg',
        (SELECT id FROM category WHERE name = 'Philosophy'), 6, 2),
       ('The Art of War', 'Sun Tzu', 'image42.jpg', (SELECT id FROM category WHERE name = 'Philosophy'), 8, 5),

       ('Thinking, Fast and Slow', 'Daniel Kahneman', 'image43.jpg',
        (SELECT id FROM category WHERE name = 'Psychology'), 7, 2),
       ('Influence', 'Robert Cialdini', 'image44.jpg', (SELECT id FROM category WHERE name = 'Psychology'), 7, 3),

       ('Atomic Habits', 'James Clear', 'image45.jpg', (SELECT id FROM category WHERE name = 'Productivity'), 8, 4),
       ('Deep Work', 'Cal Newport', 'image46.jpg', (SELECT id FROM category WHERE name = 'Productivity'), 7, 2),

       ('The Girl with the Dragon Tattoo', 'Stieg Larsson', 'image47.jpg',
        (SELECT id FROM category WHERE name = 'Mystery'), 7, 3),
       ('Gone Girl', 'Gillian Flynn', 'image48.jpg', (SELECT id FROM category WHERE name = 'Mystery'), 6, 1),
       ('The Da Vinci Code', 'Dan Brown', 'image49.jpg', (SELECT id FROM category WHERE name = 'Mystery'), 8, 4),

       ('Refactoring UI', 'Adam Wathan; Steve Schoger', 'image50.jpg',
        (SELECT id FROM category WHERE name = 'Programming'), 4, 2);


INSERT INTO loan(user_id, book_id, borrow_date, due_date, returned_at, status)
VALUES ((SELECT id FROM users WHERE library_card_number = 'R0001'),
        (SELECT id FROM book WHERE title = 'Refactoring'),
        DATE '2025-09-10', DATE '2025-10-01', NULL, 'EXPECTED'),

       ((SELECT id FROM users WHERE library_card_number = 'R0002'),
        (SELECT id FROM book WHERE title = 'Patterns of Enterprise Application Architecture'),
        DATE '2025-09-05', DATE '2025-09-28', NULL, 'OVERDUE'),

       ((SELECT id FROM users WHERE library_card_number = 'R0003'),
        (SELECT id FROM book WHERE title = 'Snow Crash'),
        DATE '2025-09-12', DATE '2025-10-05', NULL, 'EXPECTED'),

       ((SELECT id FROM users WHERE library_card_number = 'R0004'),
        (SELECT id FROM book WHERE title = 'The Name of the Wind'),
        DATE '2025-09-01', DATE '2025-09-25', NULL, 'OVERDUE'),

       ((SELECT id FROM users WHERE library_card_number = 'R0005'),
        (SELECT id FROM book WHERE title = 'Refactoring'),
        DATE '2025-09-15', DATE '2025-10-06', NULL, 'EXPECTED'),

       ((SELECT id FROM users WHERE library_card_number = 'R0006'),
        (SELECT id FROM book WHERE title = 'Introduction to Algorithms'),
        DATE '2025-09-03', DATE '2025-10-03', NULL, 'EXPECTED'),

       -- Несколько возвращённых (не участвуют в expectedAvailableAt)
       ((SELECT id FROM users WHERE library_card_number = 'R0007'),
        (SELECT id FROM book WHERE title = 'Clean Code'),
        DATE '2025-08-20', DATE '2025-09-10', DATE '2025-09-09', 'RETURNED'),

       ((SELECT id FROM users WHERE library_card_number = 'R0008'),
        (SELECT id FROM book WHERE title = 'Dune'),
        DATE '2025-08-25', DATE '2025-09-15', DATE '2025-09-14', 'RETURNED'),

       ((SELECT id FROM users WHERE library_card_number = 'R0009'),
        (SELECT id FROM book WHERE title = 'The Hobbit'),
        DATE '2025-08-28', DATE '2025-09-18', DATE '2025-09-18', 'RETURNED'),

       -- Ещё активные/ожидаемые
       ((SELECT id FROM users WHERE library_card_number = 'R0010'),
        (SELECT id FROM book WHERE title = 'Effective Java'),
        DATE '2025-09-16', DATE '2025-10-07', NULL, 'EXPECTED'),

       ((SELECT id FROM users WHERE library_card_number = 'R0001'),
        (SELECT id FROM book WHERE title = 'Patterns of Enterprise Application Architecture'),
        DATE '2025-09-18', DATE '2025-10-09', NULL, 'EXPECTED'),

       ((SELECT id FROM users WHERE library_card_number = 'R0002'),
        (SELECT id FROM book WHERE title = 'The Pragmatic Programmer'),
        DATE '2025-09-19', DATE '2025-10-10', NULL, 'EXPECTED');



