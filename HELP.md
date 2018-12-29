# Baza Danych basenów - Pomoc

## ORA-01033
   > Rozwiązanie dla **ORA-01033** Oracle initialization or shutdown in progress standby

**Włączenie dołączanych baz danych** 
   
1. Zalogować się do bazy danych jako sys z hasłem dla system i rolą SYSDBA
2. Wpisać komendy

   ```
   ALTER PLUGGABLE DATABASE ALL OPEN;
   ALTER PLUGGABLE DATABASE ALL SAVE STATE;
   ```
3. Wykonać komendy   
4. Wylogować się z tego konta, zapomnieć i się uspokoić, bo wszystko już będzie dobrze