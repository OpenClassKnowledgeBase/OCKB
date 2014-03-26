# --- Sample dataset

# --- !Ups

insert into user (email, name, password, usertype, posts) values ('tpascua', 'Tyler Pascua', '12345', 'Student', 33);
insert into user (email, name, password, usertype, posts) values ('pascuamr', 'Matt Pascua', '23456', 'Student', 3);

# --- !Downs

delete from user;
