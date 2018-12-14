----========================================================================================================================----
----===-----------------------------------------------Usuwanie tabel-----------------------------------------------------===----
----========================================================================================================================----

DROP TABLE Audytorzy CASCADE CONSTRAINTS PURGE;
DROP TABLE Baseny CASCADE CONSTRAINTS PURGE;
DROP TABLE Dane_do_logowania CASCADE CONSTRAINTS PURGE;
DROP TABLE Klienci CASCADE CONSTRAINTS PURGE;
DROP TABLE Koszyki CASCADE CONSTRAINTS PURGE;
DROP TABLE Lekcje_plywania CASCADE CONSTRAINTS PURGE;
DROP TABLE Lokalne CASCADE CONSTRAINTS PURGE;
DROP TABLE Ogolne CASCADE CONSTRAINTS PURGE;
DROP TABLE Osoby CASCADE CONSTRAINTS PURGE;
DROP TABLE Pracownicy CASCADE CONSTRAINTS PURGE;
DROP TABLE Przeglady CASCADE CONSTRAINTS PURGE;
DROP TABLE Rezerwacje_toru CASCADE CONSTRAINTS PURGE;
DROP TABLE Stanowiska CASCADE CONSTRAINTS PURGE;
DROP TABLE Transakcje CASCADE CONSTRAINTS PURGE;
DROP TABLE Uczestnicy_lekcji CASCADE CONSTRAINTS PURGE;
DROP TABLE Uslugi CASCADE CONSTRAINTS PURGE;
DROP TABLE Wlasciciele CASCADE CONSTRAINTS PURGE;

----========================================================================================================================----
----===----------------------------------------------Usuwanie indeksów---------------------------------------------------===----
----========================================================================================================================----

DROP INDEX Dane_do_logowania__idx;