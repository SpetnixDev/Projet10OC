INSERT INTO users (email, password, first_name, last_name) VALUES
('paul.martin@example.com', '$2a$10$Eb6mpHwKLPFd7TYvKOv42uk6TqS0/kjktVlq2M77SJ4xVhtoWQOtK', 'Paul', 'Martin'),
('marie.durand@example.com', '$2a$10$Eb6mpHwKLPFd7TYvKOv42uk6TqS0/kjktVlq2M77SJ4xVhtoWQOtK', 'Marie', 'Durand'),
('jean.leclerc@example.com', '$2a$10$Eb6mpHwKLPFd7TYvKOv42uk6TqS0/kjktVlq2M77SJ4xVhtoWQOtK', 'Jean', 'Leclerc'),
('sophie.lambert@example.com', '$2a$10$Eb6mpHwKLPFd7TYvKOv42uk6TqS0/kjktVlq2M77SJ4xVhtoWQOtK', 'Sophie', 'Lambert'),
('luc.dupont@example.com', '$2a$10$Eb6mpHwKLPFd7TYvKOv42uk6TqS0/kjktVlq2M77SJ4xVhtoWQOtK', 'Luc', 'Dupont'),
('emma.riviere@example.com', '$2a$10$Eb6mpHwKLPFd7TYvKOv42uk6TqS0/kjktVlq2M77SJ4xVhtoWQOtK', 'Emma', 'Rivière'),
('nicolas.roche@example.com', '$2a$10$Eb6mpHwKLPFd7TYvKOv42uk6TqS0/kjktVlq2M77SJ4xVhtoWQOtK', 'Nicolas', 'Roche'),
('claire.perrin@example.com', '$2a$10$Eb6mpHwKLPFd7TYvKOv42uk6TqS0/kjktVlq2M77SJ4xVhtoWQOtK', 'Claire', 'Perrin');

INSERT INTO books (title, author, release_date, total_copies, available_copies) VALUES
('Le Petit Prince', 'Antoine de Saint-Exupéry', '1943-04-06', 8, 8),
('Les Misérables', 'Victor Hugo', '1862-01-01', 5, 5),
('L''Étranger', 'Albert Camus', '1942-05-01', 6, 5),
('Madame Bovary', 'Gustave Flaubert', '1857-12-01', 4, 3),
('Cyrano de Bergerac', 'Edmond Rostand', '1897-12-28', 7, 6),
('Notre-Dame de Paris', 'Victor Hugo', '1831-03-16', 5, 4),
('Candide', 'Voltaire', '1759-01-01', 3, 2),
('Les Fleurs du mal', 'Charles Baudelaire', '1857-06-25', 4, 3);

INSERT INTO loans (book_id, user_id, loan_date, return_date, returned, extended) VALUES
(1, 5, '2025-01-01', '2025-01-29', TRUE, FALSE),
(2, 6, '2025-01-03', '2025-01-31', TRUE, FALSE),
(3, 7, '2025-01-10', '2025-02-07', FALSE, FALSE),
(4, 8, '2025-01-12', '2025-02-09', FALSE, FALSE),
(5, 1, '2025-01-15', '2025-02-12', FALSE, FALSE),
(6, 2, '2025-01-20', '2025-02-17', FALSE, FALSE),
(7, 3, '2025-01-22', '2025-03-19', FALSE, TRUE),
(8, 4, '2025-01-25', '2025-02-22', FALSE, FALSE);