-- Table: crud.person

-- DROP TABLE crud.person;

CREATE TABLE crud.person
(
    id bigserial NOT NULL PRIMARY KEY,
    name character varying(60) COLLATE pg_catalog."default" NOT NULL,
    sex character varying(15) COLLATE pg_catalog."default" NOT NULL,
    birthday date NOT NULL,    
    phone character varying(15) COLLATE pg_catalog."default",
    email character varying(60) COLLATE pg_catalog."default"
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE crud.person
    OWNER to postgres;