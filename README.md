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
2. Ściągnąć Oracle 12g Database
3. Zapisać sobie gdzieć ustawione podczas instalacji hasło
4. Zapamiętać ścieżkę do "Software location"
5. Po zainstalowaniu, wejść do tej ścieżki, a potem dalej do \network\admin\listener.ora
6. Dodać do SID_LIST
    (SID_DESC =
      (SID_NAME = orcl)      -> jeśli nie ustawiono innego podczas instalacji
      (ORACLE_HOME = wasza ścieżka do Software location)
    )
7. Po zapisaniu, stworzyć new connection (login:system password: z pkt.3 SID: orcl -> jeśli nie ustawiono innego podczas instalacji)

### Utworzenie tabel dla sieci basenów w bazie danych
1. Przekopiować zawartość z pliku Siec_basenow_tabele z katalogu Generator do worksheeta w sql developerze.
2. Kliknąć "Run script"

### Utworzenie tabel do generatora w bazie danych
1. Przekopiować zawartość z plików z katalogu Generator/Generator_tables_scripts do worksheeta w sql developerze.
2. Kliknąć "Run script"

### Generacja danych
1. Przekopiować zawartość z plików (o nazwach zaczynających się od "generate") z katalogu Generator/Procedures do worksheeta w sql developerze.
2. Kliknąć "Run script"
#### W ten sposób zostały dodane procedury pozwalające wygenerować dane. Można ich używać oddzielnie lub wywołać je wszystkie przy pomocy pliku "populate.txt", gdzie parametry procedur zostały wpisane "na sztywno" (można je zmieniać).

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
