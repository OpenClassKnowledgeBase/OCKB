# --- First database schema

# --- !Ups

create table comment (
  id bigint not null,
  content longtext,
  constraint pk_comment primary key (id))
;


create sequence comment_seq start with 1000;


# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists comment;

SET REFERENTIAL_INTEGRITY TRUE;


drop sequence if exists comment_seq;