# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comment (
  id                        bigint,
  content                   varchar(255),
  author                    varchar(255),
  submission_date           timestamp)
;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists comment;

SET REFERENTIAL_INTEGRITY TRUE;

