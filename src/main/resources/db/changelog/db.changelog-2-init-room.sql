--liquibase formatted sql

--changeset parshakov-as:20230414-01 failOnError:true
--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:0 select count(*) from information_schema.tables where table_schema='sp' and table_name='room'
CREATE TABLE sp.room
(
    room_id     uuid                     not null,
    name        varchar(255)             not null,
    create_date timestamp with time zone not null,
    primary key (room_id)
);

--changeset parshakov-as:20230414-02 failOnError:true
--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:0 select count(*) from information_schema.tables where table_schema='sp' and table_name='player_room'
CREATE TABLE sp.player_room
(
    room_player_id uuid                     not null,
    room           uuid                     not null,
    user_player    uuid                     not null,
    join_date      timestamp with time zone not null,
    leave_date     timestamp with time zone,
    primary key (room_player_id)
);
