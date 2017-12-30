drop table user if exists;

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
VALUES (1, 'himanshu', '$2a$10$Dda1sOv8IvLptY95yobXV.N4jzHPKlEF/av8PZccnclO96vkYwHBu', 'abc@test.com', 1, 1, 1, 1);