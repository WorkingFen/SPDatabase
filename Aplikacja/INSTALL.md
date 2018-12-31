# Baza Danych basenów - Instalacja aplikacji

## Instalacja aplikacji nieskompilowanej
   > Wykorzystywaliśmy IntelliJ IDEA
   
### Tworzenie projektu

1. Pobrać pliki z [tego](https://github.com/WorkingFen/BDProject/tree/master/Aplikacja/src) katalogu
2. Stworzyć nowy projekt IntelliJ, wykorzystujący istniejące pliki źródłowe (te które zostały pobrane)

### Instalacja JDBC
   
1. Ze strony Oracle pobrać [ojdbc8](https://www.oracle.com/technetwork/database/features/jdbc/jdbc-ucp-122-3110062.html) przy wcześniejszym zaakceptowaniu regulaminu.
2. Oracle poprosi o jednorazowe zalogowanie
   > Należy mieć wcześniej utworzone konto, lub stworzyć nowe
3. Pobrany plik .jar wrzucić do katalogu [JDBC](https://github.com/WorkingFen/BDProject/tree/master/Aplikacja/src/JDBC) wraz z innymi plikami.
4. Otworzyć **Project Structure** (Ctrl+Alt+Shift+S), a następnie przejść do **Modules**
5. Po prawej stronie okna nacisnąć znak "+", a następnie **JARs or directories...**
6. Wybrać ścieżkę, w której znajduje się plik **ojdbc8.jar**, nacisnąć na niego i następnie **OK**
7. Zakończyć poprzez zatwierdzenie zmian i wyłączenie okna

### Instalacja JavaFX

1. 

### Konfiguracja sposobu wywoływania aplikacji

1. 