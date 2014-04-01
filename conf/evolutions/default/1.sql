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
  is_sticky                 boolean,
  category_id               bigint,
  title                     varchar(255),
  content                   TEXT,
  date_posted               timestamp,
  constraint pk_post primary key (id))
;

create table user (
  id                        bigint not null,
  email                     varchar(255),
  name                      varchar(255),
  status                    varchar(255),
  role                      varchar(255),
  posts                     integer,
  constraint pk_user primary key (id))
;

create sequence category_seq;

create sequence post_seq;

create sequence user_seq;

alter table post add constraint fk_post_category_1 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_post_category_1 on post (category_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists category;

drop table if exists comment;

drop table if exists post;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists category_seq;

drop sequence if exists post_seq;

drop sequence if exists user_seq;

