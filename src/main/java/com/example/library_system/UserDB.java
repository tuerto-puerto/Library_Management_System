package com.example.library_system;

import java.sql.*;
import java.sql.DriverManager;
public class UserDB {
    private static Connection conn;
    private static final String dbURL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String username = "azar";
    private static final String password = "azar";

    public static String ConnectToDatabase() {

        try {
            conn = DriverManager.getConnection(dbURL, username, password);
            return "Connected to database";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public static int createUser(User user) {
        String insertUserSQL = "INSERT INTO \"user\"(" +
                " \"name\", surname, email, phone, address, password, birthday)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?);";

        String insertPassportSQL = "INSERT INTO passport (user_id, passportnumber) VALUES (DEFAULT, ?);";

        String insertLicenseSQL = "INSERT INTO license(user_id, number, category) VALUES (DEFAULT, ?, ?);";

        int userId = -1;

        try (PreparedStatement userStmt = conn.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS)) {
            userStmt.setString(1, user.getUser_name());
            userStmt.setString(2, user.getUser_surname());
            userStmt.setString(3, user.getUser_email());
            userStmt.setString(4, user.getUser_phone());
            userStmt.setString(5, user.getUser_address());
            userStmt.setString(6, user.getUser_password());
            userStmt.setDate(7, user.getUser_birthday());

            int affectedRows = userStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed.");
            }

            ResultSet generatedKeys = userStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (userId != -1) {
            try (PreparedStatement passportStmt = conn.prepareStatement(insertPassportSQL)) {
                passportStmt.setString(1, user.getUser_passportNumber());

                int affectedRows = passportStmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Passport data inserted successfully!");
                } else {
                    System.out.println("Failed to insert passport data");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            try (PreparedStatement licenseStmt = conn.prepareStatement(insertLicenseSQL)) {
                licenseStmt.setString(1, user.getUser_licenseNumber());
                licenseStmt.setString(2, user.getUser_licenseCategoria());

                int affectedRows = licenseStmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("License data inserted successfully!");
                } else {
                    System.out.println("Failed to insert license data");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return userId;
    }

    public static boolean isUserExist(User user) {
        String sql = "SELECT * FROM \"user\" WHERE (email = ? OR phone = ?) AND password = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUser_email());
            stmt.setString(2, user.getUser_phone());
            stmt.setString(3, user.getUser_password());

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("User does not exist");
            System.out.println(e.getMessage());
            return false;
        }
    }




    public static User getUserInfo(User user) {
        String sql = "SELECT * FROM \"user\" WHERE (email = ? OR phone = ?) AND password = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUser_email());
            stmt.setString(2, user.getUser_phone());
            stmt.setString(3, user.getUser_password());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Populate the User object with information from the database
                    user.setUser_id(rs.getInt("id"));
                    user.setUser_name(rs.getString("name"));
                    user.setUser_surname(rs.getString("surname"));
                    user.setUser_email(rs.getString("email"));
                    user.setUser_phone(rs.getString("phone"));
                    user.setUser_address(rs.getString("address"));
                    user.setUser_password(rs.getString("password"));
                    user.setUser_birthday(rs.getDate("birthday"));

                    // Add other fields as needed

                    return user;
                } else {
                    // If no matching user is found, return null
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean isAdminExist(User user) {
        String sql = "SELECT * FROM administrator WHERE (email = ? OR phone = ?) AND password = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUser_email());
            stmt.setString(2, user.getUser_phone());
            stmt.setString(3, user.getUser_password());

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Admin does not exist");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static User getAdminInfo(User user) {
        String sql = "SELECT * FROM administrator WHERE (email = ? OR phone = ?) AND password = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUser_email());
            stmt.setString(2, user.getUser_phone());
            stmt.setString(3, user.getUser_password());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Populate the User object with information from the database
                    user.setUser_id(rs.getInt("id"));
                    user.setUser_name(rs.getString("name"));
                    user.setUser_surname(rs.getString("surname"));
                    user.setUser_email(rs.getString("email"));
                    user.setUser_phone(rs.getString("phone"));
                    user.setUser_password(rs.getString("password"));
                    user.setUser_licenseCategoria(rs.getString("position"));

                    // Add other fields as needed

                    return user;
                } else {
                    // If no matching admin is found, return null
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}