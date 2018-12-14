# Baza Danych basenów
Projekt polega na stworzeniu bazy danych, która ma wspomagać teoretyczną sieć basenów.

## Pierwsze kroki
Zanim zaczniemy cokolwiek pobierać, to najpierw musimy cokolwiek zrobić.
### Wymagania wstępne
Wykonanie choć dokumentacji z naszej strony, więc chwilowo nie ma nic
### Instalacja
Brak instalacji, bo nie ma czego
### Postawienie serwera lokalnie
1. Ściągnąć Oracle SQL Developer (18.3)
2. Ściągnąć Oracle 12c Database
3. Zapisać sobie gdzieć ustawione podczas instalacji hasło
4. Zapamiętać co jest wpisane w Global database name oraz Pluggable database name 
5. Zapamiętać ścieżkę do "Software location"
6. Po zainstalowaniu, wejść do tej ścieżki, a potem dalej do \network\admin\listener.ora
7. Dodać do SID_LIST
    (SID_DESC =
      (SID_NAME = orcl)      -> jeśli nie ustawiono innego podczas instalacji
      (ORACLE_HOME = wasza ścieżka do Software location)
    )
8. Po zapisaniu, stworzyć new connection (login:system password: z pkt.3 SID: orcl -> jeśli nie ustawiono innego podczas instalacji w Global database name)

### Tworzenie nowego usera dla Oracle 12c i SQL Developera
1. Zalogować się do bazy danych jako system
2. Wpisać komendę
   > ALTER SESSION SET CONTAINTER=pdborcl;  --Gdzie pdborcl to Pluggable database name
3. Utworzyć nowego użytkownika i nadać mu przywileje. Odpowiednio:

   ```
   CREATE USER login IDENTIFIED BY password;
   GRANT privileges TO login;
   ```
  
4. Należy utworzyć nowe połączenie
5. Uzupełnić odpowiednio pola tworzenia nowego połączenia
   > Przy czym zmieniamy wybór z pola SID na pole Service name i wpisujemy pdborcl -> jeśli nie ustawiono innego PDB name z punktu 4
6. Testujemy połączenie i łączymy się z bazą danych.   

### Utworzenie tabel dla sieci basenów w bazie danych
1. Otworzyć skrypt [Tables](https://github.com/WorkingFen/BDProject/blob/master/Generator/SQL_Scripts/Tables/Tables.sql) w sql developerze.
2. Powiązać plik z aktualnie użytkowaną bazą
3. Kliknąć "Run script"

### Utworzenie tabel do generatora w bazie danych
1. Otworzyć skrypty z katalogu [Generators](https://github.com/WorkingFen/BDProject/tree/master/Generator/SQL_Scripts/Generators) w sql developerze.
2. Powiązać pliki z aktualnie użytkowaną bazą
3. Kliknąć dla każdego skryptu "Run script"

### Generacja danych
1. Otworzyć skrypty z katalogu [Procedures](https://github.com/WorkingFen/BDProject/tree/master/Generator/SQL_Scripts/Procedures) w sql developerze.
2. Powiązać pliki z aktualnie użytkowaną bazą
3. Kliknąć dla każdego skryptu "Run script"

**W ten sposób zostały dodane procedury pozwalające wygenerować dane. Można ich używać oddzielnie lub wywołać je wszystkie przy pomocy pliku [Populate](https://github.com/WorkingFen/BDProject/blob/master/Generator/SQL_Scripts/Populate.sql), gdzie parametry procedur zostały wpisane "na sztywno" (można je zmieniać).**

### Usuwanie bazy danych
Ze względu na to, że usuwanie bazy danych Oracle wiąże się z ogromnymi kosztami nałożonymi na psychikę użytkownika (Jako, że wbudowany skrypt usuwania bazy danych bardziej szkodzi, aniżeli pomaga), kierujemy do bardzo pomocnej strony [usuwania bazy](http://www.rebellionrider.com/oracle-database-12c-tutorial/how-to-uninstall-oracle-database-12c-rebellionrider.htm#.XBOlpVxKiUl)

## Technologie
Technologie, które wykorzystujemy/będziemy wykorzystywać do projektu:
- Oracle Database 
  > Jako nasz DBMS
- JavaFX
  > Do tworzenia aplikacji okienkowej, wykorzystywać będziemy rozwiązania JavaFX
- JDBC
  > Komunikacja z bazą danych z poziomu aplikacji okientkowej

## Autorzy
- **Bochenek Mateusz** - [mbochene](https://github.com/mbochene)
- **Lipski Kamil** - [kklipski](https://github.com/kklipski)
- **Rzepka Karol** - [krzepka](https://github.com/krzepka)
- **Zawadka Piotr** - [WorkingFen](https://github.com/WorkingFen)

## Licencja
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
