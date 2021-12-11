INSERT INTO task VALUES (1, 'Банановый рай', '', current_timestamp, '', true, 4);
INSERT INTO task VALUES (2, 'Monkey Business', '', current_timestamp, '', true, 4);
INSERT INTO task VALUES (3, 'Павшая обезьяна', '', current_timestamp, '', true, 4);
INSERT INTO task VALUES (4, 'Спираль', '', current_timestamp, '', true, 4);
SELECT setval('task_id_seq', (select max(id) from task));