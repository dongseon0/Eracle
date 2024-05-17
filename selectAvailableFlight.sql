CREATE VIEW AvailableSeats AS
SELECT 
    s.seatId,
    s.flightId,
    s.seatNum,
    s.isAvailable
FROM 
    Seat s
WHERE 
    s.isAvailable = TRUE;
    
SELECT 
    f.flightId,
    depAirport.airportName AS DepartureAirport,
    arrAirport.airportName AS ArrivalAirport,
    f.departureTime,
    f.arrivalTime,
    f.airline,
    f.flightPrice,
    s.seatNum
FROM 
    Flight f, 
    Airport depAirport, 
    Airport arrAirport, 
    AvailableSeats s
WHERE 
    f.departureAirportId = depAirport.airportId AND
    f.arrivalAirportId = arrAirport.airportId AND
    f.flightId = s.flightId AND
    depAirport.airportName = ? AND 
    arrAirport.airportName = ? AND
    f.airline = ?;
