CREATE TABLE PERSON_TBL ( person_id BIGINT NOT NULL AUTO_INCREMENT, first_name VARCHAR(25) NOT NULL, last_name VARCHAR(25) NOT NULL, username VARCHAR(25) NOT NULL, date_of_birth VARCHAR(255) NOT NULL, ssn VARCHAR(255) NOT NULL, gender VARCHAR(255), email VARCHAR(255) NOT NULL, PRIMARY KEY (person_id));

ALTER TABLE person_tbl ADD CONSTRAINT uc_person_tbl_username UNIQUE (username);

INSERT INTO PERSON_TBL (FIRST_NAME, LAST_NAME, USERNAME, DATE_OF_BIRTH, SSN, GENDER, EMAIL) VALUES ('Erica','Wright','erwright','02/26/1971', '123-45-6789', 'female', 'erwright@me.com');

INSERT INTO PERSON_TBL (FIRST_NAME, LAST_NAME, USERNAME, DATE_OF_BIRTH, SSN, GENDER, EMAIL) VALUES ('Patrick','Baril','pabaril','01/23/1982', '123-45-6780', 'male', 'pabaril@gmail.com');

INSERT INTO PERSON_TBL (FIRST_NAME, LAST_NAME, USERNAME, DATE_OF_BIRTH, SSN, GENDER, EMAIL) VALUES ('Christopher','Martin','chmartin','03/21/1966', '123-45-6781', 'male', 'chmartin@yahoo.com');

INSERT INTO PERSON_TBL (FIRST_NAME, LAST_NAME, USERNAME, DATE_OF_BIRTH, SSN, GENDER, EMAIL) VALUES ('Daniel','Maman','damaman','10/25/1977', '123-45-6782', 'male', 'damaman@hotmail.com');

INSERT INTO PERSON_TBL (FIRST_NAME, LAST_NAME, USERNAME, DATE_OF_BIRTH, SSN, GENDER, EMAIL) VALUES ('Jill','Scott','jiscott','04/04/1972', '123-45-6783', 'female', 'jiscott@sbcglobal.net');
