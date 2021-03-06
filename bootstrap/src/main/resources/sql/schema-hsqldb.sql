SET DATABASE SQL SYNTAX ORA TRUE;
DROP SCHEMA IF EXISTS TEST CASCADE;

CREATE SCHEMA TEST;

DROP SEQUENCE IF EXISTS SEQ_T_BOOKMARK;

DROP TABLE IF EXISTS T_BOOKMARK;

CREATE SEQUENCE SEQ_T_BOOKMARK START WITH 1 INCREMENT BY 1;

CREATE TABLE T_BOOKMARK
(
"ID" NUMBER(5, 0) NOT NULL,
"DESCRIPTION" VARCHAR2(200),
PRIMARY KEY("ID")
)
