# --- First database schema

# --- !Ups

create table post (
  id bigint not null,
  title varchar(255),
  content longtext,
  constraint pk_post primary key (id))
;

create table comment (
  id bigint not null,
  post_id bigint not null,
  content longtext,
  constraint pk_comment primary key (id))
;

create sequence post_seq start with 1000;

create sequence comment_seq start with 1000;

alter table comment add constraint fk_comment_post_1 foreign key (post_id) references post (id) on delete restrict on update restrict;
create index ix_comment_post_1 on comment (post_id);

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists post;

drop table if exists comment;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists post_seq;

drop sequence if exists comment_seq;