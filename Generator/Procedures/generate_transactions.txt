create or replace PROCEDURE generate_transactions (service_id NUMBER, amount_of_transactions NUMBER, years_back NUMBER) AS
start_date DATE;
transaction_date DATE;
transaction_id NUMBER(9);
condition NUMBER(3);
condition2 NUMBER(3);
pool_id NUMBER(2);
earliest_date DATE;

BEGIN

	SELECT Ogolne_numer_uslugi INTO condition FROM USLUGI WHERE Numer_uslugi = service_id;
    
    SELECT COUNT(*) INTO condition2 FROM OGOLNE WHERE Numer_uslugi = condition;

	IF condition2=0 THEN

		SELECT COUNT(*) INTO transaction_id FROM TRANSAKCJE;

		transaction_id := transaction_id + 1;

		SELECT Baseny_numer_obiektu INTO pool_id FROM USLUGI WHERE Numer_uslugi = service_id;

		SELECT Data INTO start_date FROM
		( SELECT Data FROM PRZEGLADY WHERE Baseny_numer_obiektu = pool_id ORDER BY Data )
		WHERE rownum = 1;

		start_date := start_date - INTERVAL '2' YEAR;

		earliest_date := TRUNC(SYSDATE - 365 * years_back);

		IF start_date < earliest_date THEN

            start_date := earliest_date;

		END IF;

		FOR counter IN 1..amount_of_transactions
		LOOP

			transaction_date := TRUNC(SYSDATE -  DBMS_RANDOM.value(0,30*MONTHS_BETWEEN(SYSDATE, start_date)));

			INSERT INTO TRANSAKCJE
			VALUES (transaction_id, transaction_date);

			INSERT INTO KOSZYKI
			VALUES (transaction_id, service_id, 1);

			transaction_id := transaction_id + 1;

		END LOOP;

	ELSE

		DBMS_OUTPUT.put_line('Uslugi ogolne dodawane sa w innym generatorze.');

	END IF;
END;