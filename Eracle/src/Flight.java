import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Flight {
    // Method to get flights with available seats based on arrival airport and departure date
    public static List<String> getFlightsWithAvailableSeats(Connection conn, String arrivalAirportId, String departureDate) throws SQLException {
        List<String> flights = new ArrayList<>();
        // SQL query to retrieve flights with available seats
        String query = "SELECT f.flightId, f.departureTime, f.arrivalTime, s.seatNum " +
                       "FROM Flight f, Seat s " +
                       "WHERE f.flightId = s.flightId AND f.arrivalAirportId = ? AND DATE(f.departureTime) = ? " +
                       "AND s.isAvailable = True";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set the arrival airport ID and departure date parameters
            stmt.setString(1, arrivalAirportId);
            stmt.setDate(2, java.sql.Date.valueOf(departureDate));
            try (ResultSet rs = stmt.executeQuery()) {
                String currentFlightId = null;
                StringBuilder currentFlightInfo = new StringBuilder();
                // Process the result set
                while (rs.next()) {
                    String flightId = rs.getString("flightId");
                    if (!flightId.equals(currentFlightId)) {
                        // Add the previous flight info to the list if it's a new flight ID
                        if (currentFlightId != null) {
                            flights.add(currentFlightInfo.toString());
                        }
                        currentFlightId = flightId;
                        currentFlightInfo.setLength(0);
                        // Initialize new flight info
                        currentFlightInfo.append("Flight ID: ").append(flightId)
                                         .append(", Departure: ").append(rs.getTimestamp("departureTime"))
                                         .append(", Arrival: ").append(rs.getTimestamp("arrivalTime"))
                                         .append(", Available Seats: ");
                    }
                    // Append seat number to the current flight info
                    currentFlightInfo.append(rs.getString("seatNum")).append(" ");
                }
                // Add the last flight info to the list
                if (currentFlightId != null) {
                    flights.add(currentFlightInfo.toString());
                }
            }
        }
        return flights;
    }

    // Method to get the cheapest flights for a specific departure date
    public static List<String> getCheapestFlightsByDate(Connection conn, String departureDate) throws SQLException {
        List<String> flights = new ArrayList<>();
        // SQL query to retrieve the cheapest flight price for each airline on a specific date
        String query = "SELECT airline, MIN(flightPrice) AS MinPrice " +
                       "FROM Flight " +
                       "WHERE DATE(departureTime) = ? " +
                       "GROUP BY airline";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set the departure date parameter
            stmt.setDate(1, java.sql.Date.valueOf(departureDate));
            try (ResultSet rs = stmt.executeQuery()) {
                // Process the result set
                while (rs.next()) {
                    // Add the airline and its minimum flight price to the list
                    flights.add("Airline: " + rs.getString("airline") + 
                                ", Minimum Price: ₩" + rs.getDouble("MinPrice"));
                }
            }
        }
        return flights;
    }
}
