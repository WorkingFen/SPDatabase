----========================================================================================================================----
----===-----------------------------------------Tworzenie przestrzeni tabel----------------------------------------------===----
----========================================================================================================================----

CREATE TABLESPACE "2017_1" DATAFILE
    '2017_1_file.dbf' SIZE 100M REUSE
LOGGING ONLINE EXTENT MANAGEMENT LOCAL AUTOALLOCATE FLASHBACK ON;

CREATE TABLESPACE "2017_2" DATAFILE
    '2017_2_file.dbf' SIZE 100M REUSE
LOGGING ONLINE EXTENT MANAGEMENT LOCAL AUTOALLOCATE FLASHBACK ON;

CREATE TABLESPACE "2017_3" DATAFILE
    '2017_3_file.dbf' SIZE 100M REUSE
LOGGING ONLINE EXTENT MANAGEMENT LOCAL AUTOALLOCATE FLASHBACK ON;

CREATE TABLESPACE "2017_4" DATAFILE
    '2017_4_file.dbf' SIZE 100M REUSE
LOGGING ONLINE EXTENT MANAGEMENT LOCAL AUTOALLOCATE FLASHBACK ON;

CREATE TABLESPACE "2018_1" DATAFILE
    '2018_1_file.dbf' SIZE 100M REUSE
LOGGING ONLINE EXTENT MANAGEMENT LOCAL AUTOALLOCATE FLASHBACK ON;

CREATE TABLESPACE "2018_2" DATAFILE
    '2018_2_file.dbf' SIZE 100M REUSE
LOGGING ONLINE EXTENT MANAGEMENT LOCAL AUTOALLOCATE FLASHBACK ON;

CREATE TABLESPACE "2018_3" DATAFILE
    '2018_3_file.dbf' SIZE 100M REUSE
LOGGING ONLINE EXTENT MANAGEMENT LOCAL AUTOALLOCATE FLASHBACK ON;

CREATE TABLESPACE "2018_4" DATAFILE
    '2018_4_file.dbf' SIZE 100M REUSE
LOGGING ONLINE EXTENT MANAGEMENT LOCAL AUTOALLOCATE FLASHBACK ON;

----========================================================================================================================----
----===-----------------------------------------------Tworzenie tabel----------------------------------------------------===----
----========================================================================================================================----

---------------------------------------------------------Audytorzy--------------------------------------------------------------
CREATE TABLE audytorzy (
    numer_identyfikacyjny   INTEGER NOT NULL
)
LOGGING;

ALTER TABLE audytorzy ADD CONSTRAINT audytorzy_pk PRIMARY KEY ( numer_identyfikacyjny );

----------------------------------------------------------Baseny----------------------------------------------------------------
CREATE TABLE baseny (
    numer_obiektu             INTEGER NOT NULL,
    nazwa_obiektu             VARCHAR2(4000) NOT NULL,
    liczba_torow_plywackich   INTEGER NOT NULL,
    miasto                    VARCHAR2(4000),
    ulica                     VARCHAR2(4000)
)
LOGGING;

ALTER TABLE baseny ADD CONSTRAINT baseny_pk PRIMARY KEY ( numer_obiektu );

-----------------------------------------------------Dane do logowania----------------------------------------------------------
CREATE TABLE dane_do_logowania (
    id                            INTEGER NOT NULL,
    login                         VARCHAR2(4000) NOT NULL,
    haslo                         VARCHAR2(4000) NOT NULL,
    osoby_numer_identyfikacyjny   INTEGER NOT NULL
)
LOGGING;

CREATE UNIQUE INDEX dane_do_logowania__idx ON
    dane_do_logowania (
        osoby_numer_identyfikacyjny
    ASC )
        LOGGING;

ALTER TABLE dane_do_logowania ADD CONSTRAINT dane_do_logowania_pk PRIMARY KEY ( id );

----------------------------------------------------------Klienci---------------------------------------------------------------
CREATE TABLE klienci (
    numer_klienta    INTEGER NOT NULL,
    imie             VARCHAR2(4000) NOT NULL,
    nazwisko         VARCHAR2(4000) NOT NULL,
    numer_telefonu   VARCHAR2(4000) NOT NULL,
    "Adres_e-mail"   VARCHAR2(4000)
)
LOGGING;

ALTER TABLE klienci ADD CONSTRAINT klienci_pk PRIMARY KEY ( numer_klienta );

----------------------------------------------------------Koszyki---------------------------------------------------------------
CREATE TABLE koszyki (
    transakcje_numer_transakcji   INTEGER NOT NULL,
    uslugi_numer_uslugi           INTEGER NOT NULL,
    ilosc                         INTEGER NOT NULL
)
LOGGING;

