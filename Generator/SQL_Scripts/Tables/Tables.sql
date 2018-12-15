----========================================================================================================================----
----===-----------------------------------------------Tworzenie tabel----------------------------------------------------===----
----========================================================================================================================----

---------------------------------------------------------Audytorzy--------------------------------------------------------------
CREATE TABLE audytorzy (
    numer_identyfikacyjny   INTEGER NOT NULL
);

ALTER TABLE audytorzy ADD CONSTRAINT audytorzy_pk PRIMARY KEY ( numer_identyfikacyjny );

----------------------------------------------------------Baseny----------------------------------------------------------------
CREATE TABLE baseny (
    numer_obiektu             INTEGER NOT NULL,
    nazwa_obiektu             VARCHAR2(4000) NOT NULL,
    liczba_torow_plywackich   INTEGER NOT NULL,
    miasto                    VARCHAR2(4000),
    ulica                     VARCHAR2(4000)
);

ALTER TABLE baseny ADD CONSTRAINT baseny_pk PRIMARY KEY ( numer_obiektu );

-----------------------------------------------------Dane do logowania----------------------------------------------------------
CREATE TABLE dane_do_logowania (
    id                            INTEGER NOT NULL,
    login                         VARCHAR2(4000) NOT NULL,
    haslo                         VARCHAR2(4000) NOT NULL,
    osoby_numer_identyfikacyjny   INTEGER NOT NULL
);

CREATE UNIQUE INDEX dane_do_logowania__idx ON
    dane_do_logowania (
        osoby_numer_identyfikacyjny
    ASC );

ALTER TABLE dane_do_logowania ADD CONSTRAINT dane_do_logowania_pk PRIMARY KEY ( id );

----------------------------------------------------------Klienci---------------------------------------------------------------
CREATE TABLE klienci (
    numer_klienta    INTEGER NOT NULL,
    imie             VARCHAR2(4000) NOT NULL,
    nazwisko         VARCHAR2(4000) NOT NULL,
    numer_telefonu   VARCHAR2(4000) NOT NULL,
    "Adres_e-mail"   VARCHAR2(4000)
);

ALTER TABLE klienci ADD CONSTRAINT klienci_pk PRIMARY KEY ( numer_klienta );

----------------------------------------------------------Koszyki---------------------------------------------------------------
CREATE TABLE koszyki (
    transakcje_numer_transakcji   INTEGER NOT NULL,
    uslugi_numer_uslugi           INTEGER NOT NULL,
    ilosc                         INTEGER NOT NULL
);

ALTER TABLE koszyki ADD CONSTRAINT koszyki_pk PRIMARY KEY ( uslugi_numer_uslugi,
                                                            transakcje_numer_transakcji );

-----------------------------------------------------Lekcje plywania------------------------------------------------------------
CREATE TABLE lekcje_plywania (
    numer_lekcji             INTEGER NOT NULL,
    data_i_godzina           DATE NOT NULL,
    numer_ratownika          INTEGER NOT NULL,
    ogolne_numer_uslugi      INTEGER NOT NULL,
    liczba_zapisanych_osob   INTEGER NOT NULL
);

ALTER TABLE lekcje_plywania ADD CONSTRAINT lekcje_plywania_pk PRIMARY KEY ( numer_lekcji );

---------------------------------------------------------Lokalne----------------------------------------------------------------
CREATE TABLE lokalne (
    numer_uslugi   INTEGER NOT NULL
);

ALTER TABLE lokalne ADD CONSTRAINT lokalne_pk PRIMARY KEY ( numer_uslugi );

---------------------------------------------------------Ogolne-----------------------------------------------------------------
CREATE TABLE ogolne (
    numer_uslugi   INTEGER NOT NULL
);

ALTER TABLE ogolne ADD CONSTRAINT ogolne_pk PRIMARY KEY ( numer_uslugi );

---------------------------------------------------------Osoby------------------------------------------------------------------
CREATE TABLE osoby (
    numer_identyfikacyjny   INTEGER NOT NULL,
    audytorzy_numer_id      INTEGER,
    wlasciciele_numer_id    INTEGER,
    pracownicy_numer_id     INTEGER
);

