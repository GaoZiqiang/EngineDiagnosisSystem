drop engine_diagnosis_system;
create database engine_diagnosis_system;
CREATE SEQUENCE public.engine_info_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 126
  CACHE 1;
ALTER TABLE public.engine_info_id_seq
  OWNER TO postgres;

CREATE TABLE public.engine_info
(
  "time" date,
    "tem_T" double precision,
  "pressure_P1" double precision,
    "pressure_P2" double precision,
  "gap_L1" double precision,
    "gap_L2" double precision,
  "offset_X" double precision,
    "exception_T" character varying(128),
  "exception_P1" character varying(128),
    "exception_P2" character varying(128),
  "exception_L1" character varying(128),
    "exception_L2" character varying(128),
  "exception_X" character varying(128),
    id integer NOT NULL DEFAULT nextval('engine_info_id_seq'::regclass),
  CONSTRAINT pk_engine PRIMARY KEY (id)
  )
WITH (
  OIDS=FALSE
  );
ALTER TABLE public.engine_info
  OWNER TO postgres;
COMMENT ON TABLE public.engine_info
  IS '发动机信息';

CREATE SEQUENCE public.user_info_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 25
  CACHE 1;
ALTER TABLE public.user_info_id_seq
  OWNER TO postgres;

CREATE TABLE public.user_info
(
  username character varying(32),
    password character varying(32),
  id integer NOT NULL DEFAULT nextval('user_info_id_seq'::regclass),
    CONSTRAINT pk_user PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
  );
ALTER TABLE public.user_info
  OWNER TO postgres;
