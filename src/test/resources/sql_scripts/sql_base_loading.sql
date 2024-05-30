-- Загрузим аккаунты
INSERT INTO accounts (account_id, login, pass)
VALUES (1,'Gena12', 111111),
       (2,'Cheburator', 222222),
       (3,'Coolbabul', 333333),
       (4,'rokyman', 444444),
       (5,'Hizenberg', 555555),
       (6,'trapunka', 666666),
       (7,'tamtudoid', 777777),
       (8,'lovkach', 888888),
       (9,'shnepsel', 999999);

-- Загрузим данные клиентов
INSERT INTO users (account_id, lastname, firstname, patronymic, birth_date)
VALUES ((SELECT account_id FROM accounts WHERE account_id = 1),'Зеленов','Геннадий','Крокодилович','1969-12-31') ,
       ((SELECT account_id FROM accounts WHERE account_id = 2),'Ушидзе','Чебурэн','Апельсинович','1969-12-31'),
       ((SELECT account_id FROM accounts WHERE account_id = 3),'Шапокляк','Наина','Эдуардовна','1969-12-31'),
       ((SELECT account_id FROM accounts WHERE account_id = 4),'Сталонив','Сильвестр','Френкивич','1946-07-06'),
       ((SELECT account_id FROM accounts WHERE account_id = 5),'Амфитоминов','Самовар','Химикович','1975-06-11'),
       ((SELECT account_id FROM accounts WHERE account_id = 6),'Тимошенко','Юрий','Тимофеевич','1919-06-02'),
       ((SELECT account_id FROM accounts WHERE account_id = 7),'Ежегодин','Дневник','Строкович','1989-02-13'),
       ((SELECT account_id FROM accounts WHERE account_id = 8),'Люпен','Арсен','Теофрастович','1889-04-01'),
       ((SELECT account_id FROM accounts WHERE account_id = 9),'Березин','Ефим','Иосифович','1919-11-11');

-- Подогреем клиентов деньгами
INSERT INTO amounts (account_id, amount, start_limit, stop_limit, count_period, interest)
VALUES ((SELECT account_id FROM accounts WHERE account_id = 1), 1234.05, 1234.05, 2554.48, 0, 0),
       ((SELECT account_id FROM accounts WHERE account_id = 2), 245.45, 245.45, 508.08, 0, 0),
       ((SELECT account_id FROM accounts WHERE account_id = 3), 1400.35, 1400.35, 2898.72, 0, 0),
       ((SELECT account_id FROM accounts WHERE account_id = 4), 2245.45, 2245.45, 4648.08, 0, 0),
       ((SELECT account_id FROM accounts WHERE account_id = 5), 6400.35, 6400.35, 13248.72, 0, 0),
       ((SELECT account_id FROM accounts WHERE account_id = 6), 2345.45, 2345.45, 4855.08, 0, 0),
       ((SELECT account_id FROM accounts WHERE account_id = 7), 24000.35, 24000.35, 49680.72, 0, 0),
       ((SELECT account_id FROM accounts WHERE account_id = 8), 2645.45, 2645.45, 5476.08, 0, 0),
       ((SELECT account_id FROM accounts WHERE account_id = 9), 14500.35, 14500.35, 30015.72, 0, 0);


INSERT INTO emails (user_id, user_email)
VALUES ((SELECT id FROM users WHERE account_id = 1), 'kroki@yandex.ru'),
       ((SELECT id FROM users WHERE account_id = 2), 'teg@google.com'),
       ((SELECT id FROM users WHERE account_id = 3), 'klikastiy@rambler.ru'),
       ((SELECT id FROM users WHERE account_id = 4), 'ushivstoronu@bk.ru'),
       ((SELECT id FROM users WHERE account_id = 5), 'smartbroker@yahoo.com'),
       ((SELECT id FROM users WHERE account_id = 6), 'endtoun@list.ru'),
       ((SELECT id FROM users WHERE account_id = 7), 'karector@bk.ru'),
       ((SELECT id FROM users WHERE account_id = 8), 'dublekatuk@yahoo.com'),
       ((SELECT id FROM users WHERE account_id = 9), 'katakomber@yandex.ru'),
       ((SELECT id FROM users WHERE account_id = 6), 'kulturmultur@google.com'),
       ((SELECT id FROM users WHERE account_id = 7), 'balkash12@yahoo.com'),
       ((SELECT id FROM users WHERE account_id = 2), 'kvestus32@galadec.com'),
       ((SELECT id FROM users WHERE account_id = 1), 'bashtak341@bk.org'),
       ((SELECT id FROM users WHERE account_id = 8), 'lackyboy@yahoo.com'),
       ((SELECT id FROM users WHERE account_id = 3), 'rotland132@google.com'),
       ((SELECT id FROM users WHERE account_id = 4), 'bezenkil554@yahoo.com'),
       ((SELECT id FROM users WHERE account_id = 5), 'tukano1@galadec.com'),
       ((SELECT id FROM users WHERE account_id = 2), 'moguci341@bk.org'),
       ((SELECT id FROM users WHERE account_id = 9), 'layak55tu@yahoo.com');

INSERT INTO phones (user_id, user_phone)
VALUES ((SELECT id FROM users WHERE account_id = 1), '555435234'),
       ((SELECT id FROM users WHERE account_id = 2), '8495234234'),
       ((SELECT id FROM users WHERE account_id = 3), '88002546543'),
       ((SELECT id FROM users WHERE account_id = 4), '972504179834'),
       ((SELECT id FROM users WHERE account_id = 5), '83324356735'),
       ((SELECT id FROM users WHERE account_id = 6), '89994374820'),
       ((SELECT id FROM users WHERE account_id = 7), '8555546734'),
       ((SELECT id FROM users WHERE account_id = 8), '89271547635'),
       ((SELECT id FROM users WHERE account_id = 9), '89342333545'),
       ((SELECT id FROM users WHERE account_id = 1), '89944348923'),
       ((SELECT id FROM users WHERE account_id = 2), '89435234627'),
       ((SELECT id FROM users WHERE account_id = 3), '83324959382'),
       ((SELECT id FROM users WHERE account_id = 4), '89223223748'),
       ((SELECT id FROM users WHERE account_id = 5), '85467256634'),
       ((SELECT id FROM users WHERE account_id = 6), '88547887346'),
       ((SELECT id FROM users WHERE account_id = 7), '89966788954'),
       ((SELECT id FROM users WHERE account_id = 8), '73949582348'),
       ((SELECT id FROM users WHERE account_id = 9), '95934568918'),
       ((SELECT id FROM users WHERE account_id = 1), '39593893823');