CREATE TABLE IF NOT EXISTS account(
    id int PRIMARY KEY AUTO_INCREMENT,
    balance DECIMAL(10,2) NOT NULL,
    date_opened BIGINT NOT NULL,
    account_type VARCHAR(10) NOT NULL,
    user_id BIGINT NOT NULL
);