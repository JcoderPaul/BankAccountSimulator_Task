CREATE TABLE IF NOT EXISTS accounts
(
    account_id BIGSERIAL PRIMARY KEY ,
    -- Логин не должен быть занят (т.е. уникальный)
    login VARCHAR(64) NOT NULL UNIQUE,
    -- А вот пароли могут быть одинаковыми
    pass VARCHAR(128) NOT NULL DEFAULT '{noop}123'
);

CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY ,
    account_id BIGSERIAL REFERENCES accounts (account_id) ,
    -- ФИО и ДР могут быть не уникальными
    lastname VARCHAR(64) NOT NULL,
    firstname VARCHAR(64) NOT NULL,
    patronymic VARCHAR(64) NOT NULL,
    birth_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS amounts
(
    id BIGSERIAL PRIMARY KEY ,
    account_id BIGSERIAL REFERENCES accounts (account_id) ,
    -- Счет клиента не может быть отрицательным
    amount NUMERIC CONSTRAINT amount_balance CHECK (amount >= 0),
    start_limit NUMERIC CONSTRAINT start_balance CHECK (start_limit >= 0),
    stop_limit NUMERIC CONSTRAINT stop_balance CHECK (stop_limit >= 0),
    count_period NUMERIC CONSTRAINT count_balance CHECK (count_period >= 0),
    interest NUMERIC CONSTRAINT interest_balance CHECK (interest >= 0)
);

CREATE TABLE IF NOT EXISTS emails
(
    id SERIAL PRIMARY KEY ,
    user_id BIGSERIAL REFERENCES users (id) ,
    user_email varchar(64) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS phones
(
    id SERIAL PRIMARY KEY ,
    user_id BIGSERIAL REFERENCES users (id) ,
    user_phone varchar(64) UNIQUE NOT NULL
);