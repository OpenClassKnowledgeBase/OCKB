# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  description               varchar(255),
  url                       varchar(255),
  requested                 tinyint(1) default 0,
  user                      varchar(255),
  constraint pk_category primary key (id))
;

create table comment (
  id                        bigint,
  content                   TEXT,
  author                    varchar(255),
  submission_date           datetime,
  parent_post_id            bigint)
;

create table post (
  id                        bigint auto_increment not null,
  user_name                 varchar(255),
  is_sticky                 tinyint(1) default 0,
  category_id               bigint,
  title                     varchar(255),
  content                   TEXT,
  date_posted               datetime,
  latest_activity           datetime,
  comments                  bigint,
  votes                     bigint,
  constraint pk_post primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  name                      varchar(255),
  status                    varchar(255),
  role                      varchar(255),
  posts                     integer,
  constraint pk_user primary key (id))
;

alter table comment add constraint fk_comment_parent_post_1 foreign key (parent_post_id) references post (id) on delete restrict on update restrict;
create index ix_comment_parent_post_1 on comment (parent_post_id);
alter table post add constraint fk_post_category_2 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_post_category_2 on post (category_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table category;

drop table comment;

drop table post;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