ALTER TABLE osoby
    ADD CONSTRAINT fkarc_1 CHECK ( ( ( audytorzy_numer_id IS NOT NULL )
                                     AND ( wlasciciele_numer_id IS NULL )
                                     AND ( pracownicy_numer_id IS NULL ) )
                                   OR ( ( wlasciciele_numer_id IS NOT NULL )
                                        AND ( audytorzy_numer_id IS NULL )
                                        AND ( pracownicy_numer_id IS NULL ) )
                                   OR ( ( pracownicy_numer_id IS NOT NULL )
                                        AND ( audytorzy_numer_id IS NULL )
                                        AND ( wlasciciele_numer_id IS NULL ) ) );

ALTER TABLE osoby ADD CONSTRAINT osoby_pk PRIMARY KEY ( numer_identyfikacyjny );

------------------------------------------------------Pracownicy----------------------------------------------------------------
CREATE TABLE pracownicy (
    numer_identyfikacyjny         INTEGER NOT NULL,
    imie                          VARCHAR2(4000) NOT NULL,
    nazwisko                      VARCHAR2(4000) NOT NULL,
    dodatek_do_pensji             INTEGER,
    stanowiska_numer_stanowiska   INTEGER NOT NULL,
    baseny_numer_obiektu          INTEGER NOT NULL
);

ALTER TABLE pracownicy ADD CONSTRAINT pracownicy_pk PRIMARY KEY ( numer_identyfikacyjny );

------------------------------------------------------Przeglady-----------------------------------------------------------------
CREATE TABLE przeglady (
    numer_przegladu        INTEGER NOT NULL,
    data                   DATE NOT NULL,
    baseny_numer_obiektu   INTEGER NOT NULL
);

ALTER TABLE przeglady ADD CONSTRAINT przeglady_pk PRIMARY KEY ( numer_przegladu );

---------------------------------------------------Rezerwacje toru--------------------------------------------------------------
CREATE TABLE rezerwacje_toru (
    numer_rezerwacji        INTEGER NOT NULL,
    data_i_godzina          DATE NOT NULL,
    numer_toru              INTEGER NOT NULL,
    status                  CHAR(1) NOT NULL,
    klienci_numer_klienta   INTEGER NOT NULL,
    ogolne_numer_uslugi     INTEGER NOT NULL
);

ALTER TABLE rezerwacje_toru ADD CONSTRAINT rezerwacje_toru_pk PRIMARY KEY ( numer_rezerwacji );

------------------------------------------------------Stanowiska----------------------------------------------------------------
CREATE TABLE stanowiska (
    numer_stanowiska   INTEGER NOT NULL,
    nazwa              VARCHAR2(4000) NOT NULL,
    wynagrodzenie      INTEGER NOT NULL
);

ALTER TABLE stanowiska ADD CONSTRAINT stanowiska_pk PRIMARY KEY ( numer_stanowiska );

------------------------------------------------------Transakcje----------------------------------------------------------------
CREATE TABLE transakcje (
    numer_transakcji   INTEGER NOT NULL,
    data               DATE NOT NULL
);

ALTER TABLE transakcje ADD CONSTRAINT transakcje_pk PRIMARY KEY ( numer_transakcji );

---------------------------------------------------Uczestnicy lekcji------------------------------------------------------------
CREATE TABLE uczestnicy_lekcji (
    klienci_numer_klienta          INTEGER NOT NULL,
    lekcje_plywania_numer_lekcji   INTEGER NOT NULL
);

ALTER TABLE uczestnicy_lekcji ADD CONSTRAINT uczestnicy_lekcji_pk PRIMARY KEY ( klienci_numer_klienta,
                                                                                lekcje_plywania_numer_lekcji );

---------------------------------------------------------Uslugi-----------------------------------------------------------------
CREATE TABLE uslugi (
    numer_uslugi           INTEGER NOT NULL,
    nazwa_uslugi           VARCHAR2(4000) NOT NULL,
    cena                   INTEGER NOT NULL,
    baseny_numer_obiektu   INTEGER NOT NULL,
    ogolne_numer_uslugi    INTEGER,
    lokalne_numer_uslugi   INTEGER
);

ALTER TABLE uslugi
    ADD CONSTRAINT fkarc_2 CHECK ( ( ( ogolne_numer_uslugi IS NOT NULL )
                                     AND ( lokalne_numer_uslugi IS NULL ) )
                                   OR ( ( lokalne_numer_uslugi IS NOT NULL )
                                        AND ( ogolne_numer_uslugi IS NULL ) ) );

ALTER TABLE uslugi ADD CONSTRAINT uslugi_pk PRIMARY KEY ( numer_uslugi );

