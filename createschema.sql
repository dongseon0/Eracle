CREATE TABLE `Airport` (
	`airportId`	BIGINT	NOT NULL,
	`airportName`	VARCHAR	NOT NULL,
	`nation`	VARCHAR	NOT NULL,
	`location`	VARCHAR	NOT NULL
);

CREATE TABLE `Flight` (
	`flightId`	BIGINT	NOT NULL,
	`departureAirportId`	BIGINT	NOT NULL,
	`arrivalAirportId`	BIGINT	NOT NULL,
	`aircraftId`	BIGINT	NOT NULL,
	`departureTime`	DATETIME	NOT NULL,
	`arrivalTime`	DATETIME	NOT NULL,
	`airline`	VARCHAR	NOT NULL,
	`flightPrice`	INT	NOT NULL
);

CREATE TABLE Reservation (
	reservationId	BIGINT	NOT NULL,
	flightId	BIGINT	NOT NULL,
	passengerId	BIGINT	NOT NULL,
	passengerNum	VARCHAR(10)	NOT NULL,
	reservationDate	DATETIME	NOT NULL,
	classType	VARCHAR(5)	NOT NULL,
	seatNum		INT	NOT NULL,
	additionalBaggage	BOOLEAN	NOT NULL,
	totalPrice	INT	NOT NULL,
	primary key(reservationId),
	foreign key(flightId) references Flight(flightId),
	foreign key(passengerId) references Passenger(passengerId),
	foreign key(passengerNum) references Passenger(passengerNum)
);

CREATE TABLE `Airline` (
	`airlineId`	BIGINT	NOT NULL,
	`airlineName`	VARCHAR	NOT NULL
);

CREATE TABLE `Aircraft` (
	`aircraftId`	BIGINT	NOT NULL,
	`airlineId`	BIGINT	NOT NULL,
	`model`	VARCHAR	NOT NULL,
	`capacity`	INT	NOT NULL
);

CREATE TABLE `Seat` (
	`seatId`	BIGINT	NOT NULL,
	`flightId`	BIGINT	NOT NULL,
	`seatNum`	INT	NOT NULL,
	`isAvailable`	BOOLEAN	NOT NULL
);

CREATE TABLE Passenger (
	passengerId	BIGINT	NOT NULL,
	passengerNum	VARCHAR(10)	NOT NULL,
	firstName	VARCHAR(20)	NOT NULL,
	lastName	VARCHAR(20)	NOT NULL,
	dateOfBirth 	DATE	NOT NULL,
	gender		VARCHAR(6)	NOT NULL,
	nationality	VARCHAR(20)	NOT NULL,
	address		VARCHAR(50)	NOT NULL,
	phoneNum	VARCHAR(20)	NOT NULL,
	PRIMARY KEY(passengerId)
);
