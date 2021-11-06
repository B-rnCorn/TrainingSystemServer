INSERT INTO users VALUES (1, 'azamatov.ildar', '$2a$12$5.mKUaZoA3lHac3zgYLO4erSsVj9Sd5LSWuX6u0FtlNC2A.ShNM7u', 'Азаматов Ильдар Русланович');
INSERT INTO users VALUES (2, 'sulimeko.s', '$2a$12$zhXwXXOhBD1u4TrtoUwLFerNoyehbP9xEMe4yAUgMLcVZdfzzokyi', 'Сулименко Сергей Андреевич');
INSERT INTO users VALUES (3, '4abrec', '$2a$12$Dh12oRHQT7h/SNSi.99EXuL3hpOWcaCl0pr0fgH5eoygyan72fYmy', 'Черкасов Артем Дмитриевич');
INSERT INTO users VALUES (4, 'zelenko.lar', '$2a$12$NYDEc1Sg9XMm8EfoQQB8JOUax33mKMzAsaLkms6zn0FE98BvsSkTe', 'Зеленко Лариса Сергеевна');
INSERT INTO users VALUES (5, 'danilenko.a', '$2a$12$OSR7ZqTv24JhPwNuggMxIuALUfmMW9yYuSWyHiG52ezRMCvBSFsOq', 'Даниленко Александра Николаевна');

SELECT setval('users_id_seq', (select max(id) from users));

INSERT INTO user_role VALUES (1, 2);
INSERT INTO user_role VALUES (2, 2);
INSERT INTO user_role VALUES (3, 2);
INSERT INTO user_role VALUES (4, 3);
INSERT INTO user_role VALUES (5, 3);