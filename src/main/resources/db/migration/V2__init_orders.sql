CREATE TABLE orders (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL REFERENCES users(id),
  type TEXT NOT NULL CHECK (type IN ('buy', 'sell')),
  amount NUMERIC(18, 8) NOT NULL,
  price NUMERIC(18, 8) NOT NULL,
  status TEXT NOT NULL CHECK (status IN ('pending', 'filled', 'cancelled')) DEFAULT 'pending',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_orders_type ON orders(type);
CREATE INDEX idx_orders_status ON orders(status);