CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     student_id VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(120) NOT NULL,
    password_hash VARCHAR(128) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'STUDENT'
    );

CREATE TABLE IF NOT EXISTS books (
                                     id SERIAL PRIMARY KEY,
                                     title VARCHAR(200) NOT NULL,
    author VARCHAR(160) NOT NULL,
    type VARCHAR(80) NOT NULL,
    published_date DATE NOT NULL,
    available BOOLEAN NOT NULL DEFAULT true
    );

CREATE TABLE IF NOT EXISTS issued_books (
                                            id SERIAL PRIMARY KEY,
                                            book_id INTEGER NOT NULL REFERENCES books(id) ON DELETE CASCADE,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    issue_date DATE NOT NULL,
    due_date DATE NOT NULL,
    returned BOOLEAN NOT NULL DEFAULT false
    );

CREATE TABLE IF NOT EXISTS favorites (
                                         user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    book_id INTEGER REFERENCES books(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, book_id)
    );

-- A1001 / 1776  (sha256)
-- S1001 / student123 (sha256)
INSERT INTO users (student_id, name, password_hash, role) VALUES
                                                              ('A1001', 'Admin User',   '6d933e679b8770bdf27b7253af907faea4edd8d5603175a6a8fa87196bd26c75', 'ADMIN'),
                                                              ('S1001', 'Student Jane', '4dd5045bd3a1c74cf0a7e2c6a1b0f06f8d0f8f0b2c9b46b2b4c7f62dbb29b6a0', 'STUDENT')
    ON CONFLICT (student_id) DO UPDATE
                                    SET name = EXCLUDED.name,
                                    password_hash = EXCLUDED.password_hash,
                                    role = EXCLUDED.role;

INSERT INTO books (title, author, type, published_date, available) VALUES
                                                                       ('Clean Code', 'Robert C. Martin', 'Programming', '2008-08-01', true),
                                                                       ('Effective Java', 'Joshua Bloch', 'Programming', '2018-01-06', true),
                                                                       ('The Pragmatic Programmer', 'Andrew Hunt', 'Programming', '1999-10-20', true),
                                                                       ('Design Patterns', 'Erich Gamma', 'Programming', '1994-10-21', true),
                                                                       ('Thinking in Java', 'Bruce Eckel', 'Programming', '2006-02-20', true)
    ON CONFLICT DO NOTHING;