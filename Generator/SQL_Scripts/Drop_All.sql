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
----===-----------------------------------------Usuwanie tabel generatorów-----------------------------------------------===----
----========================================================================================================================----
DROP TABLE Generator_logins CASCADE CONSTRAINTS PURGE;
DROP TABLE Generator_men_lasts CASCADE CONSTRAINTS PURGE;
DROP TABLE Generator_men_names CASCADE CONSTRAINTS PURGE;
DROP TABLE Generator_men_posts CASCADE CONSTRAINTS PURGE;
DROP TABLE Generator_password_hashes CASCADE CONSTRAINTS PURGE;
DROP TABLE Generator_passwords CASCADE CONSTRAINTS PURGE;
DROP TABLE Generator_pool_names CASCADE CONSTRAINTS PURGE;
DROP TABLE Generator_services CASCADE CONSTRAINTS PURGE;
DROP TABLE Generator_streets CASCADE CONSTRAINTS PURGE;
DROP TABLE Generator_towns CASCADE CONSTRAINTS PURGE;
DROP TABLE Generator_women_lasts CASCADE CONSTRAINTS PURGE;
DROP TABLE Generator_women_names CASCADE CONSTRAINTS PURGE;
DROP TABLE Generator_women_posts CASCADE CONSTRAINTS PURGE;

----========================================================================================================================----
----===----------------------------------------------Usuwanie indeksów---------------------------------------------------===----
----========================================================================================================================----
DROP INDEX Dane_do_logowania__idx;
DROP INDEX Koszyki__idx;
DROP INDEX Uczestnicy_lekcji__idx;

----========================================================================================================================----
----===----------------------------------------------Usuwanie widoków----------------------------------------------------===----
----========================================================================================================================----
DROP VIEW Pracownicy_stanowiska;
DROP VIEW Rezerwacje_uslugi;
DROP VIEW Uczestnicy_lekcje;
DROP VIEW Uczestnicy_uslugi;

----========================================================================================================================----
----===----------------------------------------------Usuwanie procedur---------------------------------------------------===----
----========================================================================================================================----
DROP PROCEDURE Generate_auditor;
DROP PROCEDURE Generate_clients;
DROP PROCEDURE Generate_inspections;
DROP PROCEDURE Generate_lessons_reservations;
DROP PROCEDURE Generate_owner;
DROP PROCEDURE Generate_pools;
DROP PROCEDURE Generate_posts;
DROP PROCEDURE Generate_services;
DROP PROCEDURE Generate_transactions;
DROP PROCEDURE Generate_workers;
