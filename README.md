# Projekt Schronisko
Projekt pozwala na zarządzanie RDB za pomocą GUI.Do stworzenia użyto JavaFx i Hibernate. Celem projektu jest umożliwienie studentom nauki tworzenia oprogramowania z GUI oraz obsługi relacyjnych baz danych.

# Funkcjonalności

Dodawanie rekordów do tabel bazy danych
Usuwanie  rekordów z tabel bazy danych
Odczytywanie i operowanie na rekordach z tabel bazy danych
Edytowanie wybranych rekordów z tabel bazy danych
Ukazanie danych tabeli
Czyszczenie wpisywanych pól

# Baza danych PostgreSQL składa się z trzech tabel:

zwierzaki:
```
CREATE TABLE zwierzaki (
    idzwierzaka INT PRIMARY KEY,
    rasa VARCHAR(20),
    gatunek VARCHAR(20),
    plec VARCHAR(20),
    wiek INT,
    dataprzyjecia VARCHAR(20)
);
```
szczepienia
```
CREATE TABLE szczepienia (
    idszczepienia INT PRIMARY KEY,
    rodzaj_szczepienia VARCHAR(20),
    data_pierwszego_szczepienia DATE,
    data_ostatniego_szczepienia DATE,
    id__zwierzaka INT,
    FOREIGN KEY (idzwierzaka) REFERENCES zwierzaki(idzwierzaka)
);
```
adoptujacy
```
CREATE TABLE adoptujacy(
    idosoby INT PRIMARY KEY,
    imie VARCHAR(20),
    nazwisko VARCHAR(20),
    id_zwierzaka INT,
    dataadopcji DATE,
    FOREIGN KEY (idzwierzaka) REFERENCES zwierzaki(idzwierzaka)
);
```


# Autorzy
| Osoba | Zakres obowiązków |
| --- | --- |
| Bartosz Winczowski | Stworzenie konfiguracji Hibernate,mapowanie Hibernate,Klasy,Repozytoria,Kontrolery,Dokumentacja |
| Emilia Szczęch | Stworzenie bazy danych,Kontrolery,Dokumentacja |
| Nadia Schiffer | GUI |
| Filip Ślemp | GUI,Kontrolery,Dokumentacja |
