CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        item VARCHAR(255),
                        quantity INTEGER,
                        created_at TIMESTAMP,
                        user_id INTEGER REFERENCES users(id),
                        price INTEGER,
                        type VARCHAR(20),
                        transaction INTEGER
);
