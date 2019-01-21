create or replace PROCEDURE generate_services AS
number_of_pools NUMBER(2);
service_id NUMBER(3);
ogolna_id NUMBER(3);
lokalna_id NUMBER(3);
price NUMBER(3);
is_offered NUMBER(3);
BEGIN

	SELECT COUNT(*) into number_of_pools FROM BASENY;
	number_of_pools := number_of_pools - 1;
	service_id := 1;
	ogolna_id := 1;
	lokalna_id := 1;
	
	FOR counter IN 1..number_of_pools
	LOOP
	
	    price := dbms_random.value(85,95.49);
		
		INSERT INTO LOKALNE
		VALUES (lokalna_id);
		
		INSERT INTO USLUGI
		VALUES (service_id, 'karnet', price, counter, NULL, lokalna_id);
		
		service_id := service_id + 1;
		lokalna_id := lokalna_id + 1;
		
	END LOOP;
	
	FOR counter IN 1..number_of_pools
	LOOP
	
	    price := dbms_random.value(6,15.49);
		
		INSERT INTO LOKALNE
		VALUES (lokalna_id);
		
		INSERT INTO USLUGI
		VALUES (service_id, 'bilet normalny', price, counter, NULL, lokalna_id);
		
		service_id := service_id + 1;
		lokalna_id := lokalna_id + 1;
		
	END LOOP;
	
	FOR counter IN 1..number_of_pools
	LOOP
	
	    price := dbms_random.value(4,10.49);
		
		INSERT INTO LOKALNE
		VALUES (lokalna_id);
		
		INSERT INTO USLUGI
		VALUES (service_id, 'bilet ulgowy', price, counter, NULL, lokalna_id);
		
		service_id := service_id + 1;
		lokalna_id := lokalna_id + 1;
		
	END LOOP;
	
	FOR counter IN 1..number_of_pools
	LOOP
		
		is_offered := dbms_random.value(0,999.49);
		
		IF is_offered>=500 THEN
		
			price := dbms_random.value(60,120.49);
			
			INSERT INTO LOKALNE
			VALUES (lokalna_id);
		
			INSERT INTO USLUGI
			VALUES (service_id, 'masa¿', price, counter, NULL, lokalna_id);
		
			service_id := service_id + 1;
			lokalna_id := lokalna_id + 1;
			
		END IF;
		
	END LOOP;
	
	FOR counter IN 1..number_of_pools
	LOOP
		
		is_offered := dbms_random.value(0,999.49);
		
		IF is_offered>=500 THEN
		
			price := dbms_random.value(15,30.49);
			
			INSERT INTO LOKALNE
			VALUES (lokalna_id);
		
			INSERT INTO USLUGI
			VALUES (service_id, 'sauna', price, counter, NULL, lokalna_id);
		
			service_id := service_id + 1;
			lokalna_id := lokalna_id + 1;
			
		END IF;
		
	END LOOP;
	
	FOR counter IN 1..number_of_pools
	LOOP
		
		is_offered := dbms_random.value(0,999.49);
		
		IF is_offered>=500 THEN
		
			price := dbms_random.value(10,20.49);
				
			INSERT INTO LOKALNE
			VALUES (lokalna_id);	
			
			INSERT INTO USLUGI
			VALUES (service_id, 'termy', price, counter, NULL, lokalna_id);
			
			service_id := service_id + 1;
			lokalna_id := lokalna_id + 1;
            
		END IF;
		
	END LOOP;
	
	FOR counter IN 1..number_of_pools
	LOOP
	
	    price := dbms_random.value(15,30.49);
		
		INSERT INTO LOKALNE
		VALUES (lokalna_id);
		
		INSERT INTO USLUGI
		VALUES (service_id, 'aerobik', price, counter, NULL, lokalna_id);
		
		service_id := service_id + 1;
		lokalna_id := lokalna_id + 1;
		
	END LOOP;
	
	FOR counter IN 1..number_of_pools
	LOOP
	
	    price := dbms_random.value(50,100.49);
		
		INSERT INTO OGOLNE
		VALUES (ogolna_id);
		
		INSERT INTO USLUGI
		VALUES (service_id, 'rezerwacja toru', price, counter, ogolna_id, NULL);
		
		service_id := service_id + 1;
		ogolna_id := ogolna_id + 1;
		
	END LOOP;
	
	FOR counter IN 1..number_of_pools
	LOOP
	
	    price := dbms_random.value(30,60.49);
		
		INSERT INTO OGOLNE
		VALUES (ogolna_id);
		
		INSERT INTO USLUGI
		VALUES (service_id, 'lekcja p³ywania', price, counter, ogolna_id, NULL);

		service_id := service_id + 1;
		ogolna_id := ogolna_id + 1;
		
	END LOOP;
	
	FOR counter IN 1..number_of_pools
	LOOP
		
		is_offered := dbms_random.value(0,999.49);
		
		IF is_offered>=500 THEN
		
			price := dbms_random.value(30,40.49);
			
			INSERT INTO LOKALNE
			VALUES (lokalna_id);
		
			INSERT INTO USLUGI
			VALUES (service_id, 'squash', price, counter, NULL, lokalna_id);
			
			service_id := service_id + 1;
			lokalna_id := lokalna_id + 1;
		
		END IF;
		
	END LOOP;
	
END;