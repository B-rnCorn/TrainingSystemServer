CREATE TABLE users (
                        "id" serial   NOT NULL
                                CONSTRAINT users_pk PRIMARY KEY ,
                        "username" varchar(16)   NOT NULL
                                CONSTRAINT users_username_key UNIQUE ,
                        "password" varchar(100)   NOT NULL,
                        "fio" varchar(64)   NOT NULL

);

CREATE TABLE role (
                        "id" int   NOT NULL
                                CONSTRAINT role_pk PRIMARY KEY,
                        "name" varchar(20)   NOT NULL

);

CREATE TABLE user_role (
                             "user_id" int   NOT NULL
                                    CONSTRAINT user_role_fk0 REFERENCES users,
                             "role_id" int   NOT NULL
                                    CONSTRAINT user_role_fk1 REFERENCES role,
                             CONSTRAINT user_role_pk
                                 PRIMARY KEY(user_id, role_id)
);

CREATE TABLE task (
                        "id" int   NOT NULL
                                    CONSTRAINT task_pk PRIMARY KEY,
                        "title" varchar(64)   NOT NULL,
                        "description" varchar(256)   NOT NULL,
                        "created_date" timestamp   NOT NULL,
                        "map" varchar(256)   NOT NULL,
                        "is_published" boolean   NOT NULL,
                        "user_id" int   NOT NULL
                                    CONSTRAINT task_fk0 REFERENCES users

);

CREATE TABLE solution (
                            "id" int   NOT NULL,
                            "algorithm" text   NOT NULL,
                            "mark" int   NULL,
                            "created_date" timestamp   NOT NULL,
                            "is_send" boolean   NOT NULL,
                            "user_id" int   NOT NULL
                                CONSTRAINT solution_fk0 REFERENCES users,
                            "task_id" int   NOT NULL
                                CONSTRAINT solution_fk1 REFERENCES task
);