CREATE INDEX koszyki__idx ON
    koszyki (
        transakcje_numer_transakcji
    ASC )
        LOGGING;

ALTER TABLE koszyki ADD CONSTRAINT koszyki_pk PRIMARY KEY ( uslugi_numer_uslugi,
                                                            transakcje_numer_transakcji );

-----------------------------------------------------Lekcje plywania------------------------------------------------------------
CREATE TABLE lekcje_plywania (
    numer_lekcji             INTEGER NOT NULL,
    data_i_godzina           DATE NOT NULL,
    numer_ratownika          INTEGER NOT NULL,
    ogolne_numer_uslugi      INTEGER NOT NULL,
    liczba_zapisanych_osob   INTEGER NOT NULL
)
LOGGING   --   --   --   --   --   --   --   --   --   -Partycje  --   --   --   --   --   --   --   --   --   --   --   --   --
    PARTITION BY RANGE (
        data_i_godzina
    )
    ( PARTITION partition1
        VALUES LESS THAN (TO_DATE('2017/03/31', 'YYYY/MM/DD'))
    TABLESPACE "2017_1",
    PARTITION partition2
        VALUES LESS THAN (TO_DATE('2017/06/30', 'YYYY/MM/DD'))
    TABLESPACE "2017_2",
    PARTITION partition3
        VALUES LESS THAN (TO_DATE('2017/09/30', 'YYYY/MM/DD'))
    TABLESPACE "2017_3",
    PARTITION partition4
        VALUES LESS THAN (TO_DATE('2017/12/31', 'YYYY/MM/DD'))
    TABLESPACE "2017_4",
    PARTITION partition5
        VALUES LESS THAN (TO_DATE('2018/03/31', 'YYYY/MM/DD'))
    TABLESPACE "2018_1",
    PARTITION partition6
        VALUES LESS THAN (TO_DATE('2018/06/30', 'YYYY/MM/DD'))
    TABLESPACE "2018_2",
    PARTITION partition7
        VALUES LESS THAN (TO_DATE('2018/09/30', 'YYYY/MM/DD'))
    TABLESPACE "2018_3",
    PARTITION partition8
        VALUES LESS THAN (TO_DATE('2018/12/31', 'YYYY/MM/DD'))
    TABLESPACE "2018_4" );

ALTER TABLE lekcje_plywania ADD CONSTRAINT lekcje_plywania_pk PRIMARY KEY ( numer_lekcji );

---------------------------------------------------------Lokalne----------------------------------------------------------------
CREATE TABLE lokalne (
    numer_uslugi   INTEGER NOT NULL
)
LOGGING;

ALTER TABLE lokalne ADD CONSTRAINT lokalne_pk PRIMARY KEY ( numer_uslugi );

---------------------------------------------------------Ogolne-----------------------------------------------------------------
CREATE TABLE ogolne (
    numer_uslugi   INTEGER NOT NULL
)
LOGGING;

ALTER TABLE ogolne ADD CONSTRAINT ogolne_pk PRIMARY KEY ( numer_uslugi );

---------------------------------------------------------Osoby------------------------------------------------------------------
CREATE TABLE osoby (
    numer_identyfikacyjny   INTEGER NOT NULL,
    audytorzy_numer_id      INTEGER,
    wlasciciele_numer_id    INTEGER,
    pracownicy_numer_id     INTEGER
)
LOGGING;

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
)
LOGGING;

ALTER TABLE pracownicy ADD CONSTRAINT pracownicy_pk PRIMARY KEY ( numer_identyfikacyjny );

------------------------------------------------------Przeglady-----------------------------------------------------------------
CREATE TABLE przeglady (
    numer_przegladu        INTEGER NOT NULL,
    data                   DATE NOT NULL,
    baseny_numer_obiektu   INTEGER NOT NULL
)
LOGGING;

ALTER TABLE przeglady ADD CONSTRAINT przeglady_pk PRIMARY KEY ( numer_przegladu );

