-- ===================== DESIGNATION =====================
INSERT INTO designation (id, name, deleted, created_at, updated_at)
VALUES (10001, 'Assistant Software Engineer', false, now(), now());
INSERT INTO designation (id, name, deleted, created_at, updated_at)
VALUES (10002, 'Associate Software Engineer', false, now(), now());
INSERT INTO designation (id, name, deleted, created_at, updated_at)
VALUES (10003, 'Software Engineer', false, now(), now());
INSERT INTO designation (id, name, deleted, created_at, updated_at)
VALUES (10004, 'Senior Software Engineer', false, now(), now());
INSERT INTO designation (id, name, deleted, created_at, updated_at)
VALUES (10005, 'Team Lead', false, now(), now());
INSERT INTO designation (id, name, deleted, created_at, updated_at)
VALUES (10006, 'Project Manager', false, now(), now());
INSERT INTO designation (id, name, deleted, created_at, updated_at)
VALUES (10007, 'CTO', false, now(), now());


-- ===================== ACCOUNT | EXAMINER | CANDIDATE | ROLE =====================
INSERT INTO account (id, username, password, email, first_name, last_name, deleted, disabled, created_at, updated_at)
VALUES (10000,
        'admin1',
        '$2a$10$HP/BF7aseUwz4NPU49rGHOAL8NK5CpEl10N1uzI9guMCe5qROh8jO',
        'admin1@gmail.com',
        'Mr.', 'Admin 1', false, false, now(), now());

INSERT INTO account (id, username, password, email, first_name, last_name, deleted, disabled, created_at, updated_at)
VALUES (10001,
        'examiner1',
        '$2a$10$HP/BF7aseUwz4NPU49rGHOAL8NK5CpEl10N1uzI9guMCe5qROh8jO',
        'examiner1@gmail.com',
        'Mr.', 'Examiner 1', false, false, now(), now());

INSERT INTO account (id, username, password, email, first_name, last_name, deleted, disabled, created_at, updated_at)
VALUES (10002,
        'examiner2',
        '$2a$10$HP/BF7aseUwz4NPU49rGHOAL8NK5CpEl10N1uzI9guMCe5qROh8jO',
        'examiner2@gmail.com',
        'Mr.', 'Examiner 2', false, false, now(), now());

INSERT INTO account (id, username, password, email, first_name, last_name, deleted, disabled, created_at, updated_at)
VALUES (10003,
        'examiner3',
        '$2a$10$HP/BF7aseUwz4NPU49rGHOAL8NK5CpEl10N1uzI9guMCe5qROh8jO',
        'examiner3@gmail.com',
        'Mr.', 'Examiner 3', false, false, now(), now());

INSERT INTO account (id, username, password, email, first_name, last_name, deleted, disabled, created_at, updated_at)
VALUES (10004,
        'examiner4',
        '$2a$10$HP/BF7aseUwz4NPU49rGHOAL8NK5CpEl10N1uzI9guMCe5qROh8jO',
        'examiner4@gmail.com',
        'Mr.', 'Examiner 4', false, false, now(), now());

INSERT INTO account (id, username, password, email, first_name, last_name, deleted, disabled, created_at, updated_at)
VALUES (10005,
        'candidate1',
        '$2a$10$HP/BF7aseUwz4NPU49rGHOAL8NK5CpEl10N1uzI9guMCe5qROh8jO',
        'candidate1@gmail.com',
        'Mr.', 'Candidate 1', false, false, now(), now());

INSERT INTO account (id, username, password, email, first_name, last_name, deleted, disabled, created_at, updated_at)
VALUES (10006,
        'candidate2',
        '$2a$10$HP/BF7aseUwz4NPU49rGHOAL8NK5CpEl10N1uzI9guMCe5qROh8jO',
        'candidate2@gmail.com',
        'Mr.', 'Candidate 2', false, false, now(), now());

INSERT INTO account (id, username, password, email, first_name, last_name, deleted, disabled, created_at, updated_at)
VALUES (10007,
        'candidate3',
        '$2a$10$HP/BF7aseUwz4NPU49rGHOAL8NK5CpEl10N1uzI9guMCe5qROh8jO',
        'candidate3@gmail.com',
        'Mr.', 'Candidate 3', false, false, now(), now());

