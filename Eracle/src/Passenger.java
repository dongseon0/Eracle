import java.sql.*;

public class Passenger {

    // Method to get passenger ID based on passport number
    public static Long getPassengerId(Connection conn, String passportNum) throws SQLException {
        String passengerIdQuery = "SELECT passengerId FROM Passenger WHERE passportNum = ?";
        try (PreparedStatement stmt = conn.prepareStatement(passengerIdQuery)) {
            stmt.setString(1, passportNum);
            try (ResultSet rs = stmt.executeQuery()) {
                // If a passenger with the given passport number is found, return their ID
                if (rs.next()) {
                    return rs.getLong("passengerId");
                } else {
                    return null; // Return null if no passenger is found
                }
            }
        }
    }

    // Method to check if a passenger exists based on passport number
    public static boolean checkPassenger(Connection conn, String passportNum) throws SQLException {
        String checkPassengerQuery = "SELECT * FROM Passenger WHERE passportNum = ?";
        PreparedStatement stmt = conn.prepareStatement(checkPassengerQuery);
        stmt.setString(1, passportNum);
        try (ResultSet rs = stmt.executeQuery()) {
            return rs.next(); // Return true if a result is found, false otherwise
        }
    }

    // Method to update a passenger's attribute based on their passport number
    public static boolean updatePassengerAttribute(Connection conn, String passportNum, String selectedAttribute, String newValue) throws SQLException {
        String updateQuery = "";
        // Build the appropriate SQL update query based on the selected attribute
        switch (selectedAttribute) {
            case "First Name":
                updateQuery = "UPDATE Passenger SET firstName = ? WHERE passportNum = ?";
                break;
            case "Last Name":
                updateQuery = "UPDATE Passenger SET lastName = ? WHERE passportNum = ?";
                break;
            case "Date of Birth":
                updateQuery = "UPDATE Passenger SET dateOfBirth = ? WHERE passportNum = ?";
                break;
            case "Gender":
                updateQuery = "UPDATE Passenger SET gender = ? WHERE passportNum = ?";
                break;
            case "Nationality":
                updateQuery = "UPDATE Passenger SET nationality = ? WHERE passportNum = ?";
                break;
            case "Address":
                updateQuery = "UPDATE Passenger SET address = ? WHERE passportNum = ?";
                break;
            case "Phone Number":
                updateQuery = "UPDATE Passenger SET phoneNum = ? WHERE passportNum = ?";
                break;
            default:
                return false; // Return false if an invalid attribute is selected
        }
    
        try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setString(1, newValue);
            stmt.setString(2, passportNum);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if update was successful, false otherwise
        }
    }

    // Method to insert a new passenger into the database
    public static void insertPassenger(Connection conn, String passportNum, String firstName, String lastName, String dateOfBirth, String gender, String nationality, String address, String phoneNum) throws SQLException {
        try {
            insertIntoPassengerTable(conn, passportNum, firstName, lastName, dateOfBirth, gender, nationality, address, phoneNum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Private method to handle the actual SQL insertion of a new passenger
    private static void insertIntoPassengerTable(Connection conn, String passportNum, String firstName, String lastName, String dateOfBirth, String gender, String nationality, String address, String phoneNum) throws SQLException {
        String query = "INSERT INTO Passenger (passportNum, firstName, lastName, dateOfBirth, gender, nationality, address, phoneNum) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pStmt = conn.prepareStatement(query)) {
            pStmt.setString(1, passportNum);
            pStmt.setString(2, firstName);
            pStmt.setString(3, lastName);
            pStmt.setDate(4, java.sql.Date.valueOf(dateOfBirth));
            pStmt.setString(5, gender);
            pStmt.setString(6, nationality);
            pStmt.setString(7, address);
            pStmt.setString(8, phoneNum);

            int rowsInserted = pStmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("[SUCCESS] Insert into passenger table."); // Success message
            } else {
                System.out.println("[FAIL] Insert into passenger table."); // Failure message
            }
        }
    }
}
