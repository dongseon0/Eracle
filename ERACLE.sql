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

CREATE TABLE `Reservation` (
	`reservationId`	BIGINT	NOT NULL,
	`flightId`	BIGINT	NOT NULL,
	`passengerId`	BIGINT	NOT NULL,
	`passengerNum`	VARCHAR	NOT NULL,
	`reservationDate`	DATETIME	NOT NULL,
	`classType`	VARCHAR	NOT NULL,
	`seatNum`	INT	NOT NULL,
	`additionalBaggage`	BOOLEAN	NOT NULL,
	`totalPrice`	INT	NOT NULL
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

CREATE TABLE `Passenger` (
	`passengerId`	BIGINT	NOT NULL,
	`passengerNum`	VARCHAR	NOT NULL,
	`firstName`	VARCHAR	NOT NULL,
	`lastName`	VARCHAR	NOT NULL,
	`dateOfBirth`	DATE	NOT NULL,
	`gender`	VARCHAR	NOT NULL,
	`nationality`	VARCHAR	NOT NULL,
	`address`	VARCHAR	NOT NULL,
	`phoneNum`	VARCHAR	NOT NULL
);

ALTER TABLE `Airport` ADD CONSTRAINT `PK_AIRPORT` PRIMARY KEY (
	`airportId`
);

ALTER TABLE `Flight` ADD CONSTRAINT `PK_FLIGHT` PRIMARY KEY (
	`flightId`
);

ALTER TABLE `Reservation` ADD CONSTRAINT `PK_RESERVATION` PRIMARY KEY (
	`reservationId`
);

ALTER TABLE `Airline` ADD CONSTRAINT `PK_AIRLINE` PRIMARY KEY (
	`airlineId`
);

ALTER TABLE `Aircraft` ADD CONSTRAINT `PK_AIRCRAFT` PRIMARY KEY (
	`aircraftId`
);

ALTER TABLE `Seat` ADD CONSTRAINT `PK_SEAT` PRIMARY KEY (
	`seatId`,
	`flightId`
);

ALTER TABLE `Passenger` ADD CONSTRAINT `PK_PASSENGER` PRIMARY KEY (
	`passengerId`,
	`passengerNum`
);

ALTER TABLE `Seat` ADD CONSTRAINT `FK_Flight_TO_Seat_1` FOREIGN KEY (
	`flightId`
)
REFERENCES `Flight` (
	`flightId`
);

