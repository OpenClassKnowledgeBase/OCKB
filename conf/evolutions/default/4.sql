# --- Sample dataset

# --- !Ups

insert into user (id, email, name, password, usertype, posts) values (1, 'tpascua', 'Tyler Pascua', '12345', 'Student', 33);
insert into user (id, email, name, password, usertype, posts) values (2, 'pascuamr', 'Matt Pascua', '23456', 'Student', 3);

# --- !Downs

delete from user;
