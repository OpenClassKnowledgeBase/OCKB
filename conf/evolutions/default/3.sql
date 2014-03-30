# --- First database schema

# --- !Ups

create table user(
  id bigint not null PRIMARY KEY,
  email varchar(255) not null UNIQUE,
  name varchar(255) not null,
  type varchar(255) not null,
  posts bigint not null
  );

create sequence user_seq;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists user_seq;