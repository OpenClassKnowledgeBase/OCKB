# --- First database schema

# --- !Ups

create table user(
  id bigint not null,
  email longtext not null,
  name longtext not null,
  password longtext not null,
  usertype longtext not null,
  posts bigint not null,
  PRIMARY KEY (id)
  );

create sequence user_seq;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists user_seq;