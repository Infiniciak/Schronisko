#Projekt Schronisko
#Opis Projektu
Projekt pozwala na zarządzanie RDB za pomocą GUI.Do stworzenia użyto JavaFx i Hibernate. Celem projektu jest umożliwienie studentom nauki tworzenia oprogramowania z GUI oraz obsługi relacyjnych baz danych.

#Funkcjonalności

Dodawanie rekordów do tabel bazy danych
Usuwanie  rekordów z tabel bazy danych
Odczytywanie i operowanie na rekordach z tabel bazy danych
Edytowanie wybranych rekordów z tabel bazy danych
Ukazanie danych tabeli
Czyszczenie wpisywanych pól

Baza danych PostgreSQL składa się z trzech tabel:

zwierzaki:

CREATE TABLE zwierzaki (
    idzwierzaka INT PRIMARY KEY,
    rasa VARCHAR(20),
    gatunek VARCHAR(20),
    plec VARCHAR(20),
    wiek INT,
    dataprzyjecia DATE
);
szczepienia

CREATE TABLE szczepienia (
    idszczepienia INT PRIMARY KEY,
    rodzaj_szczepienia VARCHAR(20),
    data_pierwszego_szczepienia DATE,
    data_ostatniego_szczepienia DATE,
    idzwierzaka INT,
    FOREIGN KEY (idzwierzaka) REFERENCES zwierzaki(idzwierzaka)
);
adoptujacy

CREATE TABLE zwierzaki (
    idzwierzaka INT PRIMARY KEY,
    rasa VARCHAR(20),
    gatunek VARCHAR(20),
    plec VARCHAR(20),
    wiek INT,
    dataprzyjecia DATE
);

CREATE TABLE adoptujacy(
    idosoby INT PRIMARY KEY,
    imie VARCHAR(20),
    nazwisko VARCHAR(20),
    idzwierzaka INT,
    dataadopcji DATE,
    FOREIGN KEY (idzwierzaka) REFERENCES zwierzaki(idzwierzaka)
);

Przykładowe dane
INSERT INTO zwierzaki(rasa,gatunek,plec,wiek,dataprzyjecia) VALUES
('Owczarek niemiecki', 'Pies', 'Samiec',5,2023-06-19),
('Rottweiler', 'Pies', 'Samica',3,2020-02-14),
('Kot syjamski', 'Kot', 'Samica',2,2021-09-20),
('Kot perski', 'Kot', 'Samiec',1,2024-04-05);

INSERT INTO szczepienia (rodzaj_szczepienia,data_pierwszego_szczepienia,data_ostatniego_szczepienia) VALUES
('Wscieklizna', 2023-06-19, 2023-12-19),
('Cyklowe', 2020-02-14, 2020-08-14),
('Zywe', 2021-09-20, 2022-03-20);

INSERT INTO adoptujacy (imie,nazwisko,dataadopcji) VALUES
('Jan', 'Kowalski', 3, 2023-12-10),
('Anna', 'Nowak', 1, 2023-12-19),
('Zdzislaw', 'Norek', 2, 2022-03-20);



Autorzy
Bartosz Winczowski, Emilia Szczęch,Nadia Schiffer,Filip Ślemp
