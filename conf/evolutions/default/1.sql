# user schema
#
# --- !Ups

CREATE TABLE user (
  id UUID NOT NULL,
  slug varchar(50) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE bio (
  id UUID NOT NULL,
  user_id UUID NOT NULL,
  firstname varchar(50) NOT NULL,
  lastname varchar(50) NOT NULL,
  middlename varchar(50),
  suffix varchar(10),
  title varchar(100),
  profile varchar(400),
  email varchar(50),
  phone varchar(20),
  github_username varchar(50),
  twitter_username varchar(50),
  linkedin_username varchar(50),
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES PUBLIC.user(id)
);

CREATE TABLE skill (
  id UUID NOT NULL,
  user_id UUID NOT NULL,
  title varchar(50) NOT NULL DEFAULT '',
  description varchar(500),
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES PUBLIC.user(id)
);

CREATE TABLE proficiency (
  id UUID NOT NULL,
  user_id UUID NOT NULL,
  title varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES PUBLIC.user(id)
);

CREATE TABLE job (
  id UUID NOT NULL,
  user_id UUID NOT NULL,
  company varchar(100) NOT NULL DEFAULT '',
  start date NOT NULL,
  end date,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES PUBLIC.user(id)
);

CREATE TABLE position (
  id UUID NOT NULL,
  job_id UUID NOT NULL,
  title varchar(50) NOT NULL,
  description varchar(500),
  PRIMARY KEY (id),
  FOREIGN KEY (job_id) REFERENCES job(id)
);

CREATE TABLE education (
  id UUID NOT NULL,
  user_id UUID NOT NULL,
  school varchar(100) NOT NULL DEFAULT '',
  degree varchar(100),
  major varchar(100),
  graduated_date date,
  start_date date NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES user(id)
);


# --- !Downs

DROP TABLE education;
DROP TABLE position;
DROP TABLE job;
DROP TABLE proficiency;
DROP TABLE skill;
DROP TABLE user;