---------------------------------------------------Rezerwacje toru--------------------------------------------------------------
CREATE TABLE rezerwacje_toru (
    numer_rezerwacji        INTEGER NOT NULL,
    data_i_godzina          DATE NOT NULL,
    numer_toru              INTEGER NOT NULL,
    status                  NUMBER NOT NULL,
    klienci_numer_klienta   INTEGER NOT NULL,
    ogolne_numer_uslugi     INTEGER NOT NULL
)
LOGGING   --   --   --   --   --   --   --   --   --   -Partycje  --   --   --   --   --   --   --   --   --   --   --   --   --
    PARTITION BY RANGE (
        data_i_godzina
    )
    ( PARTITION partition1
        VALUES LESS THAN (TO_DATE('2017/03/31', 'YYYY/MM/DD'))
    TABLESPACE "2017_1",
    PARTITION partition2
        VALUES LESS THAN (TO_DATE('2017/06/30', 'YYYY/MM/DD'))
    TABLESPACE "2017_2",
    PARTITION partition3
        VALUES LESS THAN (TO_DATE('2017/09/30', 'YYYY/MM/DD'))
    TABLESPACE "2017_3",
    PARTITION partition4
        VALUES LESS THAN (TO_DATE('2017/12/31', 'YYYY/MM/DD'))
    TABLESPACE "2017_4",
    PARTITION partition5
        VALUES LESS THAN (TO_DATE('2018/03/31', 'YYYY/MM/DD'))
    TABLESPACE "2018_1",
    PARTITION partition6
        VALUES LESS THAN (TO_DATE('2018/06/30', 'YYYY/MM/DD'))
    TABLESPACE "2018_2",
    PARTITION partition7
        VALUES LESS THAN (TO_DATE('2018/09/30', 'YYYY/MM/DD'))
    TABLESPACE "2018_3",
    PARTITION partition8
        VALUES LESS THAN (TO_DATE('2018/12/31', 'YYYY/MM/DD'))
    TABLESPACE "2018_4" );

ALTER TABLE rezerwacje_toru ADD CONSTRAINT rezerwacje_toru_pk PRIMARY KEY ( numer_rezerwacji );

------------------------------------------------------Stanowiska----------------------------------------------------------------
CREATE TABLE stanowiska (
    numer_stanowiska   INTEGER NOT NULL,
    nazwa              VARCHAR2(4000) NOT NULL,
    wynagrodzenie      INTEGER NOT NULL
)
LOGGING;

ALTER TABLE stanowiska ADD CONSTRAINT stanowiska_pk PRIMARY KEY ( numer_stanowiska );

------------------------------------------------------Transakcje----------------------------------------------------------------
CREATE TABLE transakcje (
    numer_transakcji   INTEGER NOT NULL,
    data               DATE NOT NULL
)
LOGGING   --   --   --   --   --   --   --   --   --   -Partycje  --   --   --   --   --   --   --   --   --   --   --   --   --
    PARTITION BY RANGE (
        data
    )
    ( PARTITION partition1
        VALUES LESS THAN (TO_DATE('2017/03/31', 'YYYY/MM/DD'))
    TABLESPACE "2017_1",
    PARTITION partition2
        VALUES LESS THAN (TO_DATE('2017/06/30', 'YYYY/MM/DD'))
    TABLESPACE "2017_2",
    PARTITION partition3
        VALUES LESS THAN (TO_DATE('2017/09/30', 'YYYY/MM/DD'))
    TABLESPACE "2017_3",
    PARTITION partition4
        VALUES LESS THAN (TO_DATE('2017/12/31', 'YYYY/MM/DD'))
    TABLESPACE "2017_4",
    PARTITION partition5
        VALUES LESS THAN (TO_DATE('2018/03/31', 'YYYY/MM/DD'))
    TABLESPACE "2018_1",
    PARTITION partition6
        VALUES LESS THAN (TO_DATE('2018/06/30', 'YYYY/MM/DD'))
    TABLESPACE "2018_2",
    PARTITION partition7
        VALUES LESS THAN (TO_DATE('2018/09/30', 'YYYY/MM/DD'))
    TABLESPACE "2018_3",
    PARTITION partition8
        VALUES LESS THAN (TO_DATE('2018/12/31', 'YYYY/MM/DD'))
    TABLESPACE "2018_4" );

ALTER TABLE transakcje ADD CONSTRAINT transakcje_pk PRIMARY KEY ( numer_transakcji );

---------------------------------------------------Uczestnicy lekcji------------------------------------------------------------
CREATE TABLE uczestnicy_lekcji (
    klienci_numer_klienta          INTEGER NOT NULL,
    lekcje_plywania_numer_lekcji   INTEGER NOT NULL
)
LOGGING;

CREATE INDEX uczestnicy_lekcji__idx ON
    uczestnicy_lekcji (
        lekcje_plywania_numer_lekcji
    ASC )
        LOGGING;

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
)
LOGGING;

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
)
LOGGING;

ALTER TABLE wlasciciele ADD CONSTRAINT wlasciciele_pk PRIMARY KEY ( numer_identyfikacyjny );

