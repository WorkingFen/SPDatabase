create or replace PROCEDURE generate_clients (number_of_clients NUMBER) AS
first_name VARCHAR2(4000);
last_name VARCHAR2(4000);
phone_number NUMBER(9);
email_address VARCHAR2(4000);
sex NUMBER(3);
first_char VARCHAR2(2);
condition NUMBER(6);
login VARCHAR2(4000);
password_hash VARCHAR2(4000);
BEGIN
    FOR counter IN 1..number_of_clients
    LOOP

		sex := dbms_random.value(0,999.49);
		IF sex>=500 THEN
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

		phone_number := dbms_random.value(500000000,889999999.49);
		first_char := SUBSTR(first_name, 1, 1);
        
        SELECT COUNT(*) INTO condition FROM KLIENCI WHERE SUBSTR(first_name, 1, 1) = SUBSTR(imie, 1, 1) AND last_name = nazwisko;
        
        IF condition=0 THEN
            email_address := first_char || '.' || last_name || '@GMAIL.COM';
        ELSE
            condition := condition + 1;
            email_address := first_char || '.' || last_name || condition || '@GMAIL.COM';
        END IF;

        INSERT INTO KLIENCI
        VALUES (counter, first_name, last_name, phone_number, email_address);

                    SELECT LOGIN into login FROM (SELECT LOGIN, ROWNUM AS RN FROM GENERATOR_LOGINS_CLIENTS) WHERE RN = counter;
                    SELECT PASSWORD_HASH into password_hash FROM (SELECT PASSWORD_HASH, ROWNUM AS RN FROM GENERATOR_PASSWORD_HASHES_C) WHERE RN = counter;

                INSERT INTO DANE_DO_LOGOWANIA_KLIENCI
                VALUES (counter, login, password_hash, counter);

    END LOOP;
END;