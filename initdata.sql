INSERT INTO Airport (airportId, airportName, nation, location)
VALUES
    (101, 'Heathrow Airport', 'England', 'London'),
    (102, 'John F. Kennedy International Airport', 'USA', 'New York'),
    (103, 'Los Angeles International Airport', 'USA', 'Los Angeles'),
    (104, 'Tokyo Haneda Airport', 'Japan', 'Tokyo'),
    (105, 'Beijing Capital International Airport', 'China', 'Beijing'),
    (106, 'Sydney Kingsford Smith Airport', 'Australia', 'Sydney'),
    (107, 'Toronto Pearson International Airport', 'Canada', 'Toronto'),
    (108, 'Charles de Gaulle Airport', 'France', 'Paris'),
    (109, 'Frankfurt Airport', 'Germany', 'Frankfurt'),
    (110, 'Amsterdam Schiphol Airport', 'Netherlands', 'Amsterdam'),
    (111, 'Incheon International Airport', 'South Korea', 'Incheon');

INSERT INTO Airline (airlineId, airlineName)
VALUES
    (201, 'British Airways'),
    (202, 'American Airlines'),
    (203, 'Japan Airlines'),
    (204, 'Air China'),
    (205, 'Qantas'),
    (206, 'Air Canada'),
    (207, 'Air France'),
    (208, 'Lufthansa'),
    (209, 'KLM Royal Dutch Airlines'),
    (210, 'Korean Air'),
    (211, 'Asiana Airlines');

INSERT INTO Aircraft (aircraftId, airlineId, model, capacity)
VALUES
    (301, 201, 'Boeing 747', 400),
    (302, 202, 'Airbus A320', 180),
    (303, 203, 'Boeing 777', 350),
    (304, 204, 'Airbus A330', 300),
    (305, 205, 'Boeing 787', 250),
    (306, 206, 'Airbus A380', 500),
    (307, 207, 'Boeing 737', 200),
    (308, 208, 'Airbus A350', 440),
    (309, 209, 'Boeing 767', 280),
    (310, 210, 'Boeing 777', 350),
    (311, 211, 'Airbus A330', 300);

INSERT INTO Flight (flightId, departureAirportId, arrivalAirportId, aircraftId, departureTime, arrivalTime, airline, flightPrice)
VALUES
    ('BA401', 101, 102, 301, '2024-06-01 10:00:00', '2024-06-01 14:00:00', 'British Airways', 540000),
    ('AA402', 102, 103, 302, '2024-06-05 08:00:00', '2024-06-05 12:00:00', 'American Airlines', 200000),
    ('JL403', 103, 104, 303, '2024-06-08 16:00:00', '2024-06-08 20:00:00', 'Japan Airlines', 180000),
    ('CA404', 104, 105, 304, '2024-07-01 06:00:00', '2024-07-01 10:00:00', 'Air China', 480000),
    ('QF405', 105, 106, 305, '2024-07-04 13:00:00', '2024-07-04 17:00:00', 'Qantas', 730000),
    ('AC406', 106, 107, 306, '2024-08-24 09:00:00', '2024-08-24 13:00:00', 'Air Canada', 1300000),
    ('KE407', 111, 102, 310, '2024-06-01 11:00:00', '2024-06-01 15:00:00', 'Korean Air', 650000),
    ('OZ408', 111, 105, 311, '2024-06-05 09:00:00', '2024-06-05 13:00:00', 'Asiana Airlines', 620000);

