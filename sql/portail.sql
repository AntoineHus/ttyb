DROP TABLE IF EXISTS courses CASCADE;
DROP TABLE IF EXISTS exams CASCADE;

CREATE TABLE courses (
  id                           SERIAL NOT NULL UNIQUE PRIMARY KEY,
  code                         VARCHAR (20)  NOT NULL,
  subject                      VARCHAR (255) NOT NULL,
  ects_maximum_credits         INTEGER DEFAULT 0,
  ects_obtained_credits        INTEGER DEFAULT 0,
  ects_grade                   CHAR (1) DEFAULT '',
  french_global_average        REAL DEFAULT 0.00,
  french_finals_average        REAL DEFAULT 0.00,
  french_intermediates_average REAL DEFAULT 0.00
);

CREATE TABLE exams (
  id           SERIAL NOT NULL UNIQUE PRIMARY KEY,
  name         VARCHAR(20) NOT NULL,
  french_grade REAL DEFAULT 0.00,
  ref_course   INTEGER NOT NULL REFERENCES courses ON UPDATE CASCADE ON DELETE CASCADE
);