CREATE TABLE trades (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  buy_order_id UUID NOT NULL REFERENCES orders(id),
  sell_order_id UUID NOT NULL REFERENCES orders(id),
  price NUMERIC(18, 8) NOT NULL,
  amount NUMERIC(18, 8) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_trades_buy_order ON trades(buy_order_id);
CREATE INDEX idx_trades_sell_order ON trades(sell_order_id);
CREATE INDEX idx_trades_created_at ON trades(created_at);