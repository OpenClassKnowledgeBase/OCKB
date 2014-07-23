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
  hidden                    tinyint(1) default 0,
  constraint pk_category primary key (id))
;

create table code_challenge (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  description               varchar(255),
  required_output           varchar(255),
  required_source           varchar(255),
  code_challenge_time       integer,
  category_id               bigint,
  constraint pk_code_challenge primary key (id))
;

create table code_challenge_scores (
  id                        bigint,
  user_name                 bigint,
  submission_date           datetime,
  challenge_id              bigint)
;

create table comment (
  id                        bigint,
  content                   TEXT,
  author                    varchar(255),
  submission_date           datetime,
  parent_post_id            bigint)
;

create table course (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  description               varchar(255),
  category_order            varchar(255),
  current_sort_order        varchar(255),
  student_roster            varchar(255),
  course_section            integer,
  semester                  varchar(255),
  ics_course                varchar(255),
  constraint pk_course primary key (id))
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
  users_voted               varchar(255),
  constraint pk_post primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  name                      varchar(255),
  status                    varchar(255),
  role                      varchar(255),
  posts                     integer,
  course_id                 bigint,
  constraint pk_user primary key (id))
;

alter table code_challenge_scores add constraint fk_code_challenge_scores_user_1 foreign key (user_name) references user (id) on delete restrict on update restrict;
create index ix_code_challenge_scores_user_1 on code_challenge_scores (user_name);
alter table code_challenge_scores add constraint fk_code_challenge_scores_challenge_2 foreign key (challenge_id) references code_challenge (id) on delete restrict on update restrict;
create index ix_code_challenge_scores_challenge_2 on code_challenge_scores (challenge_id);

alter table comment add constraint fk_comment_parent_post_3 foreign key (parent_post_id) references post (id) on delete restrict on update restrict;
create index ix_comment_parent_post_3 on comment (parent_post_id);
alter table post add constraint fk_post_category_4 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_post_category_4 on post (category_id);
alter table user add constraint fk_user_course_5 foreign key (course_id) references course (id) on delete restrict on update restrict;
create index ix_user_course_5 on user (course_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table category;

drop table code_challenge;

drop table code_challenge_scores;

drop table comment;

drop table course;

drop table post;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

