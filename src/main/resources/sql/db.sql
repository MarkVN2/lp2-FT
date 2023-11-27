
create database lp2;
use lp2;

CREATE TABLE students(
 cpf varchar(11) NOT NULL,
 student_name varchar(45) NOT NULL,
 birth_date date  NULL,
 weight float not null,
 height float not null,
 PRIMARY KEY (`cpf`),
 );

 CREATE TABLE registry(
entry_id int not null auto_increment,
cpf char(11) DEFAULT NULL,
entry_date datetime DEFAULT NULL,
weight float DEFAULT NULL,
height float NOT NULL,
imc double NOT NULL,
PRIMARY KEY (`entry_id`),
KEY `cpf` (`cpf`),
CONSTRAINT `registry_ibfk_1` FOREIGN KEY (`cpf`) REFERENCES `students` (`cpf`) on delete cascade
);