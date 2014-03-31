# --- Sample dataset

# --- !Ups

INSERT INTO user (email, name, status, posts) VALUES ('tpascua', 'Tyler Pascua', 'student', 123);
INSERT INTO user (email, name, status, posts) VALUES ('the email field', 'the name field', 'status field', 321);

# --- !Downs

delete from user;
