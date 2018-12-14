create or replace PROCEDURE generate_posts AS
BEGIN
    INSERT INTO STANOWISKA
    VALUES (1, 'ekspert ds. marketingu', 7000);
    INSERT INTO STANOWISKA
    VALUES (2, 'HR', 10000);
    INSERT INTO STANOWISKA
    VALUES (3, 'kasjer', 3500);
    INSERT INTO STANOWISKA
    VALUES (4, 'kierownik', 15000);
    INSERT INTO STANOWISKA
    VALUES (5, 'konserwator', 5000);
    INSERT INTO STANOWISKA
    VALUES (6, 'ratownik', 3500);
    INSERT INTO STANOWISKA
    VALUES (7, 'sprz¹tacz', 3500);
END;