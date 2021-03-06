CREATE TABLE role (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name varchar(45) NOT NULL
);

ALTER TABLE role ADD CONSTRAINT role_name_uniq UNIQUE (name);

INSERT INTO role (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, name) VALUES (2, 'ROLE_USER');
INSERT INTO role (id, name) VALUES (3, 'ROLE_MAINTENANCE');

CREATE TABLE user (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  username varchar(45) NOT NULL,
  password varchar(200) NOT NULL,
  email varchar(200) NOT NULL,
  nonlocked tinyint NOT NULL,
  nonexpired tinyint NOT NULL,
  credentialsnonexpired tinyint NOT NULL,
  enabled tinyint NOT NULL
);

ALTER TABLE user ADD CONSTRAINT user_email_uniq UNIQUE (email);
ALTER TABLE user ADD CONSTRAINT user_username_uniq UNIQUE (username);

INSERT INTO user (id, username, password, email, nonlocked, nonexpired, credentialsnonexpired, enabled) 
VALUES (1, 'admin', 'admin', 'admin@test.com', 1, 1, 1, 1);

INSERT INTO user (id, username, password, email, nonlocked, nonexpired, credentialsnonexpired, enabled)
VALUES (2, 'user', 'user', 'user@test.com', 1, 1, 1, 1);

INSERT INTO user (id, username, password, email, nonlocked, nonexpired, credentialsnonexpired, enabled)
VALUES (3, 'support', 'support', 'support@test.com', 1, 1, 1, 1);

CREATE TABLE user_role_mapping (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  userid int NOT NULL,
  roleid int NOT NULL
);

ALTER TABLE user_role_mapping ADD FOREIGN KEY (roleid) REFERENCES role (id);
ALTER TABLE user_role_mapping ADD FOREIGN KEY (userid) REFERENCES user (id);

INSERT INTO user_role_mapping (id, userid, roleid) VALUES (1, 1, 2);
INSERT INTO user_role_mapping (id, userid, roleid) VALUES (2, 2, 1);
INSERT INTO user_role_mapping (id, userid, roleid) VALUES (3, 3, 3);
