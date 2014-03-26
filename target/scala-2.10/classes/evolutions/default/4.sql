# --- Sample dataset

# --- !Ups

insert into user (id, email, name, password, usertype, posts) values (1, 'tpascua', 'Tyler Pascua', '12345', 'Student', 33);
insert into user (id, email, name, password, usertype, posts) values (2, 'bleh', 'blah', '23456', 'Prof', 3);

# --- !Downs

delete from user;
