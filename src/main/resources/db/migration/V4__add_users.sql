INSERT INTO users VALUES (6, 'suchov.anton', '$2a$12$B5SC8KE8rp35Vz5LuwNpnOrQQeA38ckUlbWiBbGeL1dZzKo6MRMYS', 'Сухов Антон Алексеевич');
INSERT INTO users VALUES (7, 'lovtsova.n', '$2a$12$99o9SgnqAe/O0BZcH/BhQOswWuhL82lVfQwLW6pIUSeW5HHJzhag.', 'Ловцова Анастасия Александровна');
INSERT INTO users VALUES (8, 'evgen_gel', '$2a$12$7Q9cBno333Z8O7bRFeSwxe2uWEdWsFl9YKob5nERdkiVHcaBucqFi', 'Шмойлов Евгений Евгениевич');
INSERT INTO users VALUES (9, 'polinain', '$2a$12$Afhx9u1wddZtM.qXbw0QFOjDe4m4kF/iiJYpA2WocvWtqa8vzPq5S', 'Иноземцева Полина Павловна');
INSERT INTO users VALUES (10, 'artem_gilza', '$2a$12$aZPaLPJp69jf49EblRQJSelTE3tdetgxiBz8NHz2BeCoRf/zO4O8a', 'Гильмизакиров Артем Ильдарович');
SELECT setval('users_id_seq', (select max(id) from users));

INSERT INTO user_role VALUES (6, 2);
INSERT INTO user_role VALUES (7, 2);
INSERT INTO user_role VALUES (8, 2);
INSERT INTO user_role VALUES (9, 2);
INSERT INTO user_role VALUES (10, 2);