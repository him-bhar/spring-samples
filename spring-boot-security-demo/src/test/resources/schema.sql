drop table dummy_tbl if exists;

create table dummy_tbl (
  ID INT PRIMARY KEY, 
  NAME VARCHAR(255)
);

insert into dummy_tbl 
select * from csvread ('classpath:/scripts/testdata/dummy_tbl.csv');

drop table person if exists;

create table person (
  ID INT PRIMARY KEY,
  NAME VARCHAR(255),
  AGE INT
);

insert into person 
select * from csvread ('classpath:/scripts/testdata/person.csv');

drop table users if exists;

create table users (
  USERNAME VARCHAR(255) PRIMARY KEY,
  PASSWORD VARCHAR(255)   --Keeping plaintext for now, this is just for demo
);

insert into users 
select * from csvread ('classpath:/scripts/testdata/users.csv');

drop table roles if exists;

create table roles (
  role_name VARCHAR(255) PRIMARY KEY
);

insert into roles 
select * from csvread ('classpath:/scripts/testdata/roles.csv');

drop table user_role_mapping if exists;

create table user_role_mapping (
  username VARCHAR(255),
  role_name VARCHAR(255)
);

insert into user_role_mapping 
select * from csvread ('classpath:/scripts/testdata/user_role_mapping.csv');