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
5. Po zainstalowaniu wejść do tej ścieżki, a potem dalej do \network\admin\listener.ora
6. Dodać do SID_LIST
    (SID_DESC =
      (SID_NAME = orcl)      -> jeśli nie ustawiono innego podczas instalacji
      (ORACLE_HOME = wasza ścieżka do Software location)
    )
7. Po zapisaniu stworzyć new connection (login:system password: z pkt.3 SID: orcl -> jeśli nie ustawiono innego podczas instalacji)
 
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
