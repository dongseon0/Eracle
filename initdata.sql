INSERT INTO Airport (airportId, airportName, nation, location)
VALUES
    ('LHR', 'Heathrow Airport', 'England', 'London'),
    ('JFK', 'John F. Kennedy International Airport', 'USA', 'New York'),
    ('LAX', 'Los Angeles International Airport', 'USA', 'Los Angeles'),
    ('HND', 'Tokyo Haneda Airport', 'Japan', 'Tokyo'),
    ('PEK', 'Beijing Capital International Airport', 'China', 'Beijing'),
    ('SYD', 'Sydney Kingsford Smith Airport', 'Australia', 'Sydney'),
    ('YYZ', 'Toronto Pearson International Airport', 'Canada', 'Toronto'),
    ('CDG', 'Charles de Gaulle Airport', 'France', 'Paris'),
    ('FRA', 'Frankfurt Airport', 'Germany', 'Frankfurt'),
    ('AMS', 'Amsterdam Schiphol Airport', 'Netherlands', 'Amsterdam'),
    ('ICN', 'Incheon International Airport', 'South Korea', 'Incheon'),
    ('NRT', 'Narita International Airport', 'Japan', 'Tokyo'),
    ('KIX', 'Kansai International Airport', 'Japan', 'Osaka'),
    ('CTS', 'New Chitose Airport', 'Japan', 'Sapporo');

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
    ('BA401', 'ICN', 'LHR', 301, '2024-06-01 10:00:00', '2024-06-01 14:00:00', 'British Airways', 540000),
    ('AA402', 'ICN', 'JFK', 302, '2024-06-05 08:00:00', '2024-06-05 12:00:00', 'American Airlines', 200000),
    ('JL403', 'ICN', 'HND', 303, '2024-06-08 16:00:00', '2024-06-08 20:00:00', 'Japan Airlines', 180000),
    ('CA404', 'ICN', 'PEK', 304, '2024-07-01 06:00:00', '2024-07-01 10:00:00', 'Air China', 480000),
    ('QF405', 'ICN', 'SYD', 305, '2024-07-04 13:00:00', '2024-07-04 17:00:00', 'Qantas', 730000),
    ('AC406', 'ICN', 'YYZ', 306, '2024-08-24 09:00:00', '2024-08-24 13:00:00', 'Air Canada', 1300000),
    ('KE407', 'ICN', 'JFK', 310, '2024-06-05 11:00:00', '2024-06-05 15:00:00', 'Korean Air', 650000),
    ('OZ408', 'ICN', 'PEK', 311, '2024-06-05 09:00:00', '2024-06-05 13:00:00', 'Asiana Airlines', 620000),
    ('KE409', 'ICN', 'CDG', 310, '2024-06-05 10:00:00', '2024-06-05 16:00:00', 'Korean Air', 700000),
    ('KE410', 'ICN', 'FRA', 310, '2024-06-05 12:00:00', '2024-06-05 18:00:00', 'Korean Air', 720000),
    ('KL200', 'ICN', 'AMS', 301, '2024-06-05 13:00:00', '2024-06-05 19:00:00', 'KLM Royal Dutch Airlines', 560000);

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
   
INSERT INTO PassengerSeat (seatId, flightId, seatNum, isAvailable, passengerId)
VALUES
    (501, 'AA402', 1, TRUE, NULL),
    (502, 'AA402', 2, FALSE, 10003),
    (503, 'AA402', 3, TRUE, NULL),
    (504, 'AA402', 4, TRUE, NULL),
    (505, 'AA402', 5, FALSE, 10013),
    (506, 'KE407', 1, FALSE, 10002),
    (507, 'KE407', 2, TRUE, NULL),
    (508, 'KE407', 3, TRUE, NULL),
    (509, 'KE407', 4, TRUE, NULL),
    (510, 'KE407', 5, FALSE, 10009),
    (511, 'KE409', 1, TRUE, NULL),
    (512, 'BA401', 2, FALSE, 10001),
    (513, 'JL403', 4, FALSE, 10010),
    (514, 'CA404', 2, FALSE, 10004),
    (515, 'QF405', 5, FALSE, 10014),
    (516, 'AC406', 2, FALSE, 10007),
    (517, 'OZ408', 5, FALSE, 10008);

INSERT INTO Reservation (reservationId, flightId, passengerId, passportNum, reservationDate, classType, seatNum, additionalBaggage, totalPrice)
VALUES
    (20001, 'BA401', 10001, 'AT1M43', '2024-06-01', 'bu', 1, FALSE, 540000),
    (20002, 'AA402', 10003, 'JV3M76', '2024-06-05', 'eco', 2, TRUE, 200000),
    (20003, 'JL403', 10010, 'SJ2M10', '2024-06-08', 'eco', 54, FALSE, 180000),
    (20004, 'CA404', 10004, 'TB1M88', '2024-07-01', 'bu', 7, TRUE, 480000),
    (20005, 'QF405', 10014, 'LM6F92', '2024-07-04', 'eco', 1, FALSE, 730000),
    (20006, 'AC406', 10007, 'ED4M87', '2024-08-24', 'bu', 38, TRUE, 1300000),
    (20007, 'KE407', 10002, 'GH2F66', '2024-06-01', 'eco', 1, FALSE, 650000),
    (20008, 'OZ408', 10008, 'MM2M91', '2024-06-05', 'eco', 31, TRUE, 620000),
    (20009, 'AA402', 10013, 'EM5F98', '2024-06-05', 'bu', 5, TRUE, 200000),
    (20010, 'KE407', 10009, 'LT5M03', '2024-06-01', 'eco', 5, FALSE, 650000);
