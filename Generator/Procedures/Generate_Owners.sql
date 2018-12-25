create or replace PROCEDURE generate_owner (sex NUMBER) AS
first_name VARCHAR2(4000);
last_name VARCHAR2(4000);
id_number NUMBER;
login VARCHAR2(4000);
password_hash VARCHAR2(4000);
BEGIN

    SELECT COUNT(*) into id_number FROM DANE_DO_LOGOWANIA;
    id_number := id_number+1;
    
    SELECT LOGIN into login FROM (SELECT LOGIN, ROWNUM AS RN FROM GENERATOR_LOGINS) WHERE RN = id_number;
    SELECT PASSWORD_HASH into password_hash FROM (SELECT PASSWORD_HASH, ROWNUM AS RN FROM GENERATOR_PASSWORD_HASHES) WHERE RN = id_number;
    
    IF sex=0 THEN
        SELECT IMIE into first_name FROM
        ( SELECT IMIE FROM GENERATOR_MEN_NAMES ORDER BY dbms_random.value )
        WHERE rownum = 1;
        
        SELECT NAZWISKO into last_name FROM
        ( SELECT NAZWISKO FROM GENERATOR_MEN_LASTS ORDER BY dbms_random.value )
        WHERE rownum = 1;
    ELSE
        SELECT IMIE into first_name FROM
        ( SELECT IMIE FROM GENERATOR_WOMEN_NAMES ORDER BY dbms_random.value )
        WHERE rownum = 1;
        
        SELECT NAZWISKO into last_name FROM
        ( SELECT NAZWISKO FROM GENERATOR_WOMEN_LASTS ORDER BY dbms_random.value )
        WHERE rownum = 1;
    END IF;
    
    INSERT INTO WLASCICIELE
    VALUES (id_number, first_name, last_name);
    
    INSERT INTO OSOBY
    VALUES (id_number, null, id_number, null);
    
    INSERT INTO DANE_DO_LOGOWANIA
    VALUES (id_number, login, password_hash, id_number);
END;
