package com.example.library_system;

import java.sql.*;
import java.time.LocalDate;

public class ReservationDB {
    private static Connection conn;
    private static final String dbURL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String username = "postgres";
    private static final String password = "1337";

    // Constructor to initialize the connection
    public ReservationDB() {
        ConnectToDatabase();
    }

    private static void ConnectToDatabase() {
        try {
            conn = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isBookFree(int bookId, int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM reservation WHERE book_id = ? AND user_id = ? AND ending_date > CURRENT_DATE";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, bookId);
            statement.setInt(2, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int reservationCount = resultSet.getInt(1);
                    return reservationCount == 0;
                }
            }
        }
        return false;
    }

    public static void insertReservation(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservation(user_id, book_id, ending_date) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, reservation.getUser_id());
            statement.setInt(2, reservation.getBook_id());
            statement.setDate(3, reservation.getEnding_date());
            statement.executeUpdate();

        }
    }

    // Additional function to get all reservations for a book
    public static ResultSet getReservationsForBook(int bookId) throws SQLException {
        String sql = "SELECT * FROM reservation WHERE book_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, bookId);
            return statement.executeQuery();
        }
    }

    // Additional function to check if a user has already taken a book
    public static boolean isUserAlreadyTakenBook(int userId, int bookId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM reservation WHERE user_id = ? AND book_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int reservationCount = resultSet.getInt(1);
                    return reservationCount > 0;
                }
            }
        }
        return false;
    }
}
