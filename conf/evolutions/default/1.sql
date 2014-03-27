# user schema
 
# --- !Ups

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,  
  `slug` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `bio` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `middlename` varchar(50) DEFAULT NULL,
  `suffix` varchar(10) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `profile` varchar(400) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `github_username` varchar(50) DEFAULT NULL,
  `twitter_username` varchar(50) DEFAULT NULL,
  `linkedin_username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `bio_user_fk` (`user_id`),
  CONSTRAINT `bio_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `skill` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `title` varchar(50) NOT NULL DEFAULT '',
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `skill_user_fks` (`user_id`),
  CONSTRAINT `skill_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `proficiency` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `title` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `proficiency_user_fk` (`user_id`),
  CONSTRAINT `proficiency_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `job` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `company` varchar(100) NOT NULL DEFAULT '',
  `start` date NOT NULL,
  `end` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `job_user_fk` (`user_id`),
  CONSTRAINT `job_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `position` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `job_id` int(11) unsigned NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `position_job_fk` (`job_id`),
  CONSTRAINT `position_job_fk` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`)
);

CREATE TABLE `education` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `school` varchar(100) NOT NULL DEFAULT '',
  `degree` varchar(100) DEFAULT NULL,
  `major` varchar(100) DEFAULT NULL,
  `graduated_date` date DEFAULT NULL,
  `start_date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `education_user_fk` (`user_id`),
  CONSTRAINT `education_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);


# --- !Downs

DROP TABLE IF EXISTS `education`;
DROP TABLE IF EXISTS `position`;
DROP TABLE IF EXISTS `job`;
DROP TABLE IF EXISTS `proficiency`;
DROP TABLE IF EXISTS `skill`;
DROP TABLE IF EXISTS `user`;