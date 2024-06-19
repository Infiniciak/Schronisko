Projekt Schronisko
Opis Projektu
Projekt pozwala na zarządzanie RDB za pomocą GUI.Do stworzenia użyto JavaFx i Hibernate. Celem projektu jest umożliwienie studentom nauki tworzenia oprogramowania z GUI oraz obsługi relacyjnych baz danych.

Funkcjonalności
Aplikacja schronisko daje możliwość:

Dodawanie rekordów do tabel bazy danych
Usuwanie  rekordów z tabel bazy danych
Odczytywanie i operowanie na rekordach z tabel bazy danych
Edytowanie wybranych rekordów z tabel bazy danych
Ukazanie danych tabeli
Czyszczenie wpisywanych pól

Baza danych PostgreSQL składa się z trzech tabel:

zwierzaki:

CREATE TABLE car_rental.clients (
    IDClient SERIAL PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    Email VARCHAR(100)
);
szczepienia

CREATE TABLE car_rental.engine (
    IDEngine SERIAL PRIMARY KEY,
    Name VARCHAR(50),
 Power INT,
    FuelType VARCHAR(50)
);
adoptujacy

CREATE TABLE car_rental.cars (
    IDCar SERIAL PRIMARY KEY,
    Brand VARCHAR(50),
    Model VARCHAR(50),
    IDEngine INT,
    Price DECIMAL(9,2),
    FOREIGN KEY (IDEngine) REFERENCES car_rental.engine(IDEngine)
);

Przykładowe dane
INSERT INTO car_rental.clients (FirstName, LastName, Email) VALUES
('John', 'Smith', 'john@example.com'),
('Alice', 'Johnson', 'alice@example.com'),
('Emma', 'Davis', 'emma@example.com'),
('Michael', 'Brown', 'michael@example.com');

INSERT INTO car_rental.engine (Name, Power, FuelType) VALUES
('V8', 400, 'Petrol'),
('V6', 300, 'Diesel'),
('I4', 150, 'Electric');

INSERT INTO car_rental.cars (Brand, Model, IDEngine, Price) VALUES
('Toyota', 'Corolla', 3, 25000),
('Ford', 'Mustang', 1, 45000),
('BMW', 'X5', 2, 60000);

INSERT INTO car_rental.rented_car (IDClient, IDCar, Rented_Date, Rented_From, Rented_Until) VALUES
(1, 1, '2024-06-10', '2024-06-10', '2024-06-12'),
(2, 2, '2024-06-11', '2024-06-11', '2024-06-15');


Autorzy
Bartosz Winczowski, Emilia Szczęch,Nadia Schiffer,Filip Ślemp
