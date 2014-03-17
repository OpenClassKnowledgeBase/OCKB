# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table post_submission (
  id                        bigint not null,
  user_name                 varchar(255),
  category                  varchar(255),
  title                     varchar(255),
  content                   varchar(255),
  date_posted               timestamp,
  constraint pk_post_submission primary key (id))
;

create sequence post_submission_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists post_submission;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists post_submission_seq;

