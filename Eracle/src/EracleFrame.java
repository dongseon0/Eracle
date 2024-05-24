import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EracleFrame extends JFrame {
    public static final Color EWHA_COLOR_1 = new Color(80, 149, 98);
    public static final Color EWHA_COLOR_2 = new Color(255, 253, 241);
    private Connection conn;

    public EracleFrame() {
        setTitle("Eracle Airline Reservation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize database connection
        initDBConnection();

        Container contentPane = getContentPane();
        contentPane.setBackground(EWHA_COLOR_1);
        contentPane.setLayout(new GridBagLayout());

        // Title label
        JLabel titleLabel = new JLabel("Eracle Airline Reservation System");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 30));
        titleLabel.setForeground(EWHA_COLOR_2);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));  // 5 rows, 1 column, 10px padding
        buttonPanel.setBackground(EWHA_COLOR_1);
        
        int padding = 20;
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        JButton addPassengersButton = new JButton("Add Passenger");
        addPassengersButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        addPassengersButton.setBackground(EWHA_COLOR_2);
        addPassengersButton.setOpaque(true);
        addPassengersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPassenger();
            }
        });
        
        JButton updatePassengerButton = new JButton("Update Passenger Info");
        updatePassengerButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        updatePassengerButton.setBackground(EWHA_COLOR_2);
        updatePassengerButton.setOpaque(true);
        updatePassengerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePassengerInfo();
            }
        });

        JButton cheapestFlightsButton = new JButton("Find Cheapest Flights by Airline");
        cheapestFlightsButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        cheapestFlightsButton.setBackground(EWHA_COLOR_2);
        cheapestFlightsButton.setOpaque(true);
        cheapestFlightsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findCheapestFlightsByDate();
            }
        });
        
        JButton flightsButton = new JButton("Make Reservations");
        flightsButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        flightsButton.setBackground(EWHA_COLOR_2);
        flightsButton.setOpaque(true);
        flightsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findFlights();
            }
        });

        JButton reservationButton = new JButton("Cancel Reservation");
        reservationButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        reservationButton.setBackground(EWHA_COLOR_2);
        reservationButton.setOpaque(true);
        reservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMyReservation();
            }
        });

        buttonPanel.add(addPassengersButton);
        buttonPanel.add(updatePassengerButton);
        buttonPanel.add(cheapestFlightsButton);
        buttonPanel.add(flightsButton);
        buttonPanel.add(reservationButton);

        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title constraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(20, 20, 20, 20); // padding around the title
        contentPane.add(titleLabel, gbc);
        
        // Button panel constraints
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(padding, padding, padding, padding);
        contentPane.add(buttonPanel, gbc);

        setSize(800, 600);
        setVisible(true);
    }

    // DB Connection initialization
    private void initDBConnection() {
        try {
            // Initialize database connection here
            String url = "jdbc:mysql://localhost:3306/eracle";
            String username = "Eracle";
            String password = "eracle";
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void addPassenger() {
        // Implement logic to add passenger information
    	JTextField passportNumField = new JTextField(20);
        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JTextField dobField = new JTextField(20);
        JTextField genderField = new JTextField(20);
        JTextField nationalityField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField phoneNumField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Passport Number:"));
        panel.add(passportNumField);
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        panel.add(dobField);
        panel.add(new JLabel("Gender:"));
        panel.add(genderField);
        panel.add(new JLabel("Nationality:"));
        panel.add(nationalityField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Phone Number:"));
        panel.add(phoneNumField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Passenger Login", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String passportNum = passportNumField.getText();
        		String firstName = firstNameField.getText();
        		String lastName = lastNameField.getText();
        		String dateOfBirth = dobField.getText();
        		String gender = genderField.getText();
        		String nationality = nationalityField.getText();
        		String address = addressField.getText();
        		String phoneNum = phoneNumField.getText();
                
                Passenger.insertPassenger(conn, passportNum, firstName, lastName, dateOfBirth, gender, nationality, address, phoneNum);
                JOptionPane.showMessageDialog(this, "Passenger added successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void findFlights() {
        JTextField arrivalAirportField = new JTextField(20);
        JTextField departureDateField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Arrival Airport ID:"));
        panel.add(arrivalAirportField);
        panel.add(new JLabel("Departure Date (YYYY-MM-DD):"));
        panel.add(departureDateField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Find Flights", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                List<String> flights = Flight.getFlightsWithAvailableSeats(conn, arrivalAirportField.getText(), departureDateField.getText());
                if (flights.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No flights found.");
                } else {
                    String selectedFlight = (String) JOptionPane.showInputDialog(this, "Available Flights:", "Select Flight", JOptionPane.QUESTION_MESSAGE, null, flights.toArray(), flights.get(0));
                    if (selectedFlight != null) {
                        makeReservation(selectedFlight.split(",")[0].split(":")[1].trim());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }    
    
    private void findCheapestFlightsByDate() {
        JTextField departureDateField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Departure Date (YYYY-MM-DD):"));
        panel.add(departureDateField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Find Cheapest Flights", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                List<String> flights = Flight.getCheapestFlightsByDate(conn, departureDateField.getText());
                if (flights.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No flights found for the selected date.", "No Flights", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, new JList<>(flights.toArray()), "Cheapest Flights", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void makeReservation(String flightId) {
        JTextField passportNumField = new JTextField(20);
        JTextField classTypeField = new JTextField(20);
        JTextField seatNumField = new JTextField(20);
        JCheckBox additionalBaggageField = new JCheckBox("Additional Baggage");

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Passport Number:"));
        panel.add(passportNumField);
        panel.add(new JLabel("Class Type:"));
        panel.add(classTypeField);
        panel.add(new JLabel("Seat Number:"));
        panel.add(seatNumField);
        panel.add(additionalBaggageField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Make Reservation", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Long passengerId = Passenger.getPassengerId(conn, passportNumField.getText());
                if (passengerId == null) {
                    JOptionPane.showMessageDialog(this, "Passenger not found. Please check the passport number.");
                    return;
                }
                boolean reservationSuccess = Reservation.makeReservation(conn, flightId, passengerId, passportNumField.getText(), 
                														new Timestamp(System.currentTimeMillis()), classTypeField.getText(), 
                														Integer.parseInt(seatNumField.getText()), additionalBaggageField.isSelected());
                if (reservationSuccess) {
                    JOptionPane.showMessageDialog(this, "Reservation made successfully.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void showMyReservation() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextField passportNumField = new JTextField(20);
        
        panel.add(new JLabel("Your Passport Number:"));
        panel.add(passportNumField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Check My Reservation", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                List<String> myReservations = Reservation.getReservationsByPassportNum(conn, passportNumField.getText());
                if (myReservations.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No flights found for the passenger.", "No Flights", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JPanel displayPanel = new JPanel(new BorderLayout());
                    JList<String> list = new JList<>(myReservations.toArray(new String[0]));
                    JScrollPane scrollPane = new JScrollPane(list);
                    scrollPane.setPreferredSize(new Dimension(400, 200));
                    
                    JTextField flightIdField = new JTextField(20);
                    JPanel inputPanel = new JPanel(new GridLayout(0, 1));
                    inputPanel.add(new JLabel("Enter Flight ID to delete reservation:"));
                    inputPanel.add(flightIdField);
                    
                    displayPanel.add(scrollPane, BorderLayout.CENTER);
                    displayPanel.add(inputPanel, BorderLayout.SOUTH);

                    int deleteResult = JOptionPane.showConfirmDialog(this, displayPanel, "Check My Reservation", JOptionPane.OK_CANCEL_OPTION);
                    if (deleteResult == JOptionPane.OK_OPTION) {
                        int rowsAffected = Reservation.deleteReservationByFlightId(conn, passportNumField.getText(), flightIdField.getText());
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(this, "Reservation deleted successfully.");
                        } else {
                            JOptionPane.showMessageDialog(this, "No reservation found for the given flight ID.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

    private void updatePassengerInfo() {
         JTextField passportNumField = new JTextField(20);
    
        JPanel searchPanel = new JPanel(new GridLayout(0, 1));
        searchPanel.add(new JLabel("Passport Number to Update:"));
        searchPanel.add(passportNumField);
    
        int searchResult = JOptionPane.showConfirmDialog(this, searchPanel, "Search Passenger", JOptionPane.OK_CANCEL_OPTION);
        if (searchResult == JOptionPane.OK_OPTION) {
            try {
                String passportNum = passportNumField.getText();
                String query = "SELECT * FROM Passenger WHERE passportNum = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, passportNum);
                ResultSet rs = stmt.executeQuery();
    
                if (rs.next()) {
                    // 입력할 속성 선택
                    String[] options = {"First Name", "Last Name", "Date of Birth", "Gender", "Nationality", "Address", "Phone Number"};
                    JComboBox<String> attributeComboBox = new JComboBox<>(options);
                    JTextField valueField = new JTextField(20);
    
                    JPanel updatePanel = new JPanel(new GridLayout(0, 1));
                    updatePanel.add(new JLabel("Select Attribute to Update:"));
                    updatePanel.add(attributeComboBox);
                    updatePanel.add(new JLabel("Enter New Value:"));
                    updatePanel.add(valueField);
    
                    int updateResult = JOptionPane.showConfirmDialog(this, updatePanel, "Update Passenger Info", JOptionPane.OK_CANCEL_OPTION);
                    if (updateResult == JOptionPane.OK_OPTION) {
                        String selectedAttribute = (String) attributeComboBox.getSelectedItem();
                        String newValue = valueField.getText();
                        
                        // 업데이트 메서드 호출
                        boolean updateSuccessful = Passenger.updatePassengerAttribute(conn, passportNum, selectedAttribute, newValue);
                        if (updateSuccessful) { JOptionPane.showMessageDialog(this, "Passenger information updated successfully.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Passenger information update failed.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Passenger not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


    public static void main(String[] args) {
        new EracleFrame();
    }
}
