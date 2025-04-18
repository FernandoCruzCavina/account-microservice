CREATE TABLE account(
    id int PRIMARY KEY ,
    balance DECIMAL(10,2) NOT NULL,
    date_opened BIGINT NOT NULL,
    account_type VARCHAR(10) NOT NULL
);