INSERT INTO account (id, username, password, email, first_name, last_name, deleted, disabled, created_at, updated_at)
VALUES (10008,
        'candidate4',
        '$2a$10$HP/BF7aseUwz4NPU49rGHOAL8NK5CpEl10N1uzI9guMCe5qROh8jO',
        'candidate4@gmail.com',
        'Mr.', 'Candidate 4', false, false, now(), now());

INSERT INTO account_roles (account_id, roles) VALUES (10000, 'ADMIN');
INSERT INTO account_roles (account_id, roles) VALUES (10001, 'EXAMINER');
INSERT INTO account_roles (account_id, roles) VALUES (10002, 'EXAMINER');
INSERT INTO account_roles (account_id, roles) VALUES (10003, 'EXAMINER');
INSERT INTO account_roles (account_id, roles) VALUES (10004, 'EXAMINER');
INSERT INTO account_roles (account_id, roles) VALUES (10005, 'CANDIDATE');
INSERT INTO account_roles (account_id, roles) VALUES (10006, 'CANDIDATE');
INSERT INTO account_roles (account_id, roles) VALUES (10007, 'CANDIDATE');
INSERT INTO account_roles (account_id, roles) VALUES (10008, 'CANDIDATE');

INSERT INTO examiner (id, account_id, designation_id, deleted, created_at, updated_at)
VALUES (10001, 10001, 10005, false, now(), now());

INSERT INTO examiner (id, account_id, designation_id, deleted, created_at, updated_at)
VALUES (10002, 10002, 10005, false, now(), now());

INSERT INTO examiner (id, account_id, designation_id, deleted, created_at, updated_at)
VALUES (10003, 10003, 10005, false, now(), now());

INSERT INTO examiner (id, account_id, designation_id, deleted, created_at, updated_at)
VALUES (10004, 10004, 10005, false, now(), now());

INSERT INTO candidate (id, account_id, mobile_number, deleted, created_at, updated_at)
VALUES (10000, 10005, '+8801617308431', false, now(), now());

INSERT INTO candidate (id, account_id, mobile_number, deleted, created_at, updated_at)
VALUES (10001, 10006, '+8801617308432', false, now(), now());

INSERT INTO candidate (id, account_id, mobile_number, deleted, created_at, updated_at)
VALUES (10002, 10007, '+8801617308433', false, now(), now());

INSERT INTO candidate (id, account_id, mobile_number, deleted, created_at, updated_at)
VALUES (10003, 10008, '+8801617308434', false, now(), now());


-- ===================== QUESTION | CATEGORY =====================
INSERT INTO question_category (id, title, deleted, disabled, created_at, updated_at)
VALUES (10000, 'Database', false, false, now(), now());

INSERT INTO question_category (id, title, deleted, disabled, created_at, updated_at)
VALUES (10001, 'Problem Solving', false, false, now(), now());

INSERT INTO question_category (id, title, deleted, disabled, created_at, updated_at)
VALUES (10002, 'OOP', false, false, now(), now());

INSERT INTO question_category (id, title, deleted, disabled, created_at, updated_at)
VALUES (10003, 'C/C++ Programming Language', false, false, now(), now());

INSERT INTO question_category (id, title, deleted, disabled, created_at, updated_at)
VALUES (10004, 'Java Technical Question', false, false, now(), now());

INSERT INTO question (id, category_id, editor_id, title, deleted, disabled, created_at, updated_at)
VALUES (10000, 10000, 10001, 'What is database?', false, false, now(), now());

INSERT INTO question (id, category_id, editor_id, title, deleted, disabled, created_at, updated_at)
VALUES (10001, 10000, 10001, 'SQL vs NoSQL?', false, false, now(), now());

INSERT INTO question (id, category_id, editor_id, title, deleted, disabled, created_at, updated_at)
VALUES (10002, 10002, 10002, 'Explain inheritance', false, false, now(), now());

INSERT INTO question (id, category_id, editor_id, title, deleted, disabled, created_at, updated_at)
VALUES (10003, 10004, 10002, 'Explain JVM', false, false, now(), now());

INSERT INTO exam (deleted, duration, id, state, created_at, start_date, updated_at, title)
VALUES (false, 120, 100000, 0, now(), now(), now(), 'jse ex');

