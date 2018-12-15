----========================================================================================================================----
----===-------------------------------------Wywolywanie procedur generujacych--------------------------------------------===----
----========================================================================================================================----
DECLARE
number_of_services NUMBER(3) := 0;
BEGIN
generate_pools(5);
generate_inspections(10);
generate_posts;
generate_owner(0);
generate_auditor;
generate_workers(0,1,1,5,1,2,10,5);
generate_services;
generate_clients(10000);

SELECT COUNT(*) INTO number_of_services FROM USLUGI;

FOR counter IN 1..number_of_services
LOOP

	generate_transactions(counter, 1000, 2);
	
END LOOP;

generate_lessons_and_reservations(1000, 1000, 2);
END;