MERGE INTO category (name) KEY (name) VALUES ('Программирование'),
                                             ('Фэнтези'),
                                             ('Научная фантастика'),
                                             ('Классика'),
                                             ('История'),
                                             ('Биография'),
                                             ('Философия'),
                                             ('Психология'),
                                             ('Продуктивность'),
                                             ('Детектив'),
                                             ('Русская классика'),
                                             ('Мировая классика'),
                                             ('Поэзия'),
                                             ('Драматургия'),
                                             ('Современная проза'),
                                             ('Фантастика');


UPDATE book
SET title='Чистый код'
WHERE title = 'Clean Code';
UPDATE book
SET title='Прагматичный программист'
WHERE title = 'The Pragmatic Programmer';
UPDATE book
SET title='Эффективная Java'
WHERE title = 'Effective Java';
UPDATE book
SET title='Рефакторинг'
WHERE title = 'Refactoring';
UPDATE book
SET title='Паттерны проектирования'
WHERE title = 'Design Patterns';
UPDATE book
SET title='Чистая архитектура'
WHERE title = 'Clean Architecture';
UPDATE book
SET title='Работа с легаси-кодом'
WHERE title = 'Working Effectively with Legacy Code';
UPDATE book
SET title='Шаблоны корпоративных приложений'
WHERE title = 'Patterns of Enterprise Application Architecture';
UPDATE book
SET title='Предметно-ориентированное проектирование'
WHERE title = 'Domain-Driven Design';
UPDATE book
SET title='Java. Эффективная мультипоточность'
WHERE title = 'Java Concurrency in Practice';

UPDATE book
SET title='Паттерны проектирования Head First'
WHERE title = 'Head First Design Patterns';
UPDATE book
SET title='Вы не знаете JS'
WHERE title = 'You Don''t Know JS';
UPDATE book
SET title='Алгоритмы: построение и анализ'
WHERE title = 'Introduction to Algorithms';

UPDATE book
SET title='Властелин колец'
WHERE title = 'The Lord of the Rings';
UPDATE book
SET title='Хоббит, или Туда и обратно'
WHERE title = 'The Hobbit';
UPDATE book
SET title='Гарри Поттер и философский камень'
WHERE title = 'Harry Potter and the Sorcerer''s Stone';
UPDATE book
SET title='Имя ветра'
WHERE title = 'The Name of the Wind';
UPDATE book
SET title='Мистборн'
WHERE title = 'Mistborn';

UPDATE book
SET title='Дюна'
WHERE title = 'Dune';
UPDATE book
SET title='Основание'
WHERE title = 'Foundation';
UPDATE book
SET title='Нейромант'
WHERE title = 'Neuromancer';
UPDATE book
SET title='Игра Эндера'
WHERE title = 'Ender''s Game';
UPDATE book
SET title='Лавина'
WHERE title = 'Snow Crash';

UPDATE book
SET title='1984'
WHERE title = '1984';
UPDATE book
SET title='О дивный новый мир'
WHERE title = 'Brave New World';
UPDATE book
SET title='Убить пересмешника'
WHERE title = 'To Kill a Mockingbird';
UPDATE book
SET title='Великий Гэтсби'
WHERE title = 'The Great Gatsby';
UPDATE book
SET title='Преступление и наказание'
WHERE title = 'Crime and Punishment';
UPDATE book
SET title='Война и мир'
WHERE title = 'War and Peace';
UPDATE book
SET title='Гордость и предубеждение'
WHERE title = 'Pride and Prejudice';
UPDATE book
SET title='Моби Дик'
WHERE title = 'Moby-Dick';
UPDATE book
SET title='Над пропастью во ржи'
WHERE title = 'The Catcher in the Rye';
UPDATE book
SET title='Маленький принц'
WHERE title = 'The Little Prince';

UPDATE book
SET title='Sapiens. Краткая история человечества'
WHERE title = 'Sapiens';
UPDATE book
SET title='Homo Deus. Краткая история будущего'
WHERE title = 'Homo Deus';
UPDATE book
SET title='Ружья, микробы и сталь'
WHERE title = 'Guns, Germs, and Steel';

UPDATE book
SET title='Стив Джобс'
WHERE title = 'Steve Jobs';
UPDATE book
SET title='Илон Маск'
WHERE title = 'Elon Musk';
UPDATE book
SET title='Образование'
WHERE title = 'Educated';

