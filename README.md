# Baza Danych basenów
Projekt polega na stworzeniu bazy danych, która ma wspomagać teoretyczną sieć basenów.

## Pierwsze kroki
Zanim zaczniemy cokolwiek pobierać, to najpierw trzeba wiedzieć co należy zrobić.

**Reminder:** Nigdy nie należy zmieniać ustawień ani plików bazy danych poza dedykowanymi programami, jak również nie należy zatrzymywać usług powiązanych z bazą danych! Wiąże się to z zablokowaniem dostępu i koniecznością usunięcia tejże bazy danych!
> Learning the hard way

### Wymagania wstępne
- Należy pobrać [Oracle Database 12c Release 2](https://www.oracle.com/technetwork/database/enterprise-edition/downloads/index.html) po uprzednim zaakceptowaniu licencji i jednokrotnym zalogowaniu się na stronę Oracle.
- Należy pobrać [SQL Developer](https://www.oracle.com/technetwork/developer-tools/sql-developer/downloads/index.html) po uprzednim zaakceptowaniu licencji

### Instalacja
[Instalacja](https://github.com/WorkingFen/BDProject/blob/master/INSTALL.md) bazy danych krok po kroku:
1. [Lokalne postawienie serwera](https://github.com/WorkingFen/BDProject/blob/master/INSTALL.md#postawienie-serwera-lokalnie)
2. [Stworzenie usera](https://github.com/WorkingFen/BDProject/blob/master/INSTALL.md#tworzenie-nowego-usera-dla-oracle-12c-i-sql-developera)
3. [Fizyczny model bazy](https://github.com/WorkingFen/BDProject/blob/master/INSTALL.md#utworzenie-tabel-dla-sieci-basen%C3%B3w-w-bazie-danych)
4. [Widoki](https://github.com/WorkingFen/BDProject/blob/master/INSTALL.md#generacja-widok%C3%B3w)
5. [Generatory](https://github.com/WorkingFen/BDProject/blob/master/INSTALL.md#utworzenie-tabel-do-generatora-w-bazie-danych)
6. [Procedury zaludniania](https://github.com/WorkingFen/BDProject/blob/master/INSTALL.md#generacja-danych)

Lub instalacja całej bazy danych przy pomocy [generatora](https://github.com/WorkingFen/BDProject/blob/master/Generator/Generate.sql). Uruchamiamy go, tak jak inne skrypty.

### Pomoc
1. [ORA-01033 - rozwiązanie](https://github.com/WorkingFen/BDProject/blob/master/HELP.md#ora-01033)

### Dezinstalacja
[Dezinstalacja](https://github.com/WorkingFen/BDProject/blob/master/UNINSTALL.md) bazy danych w zależności od potrzeb:
1. [Usuwanie danych z tabel](https://github.com/WorkingFen/BDProject/blob/master/UNINSTALL.md#usuwanie-danych-z-tabel)
2. [Usuwanie tabel](https://github.com/WorkingFen/BDProject/blob/master/UNINSTALL.md#usuwanie-tabel)
3. [Usuwanie przestrzeni tabel](https://github.com/WorkingFen/BDProject/blob/master/UNINSTALL.md#usuwanie-przestrzeni-tabel)
4. [Usuwanie bazy danych Oracle 12c](https://github.com/WorkingFen/BDProject/blob/master/UNINSTALL.md#usuwanie-bazy-danych-oracle-12c)

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
