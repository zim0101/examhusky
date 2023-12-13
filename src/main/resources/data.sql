INSERT INTO account (id, username, password, email, first_name, last_name, created_at, updated_at)
VALUES (10000,
        'admin1',
        '$2a$10$HP/BF7aseUwz4NPU49rGHOAL8NK5CpEl10N1uzI9guMCe5qROh8jO',
        'admin1@gmail.com',
        'Mr.', 'Admin 1', now(), now());

INSERT INTO account (id, username, password, email, first_name, last_name, created_at, updated_at)
VALUES (10001,
        'examiner1',
        '$2a$10$HP/BF7aseUwz4NPU49rGHOAL8NK5CpEl10N1uzI9guMCe5qROh8jO',
        'examiner1@gmail.com',
        'Mr.', 'Examiner 1', now(), now());

INSERT INTO account (id, username, password, email, first_name, last_name, created_at, updated_at)
VALUES (10002,
        'candidate1',
        '$2a$10$HP/BF7aseUwz4NPU49rGHOAL8NK5CpEl10N1uzI9guMCe5qROh8jO',
        'candidate1@gmail.com',
        'Mr.', 'Candidate 1', now(), now());

INSERT INTO account_roles (account_id, roles) VALUES (10000, 'ADMIN');
INSERT INTO account_roles (account_id, roles) VALUES (10001, 'EXAMINER');
INSERT INTO account_roles (account_id, roles) VALUES (10002, 'CANDIDATE');

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

INSERT INTO candidate (id, account_id, mobile_number, created_at, updated_at)
VALUES (10000, 10002, '+8801617308431', now(), now());

INSERT INTO examiner (id, account_id, designation_id, created_at, updated_at)
VALUES (10000, 10001, 10005, now(), now());