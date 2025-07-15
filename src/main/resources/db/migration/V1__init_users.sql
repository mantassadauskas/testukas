CREATE TABLE users (
  id UUID PRIMARY KEY,
  email TEXT UNIQUE NOT NULL,
  password_hash TEXT NOT NULL
);

INSERT INTO users (id, email, password_hash) VALUES
  ('00000000-0000-0000-0000-000000000001', 'first@example.com', 'hashed_pw_1'),
  ('00000000-0000-0000-0000-000000000002', 'second@example.com', 'hashed_pw_2');
