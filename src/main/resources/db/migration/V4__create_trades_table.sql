CREATE TABLE trades (
                        id BIGSERIAL PRIMARY KEY,
                        buy_order_id BIGINT NOT NULL,
                        sell_order_id BIGINT NOT NULL,
                        quantity INT NOT NULL,
                        price INT NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                        CONSTRAINT fk_buy_order FOREIGN KEY (buy_order_id) REFERENCES orders(id),
                        CONSTRAINT fk_sell_order FOREIGN KEY (sell_order_id) REFERENCES orders(id)
);
