CREATE TABLE `contact` (
  `id` INTEGER NOT NULL,
  `filename` VARCHAR(255),
  `name` VARCHAR(255),
  `phone` VARCHAR(255),
  `surname` VARCHAR(255) NOT NULL,
  `user_id` bigint,
  PRIMARY KEY (`id`)) engine=MyISAM;

CREATE TABLE hibernate_sequence (next_val bigint) engine=MyISAM;

INSERT INTO hibernate_sequence VALUES ( 2 );

INSERT INTO hibernate_sequence VALUES ( 2 );

CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL,
  `roles` VARCHAR(255)) engine=MyISAM;

CREATE TABLE `usr` (
  `id` bigint NOT NULL,
  `activation_code` VARCHAR(255),
  `active` BIT NOT NULL,
  `email` VARCHAR(255),
  `password` VARCHAR(255) NOT NULL,
  `username` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)) engine=MyISAM;

ALTER TABLE `contact`
  ADD CONSTRAINT contact_user_fk
  FOREIGN KEY (`user_id`) references usr (`id`);

ALTER TABLE `user_role`
  ADD CONSTRAINT user_role_user_fk
  FOREIGN KEY (`user_id`) references usr (`id`);