UPDATE book
SET title='Размышления'
WHERE title = 'Meditations';
UPDATE book
SET title='По ту сторону добра и зла'
WHERE title = 'Beyond Good and Evil';
UPDATE book
SET title='Искусство войны'
WHERE title = 'The Art of War';

UPDATE book
SET title='Думай медленно… решай быстро'
WHERE title = 'Thinking, Fast and Slow';
UPDATE book
SET title='Психология влияния'
WHERE title = 'Influence';

UPDATE book
SET title='Атомные привычки'
WHERE title = 'Atomic Habits';
UPDATE book
SET title='Глубокая работа'
WHERE title = 'Deep Work';

UPDATE book
SET title='Девушка с татуировкой дракона'
WHERE title = 'The Girl with the Dragon Tattoo';
UPDATE book
SET title='Исчезнувшая'
WHERE title = 'Gone Girl';
UPDATE book
SET title='Код да Винчи'
WHERE title = 'The Da Vinci Code';

UPDATE book
SET title='Refactoring UI (рус. издание)'
WHERE title = 'Refactoring UI';

INSERT INTO book(title, author, image_url, category_id, total_copies, available_copies)
VALUES ('Евгений Онегин', 'А. С. Пушкин', 'image51.jpg', (SELECT id FROM category WHERE name = 'Русская классика'), 8,
        3),
       ('Мёртвые души', 'Н. В. Гоголь', 'image52.jpg', (SELECT id FROM category WHERE name = 'Русская классика'), 7, 2),
       ('Герой нашего времени', 'М. Ю. Лермонтов', 'image53.jpg',
        (SELECT id FROM category WHERE name = 'Русская классика'), 6, 2),
       ('Вишнёвый сад', 'А. П. Чехов', 'image54.jpg', (SELECT id FROM category WHERE name = 'Драматургия'), 6, 1),
       ('Мастер и Маргарита', 'М. А. Булгаков', 'image55.jpg',
        (SELECT id FROM category WHERE name = 'Русская классика'), 9, 0),

       ('Отцы и дети', 'И. С. Тургенев', 'image56.jpg', (SELECT id FROM category WHERE name = 'Русская классика'), 7,
        2),
       ('Тихий Дон', 'М. А. Шолохов', 'image57.jpg', (SELECT id FROM category WHERE name = 'Русская классика'), 6, 1),
       ('Доктор Живаго', 'Б. Л. Пастернак', 'image58.jpg', (SELECT id FROM category WHERE name = 'Русская классика'), 6,
        2),
       ('Лолита', 'В. В. Набоков', 'image59.jpg', (SELECT id FROM category WHERE name = 'Мировая классика'), 5, 1),
       ('Реквием', 'А. А. Ахматова', 'image60.jpg', (SELECT id FROM category WHERE name = 'Поэзия'), 5, 2),

       ('Во весь голос', 'В. В. Маяковский', 'image61.jpg', (SELECT id FROM category WHERE name = 'Поэзия'), 5, 3),
       ('Стихотворения и поэмы', 'С. А. Есенин', 'image62.jpg', (SELECT id FROM category WHERE name = 'Поэзия'), 6, 2),
       ('Гамлет', 'У. Шекспир', 'image63.jpg', (SELECT id FROM category WHERE name = 'Драматургия'), 7, 3),
       ('Процесс', 'Ф. Кафка', 'image64.jpg', (SELECT id FROM category WHERE name = 'Мировая классика'), 6, 2),
       ('Посторонний', 'А. Камю', 'image65.jpg', (SELECT id FROM category WHERE name = 'Мировая классика'), 6, 1),

       ('Снег', 'О. Памук', 'image66.jpg', (SELECT id FROM category WHERE name = 'Современная проза'), 5, 2),
       ('Generation «П»', 'В. О. Пелевин', 'image67.jpg', (SELECT id FROM category WHERE name = 'Современная проза'), 6,
        3),
       ('Ночной дозор', 'С. В. Лукьяненко', 'image68.jpg', (SELECT id FROM category WHERE name = 'Фантастика'), 7, 0),
       ('Пикник на обочине', 'А. и Б. Стругацкие', 'image69.jpg', (SELECT id FROM category WHERE name = 'Фантастика'),
        6, 1),
       ('Этюд в багровых тонах', 'А. К. Дойл', 'image70.jpg', (SELECT id FROM category WHERE name = 'Детектив'), 6, 2),

       ('Азазель', 'Б. Акунин', 'image71.jpg', (SELECT id FROM category WHERE name = 'Детектив'), 6, 1),
       ('Сто лет одиночества', 'Г. Г. Маркес', 'image72.jpg', (SELECT id FROM category WHERE name = 'Мировая классика'),
        7, 2),
       ('Анна Каренина', 'Л. Н. Толстой', 'image73.jpg', (SELECT id FROM category WHERE name = 'Русская классика'), 7,
        2),
       ('Идиот', 'Ф. М. Достоевский', 'image74.jpg', (SELECT id FROM category WHERE name = 'Русская классика'), 6, 2),
       ('Белая гвардия', 'М. А. Булгаков', 'image75.jpg', (SELECT id FROM category WHERE name = 'Русская классика'), 5,
        1),

       ('Игрок', 'Ф. М. Достоевский', 'image76.jpg', (SELECT id FROM category WHERE name = 'Русская классика'), 5, 2),
       ('Нос', 'Н. В. Гоголь', 'image77.jpg', (SELECT id FROM category WHERE name = 'Русская классика'), 5, 2),
       ('Чайка', 'А. П. Чехов', 'image78.jpg', (SELECT id FROM category WHERE name = 'Драматургия'), 6, 3),
       ('Превращение', 'Ф. Кафка', 'image79.jpg', (SELECT id FROM category WHERE name = 'Мировая классика'), 5, 2),
       ('Собачье сердце', 'М. А. Булгаков', 'image80.jpg', (SELECT id FROM category WHERE name = 'Русская классика'), 6,
        0);



