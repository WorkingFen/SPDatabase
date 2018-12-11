create or replace PROCEDURE generate_inspections (max_number_of_inspections NUMBER) AS
number_of_inspections NUMBER(2);
inspection_date DATE;
number_of_pools NUMBER(2);
inspection_id NUMBER(4);

BEGIN
    inspection_id := 1;
    SELECT COUNT(*) into number_of_pools FROM BASENY;
	FOR counter1 IN 1..number_of_pools
	LOOP
	
		number_of_inspections := dbms_random.value(1,max_number_of_inspections+0.49);
		inspection_date := TRUNC(SYSDATE + DBMS_RANDOM.value(0,731));
		
		INSERT INTO PRZEGLADY
		VALUES (inspection_id, inspection_date, counter1);
		
		inspection_id := inspection_id + 1;
		
		FOR counter2 IN 1..number_of_inspections-1
		LOOP
		
		    inspection_date := inspection_date - INTERVAL '2' YEAR;
			
			INSERT INTO PRZEGLADY
			VALUES (inspection_id, inspection_date, counter1);
			
			inspection_id := inspection_id + 1;
			
		END LOOP;
		
	END LOOP;
END;