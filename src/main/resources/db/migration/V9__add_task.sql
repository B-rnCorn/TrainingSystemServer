-- INSERT INTO task VALUES (5, 'Умная обезьяна', 'В данном задании необходимо собрать все бананы менее чем за 10 комнад', current_timestamp, '', false, 4);
-- INSERT INTO task VALUES (6, 'Берегись лиан!', 'Необходимо собрать все бананы и совершить не менее 5 прыжков, при условии, что общее количество команд не должно превышать 15', current_timestamp, '', false, 4);
-- SELECT setval('task_id_seq', (select max(id) from task));