------------------------------------------------------Wlasciciele---------------------------------------------------------------
CREATE TABLE wlasciciele (
    numer_identyfikacyjny   INTEGER NOT NULL,
    imie                    VARCHAR2(4000) NOT NULL,
    nazwisko                VARCHAR2(4000) NOT NULL
);

ALTER TABLE wlasciciele ADD CONSTRAINT wlasciciele_pk PRIMARY KEY ( numer_identyfikacyjny );

----========================================================================================================================----
----===-----------------------------------------Dodawanie kluczy obcych--------------------------------------------------===----
----========================================================================================================================----
ALTER TABLE dane_do_logowania
    ADD CONSTRAINT dane_do_logowania_osoby_fk FOREIGN KEY ( osoby_numer_identyfikacyjny )
        REFERENCES osoby ( numer_identyfikacyjny );

ALTER TABLE koszyki
    ADD CONSTRAINT koszyki_transakcje_fk FOREIGN KEY ( transakcje_numer_transakcji )
        REFERENCES transakcje ( numer_transakcji );

ALTER TABLE koszyki
    ADD CONSTRAINT koszyki_uslugi_fk FOREIGN KEY ( uslugi_numer_uslugi )
        REFERENCES uslugi ( numer_uslugi );

ALTER TABLE lekcje_plywania
    ADD CONSTRAINT lekcje_plywania_ogolne_fk FOREIGN KEY ( ogolne_numer_uslugi )
        REFERENCES ogolne ( numer_uslugi );

ALTER TABLE osoby
    ADD CONSTRAINT osoby_audytorzy_fk FOREIGN KEY ( audytorzy_numer_id )
        REFERENCES audytorzy ( numer_identyfikacyjny );

ALTER TABLE osoby
    ADD CONSTRAINT osoby_pracownicy_fk FOREIGN KEY ( pracownicy_numer_id )
        REFERENCES pracownicy ( numer_identyfikacyjny );

ALTER TABLE osoby
    ADD CONSTRAINT osoby_wlasciciele_fk FOREIGN KEY ( wlasciciele_numer_id )
        REFERENCES wlasciciele ( numer_identyfikacyjny );

ALTER TABLE pracownicy
    ADD CONSTRAINT pracownicy_baseny_fk FOREIGN KEY ( baseny_numer_obiektu )
        REFERENCES baseny ( numer_obiektu );

ALTER TABLE pracownicy
    ADD CONSTRAINT pracownicy_stanowiska_fk FOREIGN KEY ( stanowiska_numer_stanowiska )
        REFERENCES stanowiska ( numer_stanowiska );

ALTER TABLE przeglady
    ADD CONSTRAINT przeglady_baseny_fk FOREIGN KEY ( baseny_numer_obiektu )
        REFERENCES baseny ( numer_obiektu );

ALTER TABLE rezerwacje_toru
    ADD CONSTRAINT rezerwacje_toru_klienci_fk FOREIGN KEY ( klienci_numer_klienta )
        REFERENCES klienci ( numer_klienta );

ALTER TABLE rezerwacje_toru
    ADD CONSTRAINT rezerwacje_toru_ogolne_fk FOREIGN KEY ( ogolne_numer_uslugi )
        REFERENCES ogolne ( numer_uslugi );

ALTER TABLE uczestnicy_lekcji
    ADD CONSTRAINT uczestnicy_lekcji_klienci_fk FOREIGN KEY ( klienci_numer_klienta )
        REFERENCES klienci ( numer_klienta );

ALTER TABLE uczestnicy_lekcji
    ADD CONSTRAINT uczestnicy_lekcji_lekcje_fk FOREIGN KEY ( lekcje_plywania_numer_lekcji )
        REFERENCES lekcje_plywania ( numer_lekcji );

ALTER TABLE uslugi
    ADD CONSTRAINT uslugi_baseny_fk FOREIGN KEY ( baseny_numer_obiektu )
        REFERENCES baseny ( numer_obiektu );

ALTER TABLE uslugi
    ADD CONSTRAINT uslugi_lokalne_fk FOREIGN KEY ( lokalne_numer_uslugi )
        REFERENCES lokalne ( numer_uslugi );

ALTER TABLE uslugi
    ADD CONSTRAINT uslugi_ogolne_fk FOREIGN KEY ( ogolne_numer_uslugi )
        REFERENCES ogolne ( numer_uslugi );
