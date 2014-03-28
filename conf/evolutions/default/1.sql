# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                        bigint not null,
  title                     varchar(255),
  description               varchar(255),
  url                       varchar(255),
  constraint pk_category primary key (id))
;

create table comment (
  id                        bigint,
  content                   varchar(255),
  author                    varchar(255),
  submission_date           timestamp)
;

create table post (
  id                        bigint not null,
  user_name                 varchar(255),
  category                  varchar(255),
  title                     varchar(255),
  content                   varchar(255),
  date_posted               timestamp,
  constraint pk_post primary key (id))
;

create table post_submission (
  id                        bigint not null,
  user_name                 varchar(255),
  category                  varchar(255),
  title                     varchar(255),
  content                   varchar(255),
  date_posted               timestamp,
  constraint pk_post_submission primary key (id))
;

create sequence category_seq;

create sequence post_seq;

create sequence post_submission_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists category;

drop table if exists comment;

drop table if exists post;

drop table if exists post_submission;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists category_seq;

drop sequence if exists post_seq;

drop sequence if exists post_submission_seq;

