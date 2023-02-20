INSERT INTO "Clients" ("name", "address", "email", "phone")
VALUES
    ('Arhipova Svetlana Dmitrievna', 'Boulevard Vesnin Brothers, 2G, Moscow', 'arhipova@mail.ru', '79395247366'),
    ('Lytkina Svetlana Vladislavovna', 'Leningradsky prospect, 36с39, Moscow', 's.l@yandex.ru', '79941605820'),

    ('Agafonova Marija Alekseevna', 'Pryluky street, 21-23A, Saint Petersburg', 'agafonova_marija@mail.ru', '79366726517'),
    ('Fedoseev Trifon Ermolaevich', 'Izmailovsky Boulevard, 4k2, Saint Petersburg', 't-f@yandex.ru', '79027493396'),

    ('Alekseev Agap Egorovich', 'Belinsky street, 202, Novosibirsk', 'a.a@yandex.ru', '79286058395'),
    ('Seliverst Jakovlevich Potapov', 'Krasina street, 28, Nizhny Novgorod', 'j_seliverst@mail.ru', '79501672581'),
    ('Viktor Gertrudovich Larionov', 'Stepan Razin street, 25, Yekaterinburg, Sverdlovsk region', 'gv@mail.ru', '79752686136'),
    ('Frolova Fekla Stanislavovna', 'Strelochnikov street, 6, Yekaterinburg, Sverdlovsk region', 'feklaf@gmail.com', '79802544417');


INSERT INTO "Branches" ("name", "address", "city")
VALUES
    ('ЛУКОВ ПЕР.', 'Lukov per., 2/16', 'Moscow'),
    ('БОЛЬШАЯ ОРДЫНКА', 'st. Velyka Ordynka, 49', 'Moscow'),

    ('ПРОСП. СТАЧЕК', 'Ave. Stachek, 47E', 'Saint Petersburg'),
    ('ПРОСП. СЛАВЫ', 'Ave. Glory, 4', 'Saint Petersburg'),

    ('БОЛЬШЕВИСТСКАЯ УЛ.', 'Bolshevik street, 151', 'Novosibirsk'),
    ('ПРОСП. ЛЕНИНА', 'Ave. Lenina, 45', 'Nizhniy Novgorod'),
    ('УЛ. ХОХРЯКОВА', 'st. Khokhryakova, 74', 'Yekaterinburg');

INSERT INTO "Account_types" ("name", "credit_limit", "allow_refill", "allow_write-off")
VALUES
    ('Вклад-1', 0, false, false),
    ('Вклад-максимум', 0, true, true),
    ('Кредит-0', -10000, true, true),
    ('Кредит-1', -20000, true, true),
    ('Кредит-выгодный', -30000, true, true);

INSERT INTO "Accounts" (branch_id, client_id, type_id, balance, open_date, close_date)
VALUES
    (1, 1, 1, 10324, '2022-10-17 15:34:00', null),
    (2, 2, 2, 42384, '2022-12-05 13:52:00', null),
    (3, 3, 3, -5943, '2022-11-25 14:42:00', null),
    (4, 4, 4, -13255, '2022-09-05 09:39:00', null),
    (5, 5, 5, -26936, '2022-10-08 10:48:00', null),
    (6, 6, 1, 38284, '2022-12-17 07:12:00', null),
    (7, 7, 2, 48837, '2022-11-21 19:31:00', null),
    (7, 8, 3, 0, '2022-11-02 21:27:00', '2023-02-15 20:35:00');


INSERT INTO "History" (account_id, sum, date)
VALUES
    (1, 10324, '2022-10-17 15:34:00'),
    (2, 42384, '2022-12-05 13:52:00'),
    (3, -5943, '2022-11-26 09:34:00'),
    (4, -13255, '2022-09-15 10:49:00'),
    (5, -26936, '2022-11-01 11:12:00'),
    (6, 38284, '2022-12-17 07:12:00'),
    (7, 48837, '2022-11-21 19:31:00'),
    (8, 48837, '2022-11-21 19:31:00');