----========================================================================================================================----
----===-----------------------------------------Dodawanie kluczy obcych--------------------------------------------------===----
----========================================================================================================================----
ALTER TABLE dane_do_logowania
    ADD CONSTRAINT dane_do_logowania_osoby_fk FOREIGN KEY ( osoby_numer_identyfikacyjny )
        REFERENCES osoby ( numer_identyfikacyjny )
    NOT DEFERRABLE;

ALTER TABLE koszyki
    ADD CONSTRAINT koszyki_transakcje_fk FOREIGN KEY ( transakcje_numer_transakcji )
        REFERENCES transakcje ( numer_transakcji )
    NOT DEFERRABLE;

ALTER TABLE koszyki
    ADD CONSTRAINT koszyki_uslugi_fk FOREIGN KEY ( uslugi_numer_uslugi )
        REFERENCES uslugi ( numer_uslugi )
    NOT DEFERRABLE;

ALTER TABLE lekcje_plywania
    ADD CONSTRAINT lekcje_plywania_ogolne_fk FOREIGN KEY ( ogolne_numer_uslugi )
        REFERENCES ogolne ( numer_uslugi )
    NOT DEFERRABLE;

ALTER TABLE osoby
    ADD CONSTRAINT osoby_audytorzy_fk FOREIGN KEY ( audytorzy_numer_id )
        REFERENCES audytorzy ( numer_identyfikacyjny )
    NOT DEFERRABLE;

ALTER TABLE osoby
    ADD CONSTRAINT osoby_pracownicy_fk FOREIGN KEY ( pracownicy_numer_id )
        REFERENCES pracownicy ( numer_identyfikacyjny )
    NOT DEFERRABLE;

ALTER TABLE osoby
    ADD CONSTRAINT osoby_wlasciciele_fk FOREIGN KEY ( wlasciciele_numer_id )
        REFERENCES wlasciciele ( numer_identyfikacyjny )
    NOT DEFERRABLE;

ALTER TABLE pracownicy
    ADD CONSTRAINT pracownicy_baseny_fk FOREIGN KEY ( baseny_numer_obiektu )
        REFERENCES baseny ( numer_obiektu )
    NOT DEFERRABLE;

ALTER TABLE pracownicy
    ADD CONSTRAINT pracownicy_stanowiska_fk FOREIGN KEY ( stanowiska_numer_stanowiska )
        REFERENCES stanowiska ( numer_stanowiska )
    NOT DEFERRABLE;

ALTER TABLE przeglady
    ADD CONSTRAINT przeglady_baseny_fk FOREIGN KEY ( baseny_numer_obiektu )
        REFERENCES baseny ( numer_obiektu )
    NOT DEFERRABLE;

ALTER TABLE rezerwacje_toru
    ADD CONSTRAINT rezerwacje_toru_klienci_fk FOREIGN KEY ( klienci_numer_klienta )
        REFERENCES klienci ( numer_klienta )
    NOT DEFERRABLE;

ALTER TABLE rezerwacje_toru
    ADD CONSTRAINT rezerwacje_toru_ogolne_fk FOREIGN KEY ( ogolne_numer_uslugi )
        REFERENCES ogolne ( numer_uslugi )
    NOT DEFERRABLE;

ALTER TABLE uczestnicy_lekcji
    ADD CONSTRAINT uczestnicy_lekcji_klienci_fk FOREIGN KEY ( klienci_numer_klienta )
        REFERENCES klienci ( numer_klienta )
    NOT DEFERRABLE;

ALTER TABLE uczestnicy_lekcji
    ADD CONSTRAINT uczestnicy_lekcji_lekcje_fk FOREIGN KEY ( lekcje_plywania_numer_lekcji )
        REFERENCES lekcje_plywania ( numer_lekcji )
    NOT DEFERRABLE;

ALTER TABLE uslugi
    ADD CONSTRAINT uslugi_baseny_fk FOREIGN KEY ( baseny_numer_obiektu )
        REFERENCES baseny ( numer_obiektu )
    NOT DEFERRABLE;

ALTER TABLE uslugi
    ADD CONSTRAINT uslugi_lokalne_fk FOREIGN KEY ( lokalne_numer_uslugi )
        REFERENCES lokalne ( numer_uslugi )
    NOT DEFERRABLE;

ALTER TABLE uslugi
    ADD CONSTRAINT uslugi_ogolne_fk FOREIGN KEY ( ogolne_numer_uslugi )
        REFERENCES ogolne ( numer_uslugi )
    NOT DEFERRABLE;
