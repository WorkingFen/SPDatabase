  CREATE TABLE "SYSTEM"."GENERATOR_MEN_POSTS" 
   (	"STANOWISKO" VARCHAR2(4000 BYTE)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
REM INSERTING into SYSTEM.GENERATOR_MEN_POSTS
SET DEFINE OFF;
Insert into SYSTEM.GENERATOR_MEN_POSTS (STANOWISKO) values ('ekspert ds. marketingu');
Insert into SYSTEM.GENERATOR_MEN_POSTS (STANOWISKO) values ('HR');
Insert into SYSTEM.GENERATOR_MEN_POSTS (STANOWISKO) values ('kasjer');
Insert into SYSTEM.GENERATOR_MEN_POSTS (STANOWISKO) values ('kierownik');
Insert into SYSTEM.GENERATOR_MEN_POSTS (STANOWISKO) values ('konserwator');
Insert into SYSTEM.GENERATOR_MEN_POSTS (STANOWISKO) values ('ratownik');
Insert into SYSTEM.GENERATOR_MEN_POSTS (STANOWISKO) values ('sprz�tacz');