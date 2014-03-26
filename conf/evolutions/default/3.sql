# --- First database schema

# --- !Ups

create table user(
  email longtext not null,
  name longtext not null,
  password longtext not null,
  usertype longtext not null,
  posts bigint not null,
  constraint pk_user primary key (email)
  );

create sequence user_seq;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists user_seq;