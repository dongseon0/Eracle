CREATE TABLE Airport (
    airportId BIGINT NOT NULL,
    airportName VARCHAR(50) NOT NULL,
    nation VARCHAR(50) NOT NULL,
    location VARCHAR(50) NOT NULL,
    PRIMARY KEY (airportId)
);

CREATE TABLE Airline (
    airlineId BIGINT NOT NULL,
    airlineName VARCHAR(50) NOT NULL,
    PRIMARY KEY (airlineId)
);

CREATE TABLE Aircraft (
    aircraftId BIGINT NOT NULL,
    airlineId BIGINT NOT NULL,
    model VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    PRIMARY KEY (aircraftId),
    FOREIGN KEY (airlineId) REFERENCES Airline (airlineId)
);

CREATE TABLE Flight (
    flightId VARCHAR(10) NOT NULL,
    departureAirportId BIGINT NOT NULL,
    arrivalAirportId BIGINT NOT NULL,
    aircraftId BIGINT NOT NULL,
    departureTime DATETIME NOT NULL,
    arrivalTime DATETIME NOT NULL,
    airline VARCHAR(50) NOT NULL,
    flightPrice INT NOT NULL,
    PRIMARY KEY (flightId),
    FOREIGN KEY (departureAirportId) REFERENCES Airport (airportId),
    FOREIGN KEY (arrivalAirportId) REFERENCES Airport (airportId),
    FOREIGN KEY (aircraftId) REFERENCES Aircraft (aircraftId)
);

CREATE TABLE Passenger (
    passengerId BIGINT NOT NULL,
    passengerNum VARCHAR(10) NOT NULL,
    firstName VARCHAR(20) NOT NULL,
    lastName VARCHAR(20) NOT NULL,
    dateOfBirth DATE NOT NULL,
    gender VARCHAR(6) NOT NULL,
    nationality VARCHAR(20) NOT NULL,
    address VARCHAR(50) NOT NULL,
    phoneNum VARCHAR(20) NOT NULL,
    PRIMARY KEY (passengerId)
);

CREATE TABLE Seat (
    seatId BIGINT NOT NULL,
    flightId VARCHAR(10) NOT NULL,
    seatNum INT NOT NULL,
    isAvailable BOOLEAN NOT NULL,
    PRIMARY KEY (seatId),
    FOREIGN KEY (flightId) REFERENCES Flight (flightId)
);

CREATE TABLE Reservation (
    reservationId BIGINT NOT NULL,
    flightId VARCHAR(10) NOT NULL,
    passengerId BIGINT NOT NULL,
    passengerNum VARCHAR(10) NOT NULL,
    reservationDate DATETIME NOT NULL,
    classType VARCHAR(5) NOT NULL,
    seatNum INT NOT NULL,
    additionalBaggage BOOLEAN NOT NULL,
    totalPrice INT NOT NULL,
    PRIMARY KEY (reservationId),
    FOREIGN KEY (flightId) REFERENCES Flight (flightId),
    FOREIGN KEY (passengerId) REFERENCES Passenger (passengerId)
);
