drop table if exists users;

create table users (
user_id text,
login text,
first_name text,
last_name text
);

insert into users values ('example-user-id-2', 'user-2', 'Testuser', 'Testov');
