drop table if exists person;
-- 创建person表
create table person
(
    id         bigserial primary key,
    first_name varchar not null,
    last_name  varchar not null,

    unique (first_name, last_name)
);