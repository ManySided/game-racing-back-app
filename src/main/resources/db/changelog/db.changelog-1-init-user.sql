--liquibase formatted sql

--changeset parshakov-as:20230401-01 failOnError:true
--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:0 select count(*) from information_schema.tables where table_schema='sp' and table_name='user_player'
CREATE TABLE sp.user_player
(
    user_id  uuid         not null,
    login    varchar(255) not null,
    password varchar(255) not null,
    email    varchar(255) not null,
    primary key (user_id)
);

--changeset parshakov-as:20230401-02 failOnError:true
--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:0 select count(*) from information_schema.tables where table_schema='sp' and table_name='user_role'
CREATE TABLE sp.user_role
(
    role_id uuid         not null,
    name    varchar(255) not null,
    primary key (role_id)
);

INSERT INTO sp.user_role (role_id, name)
VALUES ('c305cf09-0afe-4cca-9475-bd9ff77c0ecc', 'usr'),
       ('20029204-9c4f-4dd5-8862-c1821b7418d7', 'adm')
;

--changeset parshakov-as:20230401-03 failOnError:true
--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:0 select count(*) from information_schema.tables where table_schema='sp' and table_name='user_to_role'
CREATE TABLE sp.user_to_role
(
    user_id uuid not null,
    role_id uuid not null
);
