----========================================================================================================================----
----===------------------------------------------Dodawanie widoków bazy--------------------------------------------------===----
----========================================================================================================================----
CREATE OR REPLACE VIEW uczestnicy_uslugi AS SELECT Klienci_Numer_klienta, Baseny_Numer_obiektu FROM UCZESTNICY_LEKCJI 
INNER JOIN LEKCJE_PLYWANIA ON UCZESTNICY_LEKCJI.Lekcje_plywania_Numer_lekcji = LEKCJE_PLYWANIA.Numer_lekcji
INNER JOIN OGOLNE ON LEKCJE_PLYWANIA.Ogolne_Numer_uslugi = OGOLNE.Numer_uslugi
INNER JOIN USLUGI ON USLUGI.Ogolne_Numer_uslugi = OGOLNE.Numer_uslugi;
    
CREATE OR REPLACE VIEW pracownicy_stanowiska AS SELECT Numer_identyfikacyjny, Baseny_numer_obiektu, Nazwa FROM PRACOWNICY 
INNER JOIN STANOWISKA ON PRACOWNICY.Stanowiska_Numer_stanowiska = STANOWISKA.Numer_stanowiska;
    
CREATE OR REPLACE VIEW uczestnicy_lekcje AS SELECT Data_i_godzina, Klienci_numer_klienta FROM LEKCJE_PLYWANIA 
INNER JOIN UCZESTNICY_LEKCJI ON UCZESTNICY_LEKCJI.Lekcje_plywania_Numer_lekcji = LEKCJE_PLYWANIA.Numer_lekcji;
    
CREATE OR REPLACE VIEW rezerwacje_uslugi AS SELECT Baseny_numer_obiektu, Data_i_godzina, Numer_toru, Klienci_numer_klienta FROM REZERWACJE_TORU 
INNER JOIN OGOLNE ON OGOLNE.Numer_uslugi = REZERWACJE_TORU.Ogolne_Numer_uslugi
INNER JOIN USLUGI ON USLUGI.Ogolne_Numer_uslugi = OGOLNE.Numer_uslugi;