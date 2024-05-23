import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Flight {
    public static List<String> getFlightsWithAvailableSeats(Connection conn, String arrivalAirportId, String departureDate) throws SQLException {
        List<String> flights = new ArrayList<>();
        String query = "SELECT f.flightId, f.departureTime, f.arrivalTime, s.seatNum " +
                       "FROM Flight f, Seat s " +
                       "WHERE f.flightId = s.flightId AND f.arrivalAirportId = ? AND DATE(f.departureTime) = ? " +
                       "AND s.isAvailable = True";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, arrivalAirportId);
            stmt.setDate(2, java.sql.Date.valueOf(departureDate));
            try (ResultSet rs = stmt.executeQuery()) {
                String currentFlightId = null;
                StringBuilder currentFlightInfo = new StringBuilder();
                while (rs.next()) {
                    String flightId = rs.getString("flightId");
                    if (!flightId.equals(currentFlightId)) {
                        if (currentFlightId != null) {
                            flights.add(currentFlightInfo.toString());
                        }
                        currentFlightId = flightId;
                        currentFlightInfo.setLength(0);
                        currentFlightInfo.append("Flight ID: ").append(flightId)
                                         .append(", Departure: ").append(rs.getTimestamp("departureTime"))
                                         .append(", Arrival: ").append(rs.getTimestamp("arrivalTime"))
                                         .append(", Available Seats: ");
                    }
                    currentFlightInfo.append(rs.getString("seatNum")).append(" ");
                }
                if (currentFlightId != null) {
                    flights.add(currentFlightInfo.toString());
                }
            }
        }
        return flights;
    }
    
    public static List<String> getCheapestFlightsByDate(Connection conn, String departureDate) throws SQLException {
        List<String> flights = new ArrayList<>();
        String query = "SELECT airline, arrivalAirportId, MIN(flightPrice) AS MinPrice " +
                       "FROM Flight " +
                       "WHERE DATE(departureTime) = ? " +
                       "GROUP BY airline, arrivalAirportId";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, java.sql.Date.valueOf(departureDate));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    flights.add("Airline: " + rs.getString("airline") + 
                                ", Airport ID: " + rs.getString("arrivalAirportId") + 
                                ", Minimum Price: $" + rs.getDouble("MinPrice"));
                }
            }
        }
        return flights;
    }
}
