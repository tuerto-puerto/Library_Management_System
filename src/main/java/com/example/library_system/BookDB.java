package com.example.library_system;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDB {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "1337";

    public BookDB() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM book c";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        books.add(getBookFromResultSet(resultSet));
                    }
                }
            }
        }
        return books;
    }

    public void insertBook(Book book) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String insertBookSql = "INSERT INTO book(title, description, author, price, year, espn) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
            try (PreparedStatement insertBookStatement = connection.prepareStatement(insertBookSql)) {
                insertBookStatement.setString(1, book.getTitle());
                insertBookStatement.setString(2, book.getDescription());
                insertBookStatement.setString(3, book.getAuthor());
                insertBookStatement.setDouble(4, book.getPrice());
                insertBookStatement.setDate(5, Date.valueOf(book.getYear())); // Convert LocalDate to java.sql.Date
                insertBookStatement.setLong(6, book.getEspn());

                try (ResultSet resultSet = insertBookStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int bookId = resultSet.getInt("id");
                        book.setId(bookId);
                    }
                }
            }
        }
    }

    public void updateBook(Book book) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String updateCarSql = "UPDATE book SET title=?, description=?, author=?, price=?, year=?, espn=? WHERE id=?";
            try (PreparedStatement updateBookStatement = connection.prepareStatement(updateCarSql)) {
                updateBookStatement.setString(1, book.getTitle());
                updateBookStatement.setString(2, book.getDescription());
                updateBookStatement.setString(3, book.getAuthor());
                updateBookStatement.setDouble(4, book.getPrice());
                updateBookStatement.setDate(5, Date.valueOf(book.getYear())); // Convert LocalDate to java.sql.Date
                updateBookStatement.setLong(6, book.getEspn());
                updateBookStatement.setInt(7, book.getId());
                updateBookStatement.executeUpdate();
            }
        }
    }

    public void deleteBook(int bookId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String deleteBookSql = "DELETE FROM book WHERE id = ?";
            try (PreparedStatement deleteBookStatement = connection.prepareStatement(deleteBookSql)) {
                deleteBookStatement.setInt(1, bookId);
                deleteBookStatement.executeUpdate();
            }
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setDescription(resultSet.getString("description"));
        book.setAuthor(resultSet.getString("author"));
        book.setPrice(resultSet.getDouble("price"));
        book.setYear(resultSet.getDate("year").toLocalDate());
        book.setEspn(resultSet.getLong("espn"));

        return book;
    }

    public List<Book> searchBooks(String searchText) {
        List<Book> searchResults = new ArrayList<>();
        // Adjust the SQL query based on your database schema
        String sql = "SELECT * FROM book WHERE title LIKE ? OR author LIKE ? OR description LIKE ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Set the parameters for the prepared statement
            preparedStatement.setString(1, "%" + searchText + "%");
            preparedStatement.setString(2, "%" + searchText + "%");
            preparedStatement.setString(3, "%" + searchText + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Process the results and populate the searchResults list
                while (resultSet.next()) {
                    Book book = getBookFromResultSet(resultSet);
                    searchResults.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

        return searchResults;
    }
}
