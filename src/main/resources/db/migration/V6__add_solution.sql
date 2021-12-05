INSERT INTO solution VALUES (1, '', 5, current_timestamp, true, 1, 1);
INSERT INTO solution VALUES (2, '', 4, current_timestamp, true, 2, 1);
INSERT INTO solution VALUES (3, '', 3, current_timestamp, true, 3, 1);
INSERT INTO solution VALUES (4, '', 5, current_timestamp, true, 6, 1);
INSERT INTO solution VALUES (5, '', 4, current_timestamp, true, 7, 1);
INSERT INTO solution VALUES (6, '', 3, current_timestamp, true, 8, 1);
INSERT INTO solution VALUES (7, '', 5, current_timestamp, true, 9, 1);
INSERT INTO solution VALUES (8, '', 4, current_timestamp, true, 10, 1);
INSERT INTO solution VALUES (9, '', 2, current_timestamp, true, 11, 1);

INSERT INTO solution VALUES (10, '', 3, current_timestamp, true, 1, 2);
INSERT INTO solution VALUES (11, '', 5, current_timestamp, true, 2, 2);
INSERT INTO solution VALUES (12, '', 4, current_timestamp, true, 3, 2);
INSERT INTO solution VALUES (13, '', 3, current_timestamp, true, 6, 2);
INSERT INTO solution VALUES (14, '', 2, current_timestamp, true, 7, 2);
INSERT INTO solution VALUES (15, '', 5, current_timestamp, true, 8, 2);
INSERT INTO solution VALUES (16, '', 3, current_timestamp, true, 9, 2);
INSERT INTO solution VALUES (17, '', 0, current_timestamp, true, 10, 2);
INSERT INTO solution VALUES (18, '', 3, current_timestamp, true, 11, 2);

INSERT INTO solution VALUES (19, '', 3, current_timestamp, true, 1, 3);
INSERT INTO solution VALUES (20, '', 1, current_timestamp, true, 2, 3);
INSERT INTO solution VALUES (21, '', 4, current_timestamp, true, 3, 3);
INSERT INTO solution VALUES (22, '', 2, current_timestamp, true, 6, 3);
INSERT INTO solution VALUES (23, '', 4, current_timestamp, true, 7, 3);
INSERT INTO solution VALUES (24, '', 3, current_timestamp, true, 8, 3);
INSERT INTO solution VALUES (25, '', 5, current_timestamp, true, 9, 3);
INSERT INTO solution VALUES (26, '', 5, current_timestamp, true, 10, 3);
INSERT INTO solution VALUES (27, '', 5, current_timestamp, true, 11, 3);

INSERT INTO solution VALUES (28, '', 5, current_timestamp, true, 1, 4);
INSERT INTO solution VALUES (29, '', 5, current_timestamp, true, 2, 4);
INSERT INTO solution VALUES (30, '', 1, current_timestamp, true, 3, 4);
INSERT INTO solution VALUES (31, '', 2, current_timestamp, true, 6, 4);
INSERT INTO solution VALUES (32, '', 0, current_timestamp, true, 7, 4);
INSERT INTO solution VALUES (33, '', 3, current_timestamp, true, 8, 4);
INSERT INTO solution VALUES (34, '', 1, current_timestamp, true, 9, 4);
INSERT INTO solution VALUES (35, '', 3, current_timestamp, true, 10, 4);
INSERT INTO solution VALUES (36, '', 3, current_timestamp, true, 11, 4);

SELECT setval('solution_id_seq', (select max(id) from solution));