import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Reservation {

    // Method to get seat number based on passenger's passport number and flight ID
    private static int getSeatNum(Connection conn, String passportNum, String flightId) {
        int seatNum = 0;
        Long passengerId = null;

        // Retrieve passenger ID using the passport number
        try {
            passengerId = Passenger.getPassengerId(conn, passportNum);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If passenger ID is not found, return 0
        if (passengerId == null) {
            return seatNum;
        }

        // Retrieve seat number using passenger ID and flight ID
        String seatNumQuery = "SELECT seatNum FROM passengerSeat WHERE passengerId = ? AND flightId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(seatNumQuery)) {
            stmt.setLong(1, passengerId);
            stmt.setString(2, flightId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    seatNum = rs.getInt("seatNum");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seatNum;
    }

    // Method to calculate total price based on flight ID, class type, and additional baggage
    private static int calculateTotalPrice(Connection conn, String flightId, String classType, boolean additionalBaggage) {
        int basePrice = 0;
        // Retrieve base price of the flight
        try {
            String query = "SELECT flightPrice FROM Flight WHERE flightId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, flightId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                basePrice = rs.getInt("flightPrice");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Determine the price multiplier based on class type
        int classMultiplier = "First".equalsIgnoreCase(classType) ? 3 : ("Business".equalsIgnoreCase(classType) ? 2 : 1);
        int baggageFee = additionalBaggage ? 50 : 0;

        // Calculate total price
        return basePrice * classMultiplier + baggageFee;
    }

    // Method to make a reservation
    public static boolean makeReservation(Connection conn, String flightId, long passengerId, String passportNum, Timestamp reservationDateStr, String classType, int seatNum, boolean additionalBaggage) throws SQLException {
        try {
            // Calculate total price for the reservation
            int totalPrice = calculateTotalPrice(conn, flightId, classType, additionalBaggage);
            // Insert reservation into the reservation table
            insertIntoReservationTable(conn, flightId, passengerId, passportNum, reservationDateStr, classType, seatNum, additionalBaggage, totalPrice);
            // Update seat availability
            updatePassengerSeat(conn, passengerId, flightId, seatNum, false);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get reservations based on passenger's passport number
    public static List<String> getReservationsByPassportNum(Connection conn, String passportNum) throws SQLException {
        List<String> myReservations = new ArrayList<>();
        String query = "SELECT flightId, reservationDate, classType, seatNum, additionalBaggage " +
                       "FROM Passenger, Reservation " +
                       "WHERE Passenger.passengerId = Reservation.passengerId " +
                       "AND Passenger.passportNum = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, passportNum);
            try (ResultSet rs = stmt.executeQuery()) {
                // Retrieve and add reservation details to the list
                while (rs.next()) {
                    myReservations.add("Flight ID: " + rs.getString("flightId") + 
                                       ", Reservation Date: " + rs.getDate("reservationDate") + 
                                       ", Class Type: " + rs.getString("classType") + 
                                       ", Seat Number: " + rs.getInt("seatNum") + 
                                       ", Additional Baggage: " + rs.getBoolean("additionalBaggage"));
                }
            }
        }
        return myReservations;
    }

    // Method to delete a reservation based on flight ID and passport number
    public static int deleteReservationByFlightId(Connection conn, String passportNum, String flightId) {
        String query = "DELETE FROM Reservation " +
                       "WHERE passengerId = (SELECT passengerId FROM Passenger WHERE passportNum = ?) " +
                       "AND flightId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, passportNum);
            stmt.setString(2, flightId);
            int seatNum = getSeatNum(conn, passportNum, flightId);
            if (stmt.executeUpdate() > 0) {
                // Reset seat availability if deletion is successful
                resetPassengerSeat(conn, true, flightId, seatNum);
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Private method to insert a reservation into the reservation table
    private static void insertIntoReservationTable(Connection conn, String flightId, long passengerId, String passportNum, Timestamp reservationDateStr, String classType, int seatNum, boolean additionalBaggage, int totalPrice) throws SQLException {
        String sql = "INSERT INTO Reservation (flightId, passengerId, passportNum, reservationDate, classType, seatNum, additionalBaggage, totalPrice) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
            // Set values for the SQL query
            pStmt.setString(1, flightId);
            pStmt.setLong(2, passengerId);
            pStmt.setString(3, passportNum);
            pStmt.setTimestamp(4, reservationDateStr);
            pStmt.setString(5, classType);
            pStmt.setInt(6, seatNum);
            pStmt.setBoolean(7, additionalBaggage);
            pStmt.setInt(8, totalPrice);

            // Execute the query and check the result
            int rowsInserted = pStmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Reservation successful.");
            } else {
                System.out.println("Reservation failed.");
            }
        }
    }

    // Private method to update passenger seat availability
    private static void updatePassengerSeat(Connection conn, long passengerId, String flightId, int seatNum, boolean isAvailable) throws SQLException {
        String sql = "UPDATE PassengerSeat SET passengerId = ?, isAvailable = ? WHERE flightId = ? AND seatNum = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, passengerId);
            stmt.setBoolean(2, isAvailable);
            stmt.setString(3, flightId);
            stmt.setInt(4, seatNum);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("PassengerSeat updated successfully.");
            } else {
                System.out.println("Failed to update PassengerSeat.");
            }
        }
    }

    // Private method to reset passenger seat availability
    private static void resetPassengerSeat(Connection conn, boolean isAvailable, String flightId, int seatNum) throws SQLException {
        String sql = "UPDATE PassengerSeat SET passengerId = NULL, isAvailable = ? WHERE flightId = ? AND seatNum = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, isAvailable);
            stmt.setString(2, flightId);
            stmt.setInt(3, seatNum);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("PassengerSeat reset successfully.");
            } else {
                System.out.println("Failed to reset PassengerSeat.");
            }
        }
    }

}
