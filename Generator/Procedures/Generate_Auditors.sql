create or replace PROCEDURE generate_auditor AS
id_number NUMBER;
id_auditor NUMBER;
login VARCHAR2(4000);
password_hash VARCHAR2(4000);
BEGIN

    SELECT COUNT(*) into id_number FROM DANE_DO_LOGOWANIA;
    id_number := id_number+1;
    
    SELECT COUNT(*) into id_auditor FROM AUDYTORZY;
    id_auditor := id_auditor+1;

    SELECT LOGIN into login FROM (SELECT LOGIN, ROWNUM AS RN FROM GENERATOR_LOGINS) WHERE RN = id_number;
    SELECT PASSWORD_HASH into password_hash FROM (SELECT PASSWORD_HASH, ROWNUM AS RN FROM GENERATOR_PASSWORD_HASHES) WHERE RN = id_number;

    INSERT INTO AUDYTORZY
    VALUES (id_auditor);

    INSERT INTO OSOBY
    VALUES (id_number, id_auditor, null, null);

    INSERT INTO DANE_DO_LOGOWANIA
    VALUES (id_number, login, password_hash, id_number);
END;