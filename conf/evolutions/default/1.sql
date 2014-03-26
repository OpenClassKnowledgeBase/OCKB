# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                        varchar(255),
  description               varchar(255))
;

create table comment (
  id                        bigint,
  content                   varchar(255))
;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists category;

drop table if exists comment;

SET REFERENTIAL_INTEGRITY TRUE;

