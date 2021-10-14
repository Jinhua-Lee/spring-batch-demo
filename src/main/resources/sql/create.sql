drop table if exists person;
-- 创建person表
create table person
(
    id         bigserial primary key,
    first_name varchar not null,
    last_name  varchar not null,

    unique (first_name, last_name)
);

drop table if exists lower_case_person;
-- 创建小写person表
create table lower_case_person
(
    id         bigserial primary key,
    first_name varchar not null,
    last_name  varchar not null,

    unique (first_name, last_name)
);