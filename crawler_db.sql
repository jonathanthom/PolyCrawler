create database if not exists CRAWLER;
use CRAWLER;

create table if not exists DEPARTMENT
(
    ID int auto_increment not null primary key,
	NAME varchar(50) not null,
	ABBREV varchar(10) not null,
	URL varchar(255) not null
);

create table if not exists INSTRUCTOR
(
    ID int auto_increment not null primary key,
	TEACHERURL varchar(255) not null,
	SOCIAL int,
	POLYRATING varchar(255),
	FIRSTNAME varchar(50) not null,
	LASTNAME varchar(50) not null,
	USERNAME varchar(50),
	DEPARTMENT int not null,
	foreign key (DEPARTMENT) references DEPARTMENT(ID) on delete cascade
);

create table if not exists PUBLICATION
(
    ID int auto_increment not null primary key,
	TITLE varchar(200) not null,
	LINK varchar(255) not null
);

create table if not exists OFFICE
(
    ID int auto_increment not null primary key,
	BUILDING int not null,
	ROOM int not null
);

create table if not exists CLASS
(
    ID int auto_increment not null primary key,
	DESCRIPTION longtext,
	NAME varchar(150) not null,
	CLASSNUMBER int not null,
	INSTRUCTOR int not null,
	foreign key (INSTRUCTOR) references INSTRUCTOR(ID) on delete cascade
);

create table if not exists WROTE
(
    ID int auto_increment not null primary key,
	INSTRUCTOR int not null,
	PUBLICATION int not null,
	foreign key (INSTRUCTOR) references INSTRUCTOR(ID) on delete cascade,
	foreign key (PUBLICATION) references PUBLICATION(ID) on delete cascade
);

create table if not exists LOCATED
(
    ID int auto_increment not null primary key,
	INSTRUCTOR int not null,
	OFFICE int not null,
	foreign key (INSTRUCTOR) references INSTRUCTOR(ID) on delete cascade,
	foreign key (OFFICE) references OFFICE(ID) on delete cascade
);
