# --- First database schema

# --- !Ups

create table user(
  id int not null AUTO_INCREMENT,
  email varchar(255) not null UNIQUE,
  name varchar(255) not null,
  status varchar(255) not null,
  posts bigint not null,
  PRIMARY KEY (id)
  );

create sequence user_seq;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists user_seq;