INSERT INTO Passenger (passengerId, passportNum, firstName, lastName, gender, nationality, address, phoneNum, dateOfBirth)
VALUES
    (10001, 'AT1M43', 'Alan', 'Turing', 'Male', 'England', '123 Main Street, London, England', '4499-1111-0111', '1943-01-01'),
    (10002, 'GH2F66', 'Grace', 'Hopper', 'Female', 'USA', '456 Maple Avenue, New York, USA', '0199-2222-0222', '1966-02-02'),
    (10003, 'JV3M76', 'John', 'von Neumann', 'Male', 'Hungary', '789 Oak Lane, Budapest, Hungary', '3699-3333-0333', '1976-03-03'),
    (10004, 'TB1M88', 'Tim', 'Berners-Lee', 'Male', 'England', '101 Pine Street, Manchester, England', '4499-4444-0444', '1988-04-04'),
    (10005, 'AL1F99', 'Ada', 'Lovelace', 'Female', 'England', '234 Elm Street, Birmingham, England', '4499-5555-0555', '1999-05-05'),
    (10006, 'DK2M83', 'Donald', 'Knuth', 'Male', 'USA', '789 Oak Lane, Los Angeles, USA', '0199-6666-0666', '1983-06-06'),
    (10007, 'ED4M87', 'Edsger', 'Dijkstra', 'Male', 'Netherlands', '890 Birch Lane, Amsterdam, Netherlands', '3199-7777-0777', '1987-07-07'),
    (10008, 'MM2M91', 'Marvin', 'Minsky', 'Male', 'USA', '567 Cedar Road, Chicago, USA', '0199-8888-0888', '1991-08-08'),
    (10009, 'LT5M03', 'Linus', 'Torvalds', 'Male', 'Finland', '222 Willow Avenue, Helsinki, Finland', '3589-9999-0999', '2003-09-09'),
    (10010, 'SJ2M10', 'Steve', 'Jobs', 'Male', 'USA', '111 Cherry Street, Houston, USA', '0199-1111-0011', '2010-10-10'),
    (10011, 'JC3F90', 'Jane', 'Doe', 'Female', 'USA', '789 Elm Street, Seattle, USA', '6199-2222-3333', '1990-11-11'),
    (10012, 'AS4M85', 'Alexander', 'Smith', 'Male', 'Canada', '456 Maple Avenue, Toronto, Canada', '7266-5555-4444', '1985-12-12'),
    (10013, 'EM5F98', 'Emma', 'Miller', 'Female', 'Australia', '123 Beach Road, Sydney, Australia', '6149-8888-7777', '1998-01-13'),
    (10014, 'LM6F92', 'Lily', 'Chen', 'Female', 'China', '456 Lotus Lane, Beijing, China', '8610-1234-5678', '1992-02-14'),
    (10015, 'SS7M77', 'Sebastian', 'Schmidt', 'Male', 'Germany', '789 Forest Road, Berlin, Germany', '4915-9999-8888', '1977-03-15');
   
INSERT INTO Seat (seatId, flightId, seatNum, isAvailable)
VALUES
    (501, 'BA401', 13, TRUE),
    (502, 'AA402', 2, TRUE),
    (503, 'JL403', 54, TRUE),
    (504, 'CA404', 7, TRUE),
    (505, 'QF405', 1, TRUE),
    (506, 'AC406', 38, TRUE),
    (507, 'BA401', 14, TRUE),
    (508, 'AA402', 3, TRUE),
    (509, 'JL403', 55, TRUE),
    (510, 'CA404', 8, TRUE),
    (511, 'QF405', 2, TRUE),
    (512, 'AC406', 39, TRUE),
    (513, 'KE407', 21, TRUE),
    (514, 'KE407', 22, TRUE),
    (515, 'OZ408', 31, TRUE),
    (516, 'OZ408', 32, TRUE);

INSERT INTO Reservation (reservationId, flightId, passengerId, passportNum, reservationDate, classType, seatNum, additionalBaggage, totalPrice)
VALUES
    (20001, 'BA401', 10001, 'AT1M43', '2024-06-01', 'bu', 13, false, 540000),
    (20002, 'AA402', 10003, 'JV3M76', '2024-06-05', 'eco', 2, true, 200000),
    (20003, 'JL403', 10010, 'SJ2M10', '2024-06-08', 'eco', 54, false, 180000),
    (20004, 'CA404', 10004, 'TB1M88', '2024-07-01', 'bu', 7, true, 480000),
    (20005, 'QF405', 10014, 'LM6F92', '2024-07-04', 'eco', 1, false, 730000),
    (20006, 'AC406', 10007, 'ED4M87', '2024-08-24', 'bu', 38, true, 1300000),
    (20007, 'KE407', 10002, 'GH2F66', '2024-06-01', 'eco', 21, false, 650000),
    (20008, 'OZ408', 10008, 'MM2M91', '2024-06-05', 'eco', 31, true, 620000);
