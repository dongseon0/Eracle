SELECT 
    depAirport.airportName AS DepartureAirport,
    arrAirport.airportName AS ArrivalAirport,
    f.flightId,
    MIN(f.flightPrice) AS MinPrice
FROM 
    Flight f, 
    Airport depAirport, 
    Airport arrAirport
WHERE 
    f.departureAirportId = depAirport.airportId AND
    f.arrivalAirportId = arrAirport.airportId AND
    depAirport.airportName = ? AND 
    arrAirport.airportName = ?
GROUP BY 
    depAirport.airportName, 
    arrAirport.airportName, 
    f.flightId;