INSERT INTO loan(user_id, book_id, borrow_date, due_date, returned_at, status)
VALUES ((SELECT id FROM users WHERE library_card_number = 'R0001'),
        (SELECT id FROM book WHERE title = 'Мастер и Маргарита'),
        DATE '2025-09-12', DATE '2025-10-05', NULL, 'EXPECTED'),

       ((SELECT id FROM users WHERE library_card_number = 'R0002'),
        (SELECT id FROM book WHERE title = 'Ночной дозор'),
        DATE '2025-09-15', DATE '2025-10-08', NULL, 'EXPECTED'),

       ((SELECT id FROM users WHERE library_card_number = 'R0003'),
        (SELECT id FROM book WHERE title = 'Собачье сердце'),
        DATE '2025-09-10', DATE '2025-09-30', NULL, 'OVERDUE'),

       ((SELECT id FROM users WHERE library_card_number = 'R0004'),
        (SELECT id FROM book WHERE title = 'Евгений Онегин'),
        DATE '2025-09-05', DATE '2025-09-27', NULL, 'OVERDUE'),

       ((SELECT id FROM users WHERE library_card_number = 'R0005'),
        (SELECT id FROM book WHERE title = 'Гамлет'),
        DATE '2025-09-07', DATE '2025-09-29', NULL, 'EXPECTED'),

       ((SELECT id FROM users WHERE library_card_number = 'R0006'),
        (SELECT id FROM book WHERE title = 'Этюд в багровых тонах'),
        DATE '2025-09-01', DATE '2025-09-24', DATE '2025-09-20', 'RETURNED'),

       ((SELECT id FROM users WHERE library_card_number = 'R0007'),
        (SELECT id FROM book WHERE title = 'Азазель'),
        DATE '2025-09-03', DATE '2025-09-25', NULL, 'EXPECTED'),

       ((SELECT id FROM users WHERE library_card_number = 'R0008'),
        (SELECT id FROM book WHERE title = 'Пикник на обочине'),
        DATE '2025-09-09', DATE '2025-10-01', NULL, 'EXPECTED'),

       ((SELECT id FROM users WHERE library_card_number = 'R0009'),
        (SELECT id FROM book WHERE title = 'Реквием'),
        DATE '2025-09-02', DATE '2025-09-23', DATE '2025-09-22', 'RETURNED'),

       ((SELECT id FROM users WHERE library_card_number = 'R0010'),
        (SELECT id FROM book WHERE title = 'Сто лет одиночества'),
        DATE '2025-09-11', DATE '2025-10-02', NULL, 'EXPECTED'),

       ((SELECT id FROM users WHERE library_card_number = 'R0001'),
        (SELECT id FROM book WHERE title = 'Анна Каренина'),
        DATE '2025-09-16', DATE '2025-10-06', NULL, 'EXPECTED'),

       ((SELECT id FROM users WHERE library_card_number = 'R0002'),
        (SELECT id FROM book WHERE title = 'Процесс'),
        DATE '2025-09-04', DATE '2025-09-26', NULL, 'OVERDUE');
