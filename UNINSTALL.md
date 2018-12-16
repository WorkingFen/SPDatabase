# Baza Danych basenów - Dezinstalacja

## Usuwanie danych z tabel
1. Otworzyć skrypt [Delete Data](https://github.com/WorkingFen/BDProject/blob/master/Generator/SQL_Scripts/Delete_Data.sql) w sql developerze.
2. Powiązać plik z aktualnie użytkowaną bazą
3. Kliknąć "Run script" lub "Run statemnet" w przypadku usuwania wybranych danych

## Usuwanie tabel
1. Otworzyć skrypt [Drop Table](https://github.com/WorkingFen/BDProject/blob/master/Generator/SQL_Scripts/Drop_All.sql) w sql developerze.
2. Powiązać plik z aktualnie użytkowaną bazą
3. Kliknąć "Run script" lub "Run statemnet" w przypadku usuwania wybranych tabel

## Usuwanie przestrzeni tabel
1. Otworzyć skrypt [Drop Tablespace](https://github.com/WorkingFen/BDProject/blob/master/Generator/SQL_Scripts/Drop_Tablespaces.sql) w sql developerze.
2. Powiązać plik z aktualnie użytkowaną bazą
3. Kliknąć "Run script" lub "Run statemnet" w przypadku usuwania wybranych przestrzeni tabel
4. Usunąć manualnie z katalogu zapisanej bazy _../product/12.1.0/dbhome_nrbazy/database_ pliki odpowiednie dla usuwanych przestrzeni tabel

## Usuwanie bazy danych Oracle 12c
Ze względu na to, że usuwanie bazy danych Oracle wiąże się z ogromnymi kosztami nałożonymi na psychikę użytkownika 
> Jako, że wbudowany skrypt usuwania bazy danych bardziej szkodzi, aniżeli pomaga 

kierujemy do bardzo pomocnej strony [usuwania bazy](http://www.rebellionrider.com/oracle-database-12c-tutorial/how-to-uninstall-oracle-database-12c-rebellionrider.htm#.XBOlpVxKiUl)