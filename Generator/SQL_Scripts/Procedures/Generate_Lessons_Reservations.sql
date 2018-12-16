create or replace PROCEDURE generate_lessons_reservations (lessons_in_general NUMBER, reservations_in_general NUMBER, years_back NUMBER) AS

client_id NUMBER(6);
condition NUMBER(3);
condition2 NUMBER(6);
condition3 NUMBER(3);
date_of_service DATE;
date_of_service_temp DATE;
earliest_date DATE;
lesson_id NUMBER(9);
lifeguard_id NUMBER(4);
number_of_clients NUMBER(6);
number_of_enrolled NUMBER(1);
number_of_lifeguards NUMBER(2);
number_of_pools NUMBER(2);
number_of_tracks NUMBER(1);
ogolna_id NUMBER(3);
pool_id NUMBER(2);
rand_hour NUMBER(2);
reservation_id NUMBER(9);
service_id NUMBER(3);
start_date DATE;
status NUMBER(3);
track_number NUMBER(1);
transaction_date DATE;
transaction_id NUMBER(9);

BEGIN

	SELECT COUNT(*) INTO number_of_pools FROM BASENY;

	SELECT COUNT(*) INTO number_of_clients FROM KLIENCI;

	SELECT COUNT(*) INTO transaction_id FROM TRANSAKCJE;

	transaction_id := transaction_id + 1;
	reservation_id := 1;
	lesson_id := 1;

	FOR counter1 IN 1..lessons_in_general
	LOOP

		pool_id := dbms_random.value(1, number_of_pools+0.49);

        SELECT Numer_uslugi INTO service_id FROM USLUGI WHERE Baseny_Numer_obiektu = pool_id AND Nazwa_uslugi = 'lekcja p³ywania';

		SELECT Ogolne_Numer_uslugi INTO ogolna_id FROM USLUGI WHERE Numer_uslugi = service_id;

		SELECT data INTO start_date FROM
		(SELECT data FROM PRZEGLADY WHERE Baseny_Numer_obiektu = pool_id ORDER BY data)
		WHERE rownum = 1;

		start_date := start_date - INTERVAL '2' YEAR;

		earliest_date := TRUNC(SYSDATE - 365 * years_back);

		IF start_date < earliest_date THEN

            start_date := earliest_date;

		END IF;

		SELECT COUNT(*) INTO number_of_lifeguards FROM pracownicy_stanowiska
		WHERE Baseny_Numer_obiektu = pool_id AND Nazwa = 'ratownik';

		SELECT Numer_identyfikacyjny INTO lifeguard_id FROM pracownicy_stanowiska
		WHERE Baseny_Numer_obiektu = pool_id AND Nazwa = 'ratownik' AND rownum = 1
        ORDER BY Numer_identyfikacyjny;

		lifeguard_id := lifeguard_id + dbms_random.value(0,number_of_lifeguards+0.49);

		condition := dbms_random.value(0,999.49);

		IF condition<950 THEN

			date_of_service_temp := TRUNC(SYSDATE - DBMS_RANDOM.value(0,30*MONTHS_BETWEEN(SYSDATE, start_date)));

		ELSE

			date_of_service_temp := TRUNC(SYSDATE + DBMS_RANDOM.value(0,8));

		END IF;

		SELECT COUNT(*) INTO condition2 FROM LEKCJE_PLYWANIA WHERE Ogolne_numer_uslugi = ogolna_id AND Data_i_godzina >= date_of_service_temp
        AND Data_i_godzina < date_of_service_temp+1 AND Numer_ratownika = lifeguard_id;
        
        SELECT COUNT(*) INTO condition3 FROM LEKCJE_PLYWANIA WHERE Ogolne_numer_uslugi = ogolna_id AND Data_i_godzina >= date_of_service_temp
        AND Data_i_godzina < date_of_service_temp+1 AND Numer_ratownika != lifeguard_id;

		WHILE (condition2=3 OR condition3>0)
		LOOP
		
			condition := dbms_random.value(0,999.49);

			IF condition<950 THEN

				date_of_service_temp := TRUNC(SYSDATE - DBMS_RANDOM.value(0,30*MONTHS_BETWEEN(SYSDATE, start_date)));

			ELSE

				date_of_service_temp := TRUNC(SYSDATE + DBMS_RANDOM.value(0,8));

			END IF;

			SELECT COUNT(*) INTO condition2 FROM LEKCJE_PLYWANIA WHERE Ogolne_numer_uslugi = ogolna_id AND Data_i_godzina >= date_of_service_temp
            AND Data_i_godzina < date_of_service_temp+1 AND Numer_ratownika = lifeguard_id;
        
            SELECT COUNT(*) INTO condition3 FROM LEKCJE_PLYWANIA WHERE Ogolne_numer_uslugi = ogolna_id AND Data_i_godzina >= date_of_service_temp
            AND Data_i_godzina < date_of_service_temp+1 AND Numer_ratownika != lifeguard_id;

		END LOOP;

		IF condition<950 THEN

			transaction_date := TRUNC(date_of_service_temp -  DBMS_RANDOM.value(0,8));

		ELSE

			transaction_date := TRUNC(date_of_service_temp -  8);

		END IF;

        rand_hour := dbms_random.value(7,21.49);
        date_of_service := date_of_service_temp + rand_hour/24;

    	SELECT COUNT(*) INTO condition FROM LEKCJE_PLYWANIA WHERE Ogolne_numer_uslugi = ogolna_id AND Data_i_godzina = date_of_service;

    	WHILE condition=1 
    	LOOP

            rand_hour := dbms_random.value(7,21.49);
            date_of_service := date_of_service_temp + rand_hour/24;

            SELECT COUNT(*) INTO condition FROM LEKCJE_PLYWANIA WHERE Ogolne_numer_uslugi = ogolna_id AND Data_i_godzina = date_of_service;

        END LOOP;

    	number_of_enrolled := dbms_random.value(1,6.49);

        INSERT INTO LEKCJE_PLYWANIA
        VALUES (lesson_id, date_of_service, lifeguard_id, ogolna_id, number_of_enrolled);

        FOR counter2 IN 1..number_of_enrolled
        LOOP

        	client_id := dbms_random.value(1, number_of_clients+0.49);

    		SELECT COUNT(*) INTO condition FROM uczestnicy_lekcje 
			WHERE Data_i_godzina = date_of_service AND Klienci_Numer_klienta = client_id;

    		SELECT COUNT(*) INTO condition2 FROM uczestnicy_uslugi
        	WHERE Klienci_Numer_klienta = client_id AND Baseny_Numer_obiektu != pool_id;

        	WHILE (condition=1 OR condition2>0)
        	LOOP

            	client_id := dbms_random.value(1, number_of_clients+0.49);

        		SELECT COUNT(*) INTO condition FROM uczestnicy_lekcje 
        		WHERE Data_i_godzina = date_of_service AND Klienci_Numer_klienta = client_id;

    			SELECT COUNT(*) INTO condition2 FROM uczestnicy_uslugi
    			WHERE Klienci_Numer_klienta = client_id AND Baseny_Numer_obiektu != pool_id;

        	END LOOP;

            INSERT INTO UCZESTNICY_LEKCJI
        	VALUES(client_id, lesson_id);

            INSERT INTO TRANSAKCJE
            VALUES(transaction_id, transaction_date);

            INSERT INTO KOSZYKI
            VALUES(transaction_id, service_id, 1);

            transaction_id := transaction_id + 1;

        END LOOP;

        lesson_id := lesson_id + 1;

    END LOOP;



	FOR counter1 IN 1..reservations_in_general
	LOOP

        client_id := dbms_random.value(1, number_of_clients+0.49);

		SELECT COUNT(*) INTO condition FROM UCZESTNICY_LEKCJI WHERE Klienci_Numer_klienta = client_id;

		IF condition=0 THEN

			pool_id := dbms_random.value(1,number_of_pools+0.49);

		ELSE 

			SELECT Baseny_Numer_obiektu INTO pool_id FROM uczestnicy_uslugi
			WHERE Klienci_Numer_klienta = client_id AND rownum = 1;

		END IF;

		SELECT Numer_uslugi INTO service_id FROM USLUGI WHERE Baseny_Numer_obiektu = pool_id AND Nazwa_uslugi = 'rezerwacja toru';

		SELECT Ogolne_Numer_uslugi INTO ogolna_id FROM USLUGI WHERE Numer_uslugi = service_id;

		SELECT data INTO start_date FROM
		(SELECT data FROM PRZEGLADY WHERE Baseny_Numer_obiektu = pool_id ORDER BY data)
		WHERE rownum = 1;

		start_date := start_date - INTERVAL '2' YEAR;

		earliest_date := TRUNC(SYSDATE - 365 * years_back);

		IF start_date < earliest_date THEN

            start_date := earliest_date;

		END IF;

        SELECT Liczba_torow_plywackich INTO number_of_tracks FROM BASENY WHERE Numer_obiektu = pool_id;

        condition := dbms_random.value(0,999.49);

        IF condition<950 THEN

            date_of_service := TRUNC(SYSDATE - DBMS_RANDOM.value(0,30*MONTHS_BETWEEN(SYSDATE, start_date)));
            rand_hour := dbms_random.value(7,21.49);
            date_of_service := date_of_service + rand_hour/24;

        ELSE

            date_of_service := TRUNC(SYSDATE + DBMS_RANDOM.value(0,8));
            rand_hour := dbms_random.value(7,21.49);
            date_of_service := date_of_service + rand_hour/24;

        END IF;

        track_number := dbms_random.value(1,number_of_tracks+0.49);

        SELECT COUNT(*) INTO condition2 FROM rezerwacje_uslugi 
        WHERE Baseny_Numer_obiektu = pool_id AND Data_i_godzina = date_of_service AND Numer_toru = track_number;

        SELECT COUNT(*) INTO condition3 FROM rezerwacje_uslugi 
        WHERE Baseny_Numer_obiektu = pool_id AND Data_i_godzina = date_of_service AND Klienci_Numer_klienta = client_id;

        WHILE (condition2=1 OR condition3=1)
        LOOP
		
			condition := dbms_random.value(0,999.49);

            IF condition<950 THEN

                date_of_service := TRUNC(SYSDATE - DBMS_RANDOM.value(0,30*MONTHS_BETWEEN(SYSDATE, start_date)));
                rand_hour := dbms_random.value(7,21.49);
                date_of_service := date_of_service + rand_hour/24;

            ELSE

                date_of_service := TRUNC(SYSDATE + DBMS_RANDOM.value(0,8));
                rand_hour := dbms_random.value(7,21.49);
                date_of_service := date_of_service + rand_hour/24;

            END IF;

            track_number := dbms_random.value(1,number_of_tracks+0.49);

            SELECT COUNT(*) INTO condition2 FROM rezerwacje_uslugi 
            WHERE Baseny_Numer_obiektu = pool_id AND Data_i_godzina = date_of_service AND Numer_toru = track_number;

            SELECT COUNT(*) INTO condition3 FROM rezerwacje_uslugi 
            WHERE Baseny_Numer_obiektu = pool_id AND Data_i_godzina = date_of_service AND Klienci_Numer_klienta = counter1;

        END LOOP;

        IF condition<950 THEN

            status := dbms_random.value(0,999.49);

            IF status<900 THEN

                status := 1;

                INSERT INTO REZERWACJE_TORU
                VALUES(reservation_id, date_of_service, track_number, status, client_id, ogolna_id);

                transaction_date := date_of_service;

                INSERT INTO TRANSAKCJE
                VALUES(transaction_id, transaction_date);

                INSERT INTO KOSZYKI
                VALUES(transaction_id, service_id, 1);

                transaction_id := transaction_id + 1;

            ELSE

                status := 0;

                INSERT INTO REZERWACJE_TORU
                VALUES(reservation_id, date_of_service, track_number, status, client_id, ogolna_id);

            END IF;

        ELSE

            status := 0;

            INSERT INTO REZERWACJE_TORU
            VALUES(reservation_id, date_of_service, track_number, status, client_id, ogolna_id);

        END IF;

        reservation_id := reservation_id + 1;

     END LOOP;

END;