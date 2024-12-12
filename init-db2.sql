drop table if exists users;

create table users (
user_id text,
login text,
first_name text,
last_name text
);

insert into users values ('2', 'user-2', 'Jane', 'Doe');
