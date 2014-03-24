# --- First database schema

# --- !Ups

create table users(
  email varchar(255) not null,
  name varchar(255),
  password varchar(255) not null,
  userType varchar(255) not null,
  posts int not null,
  );



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists users;

SET REFERENTIAL_INTEGRITY TRUE;

