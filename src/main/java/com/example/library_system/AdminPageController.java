package com.example.library_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.Buffer;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AdminPageController {
    private Stage primaryStage;

    // Set the primary stage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private TextField SearchField;
    @FXML
    private Button SearchButton;

    @FXML
    private TextField Name;
    @FXML
    private TextField Author;
    @FXML
    private TextField Price;
    @FXML
    private DatePicker Year;
    @FXML
    private TextField Description;
    @FXML
    private TextField Espn;
    @FXML
    private Button SaveButton;
    @FXML
    private Button DeleteButton;
    @FXML
    private Label UserName;
    @FXML
    private Button LogoutButton;

    @FXML
    private Label BookName;
    @FXML
    private Label BookAuthor;

    private BookDB bookDB;

    @FXML
    private Label Message;
    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, Date> yearColumn;

    @FXML
    public void initialize() {
        UserInfo userInfo = UserInfo.getInstance();
        UserName.setText(userInfo.getUserName());

        // Set up cell value factories for each column
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        bookDB = new BookDB();

        // Fetch books from the database and populate the TableView
        fetchBooksFromDatabase();
    }



    @FXML
    public void onClickLogoutButton(){
        navigateToSignIn();
    }

    public void navigateToSignIn(){
        try {
            // Load the FXML file for the sign-in page
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Sign_in.fxml"));
            Parent signInPage = fxmlLoader.load();

            Sign_in_Controller signInController = fxmlLoader.getController();
            signInController.setPrimaryStage(primaryStage);

            // Set the sign-in page as the root of the existing scene
            primaryStage.getScene().setRoot(signInPage);
            primaryStage.setTitle("Sign In Page");
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    private void fetchBooksFromDatabase() {
        try {
            List<Book> booksFromDB = bookDB.getAllBooks();
            ObservableList<Book> bookObservableList = FXCollections.observableArrayList(booksFromDB);
            booksTable.setItems(bookObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onBookSelected() {
        Message.setText("");
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            // Update text fields with book information
            Name.setText(selectedBook.getTitle());
            Author.setText(selectedBook.getAuthor());
            Year.setValue(selectedBook.getYear());
            BookName.setText(selectedBook.getTitle());
            BookAuthor.setText(selectedBook.getAuthor());
            Espn.setText(String.valueOf(selectedBook.getEspn()));
            Description.setText(selectedBook.getDescription());
            Price.setText(String.valueOf(selectedBook.getPrice()));
        }
    }



    @FXML
    private void handleSaveButtonAction() {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            // Update the selected book
            updateSelectedBook(selectedBook);
            clearFields();
            setMessage("Book updated successfully");
        } else {
            // Add a new book
            addNewBook();
            clearFields();
            setMessage("Book added successfully");
        }

        // Clear the selection and disable editing
        booksTable.getSelectionModel().clearSelection();
    }

    private void setMessage(String message) {
        Message.setText(message);
    }

    @FXML
    private void handleDeleteButtonAction() {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            // Delete the selected book
            deleteSelectedBook(selectedBook);
            setMessage("Book deleted successfully");
        } else {
            setMessage("Please select a book to delete");
        }

        // Clear the selection and disable editing
        booksTable.getSelectionModel().clearSelection();
        clearFields();
    }

    private void deleteSelectedBook(Book selectedBook) {
        try {
            // Delete the book from the database
            bookDB.deleteBook(selectedBook.getId());
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    private void updateSelectedBook(Book selectedBook) {
        // Update the selected book with the new values
        selectedBook.setTitle(Name.getText());
        selectedBook.setAuthor(Author.getText());
        selectedBook.setYear(Date.valueOf(Year.getValue()).toLocalDate()); // Assuming Year is a String
        selectedBook.setEspn(Long.parseLong(Espn.getText()));
        selectedBook.setDescription(Description.getText());
        selectedBook.setPrice(Double.parseDouble(Price.getText()));

        try {
            // Update the book in the database
            bookDB.updateBook(selectedBook);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    private void addNewBook() {
        // Create a new book with the provided values
        Book newBook = new Book();
        newBook.setTitle(Name.getText());
        newBook.setAuthor(Author.getText());
        newBook.setYear(Year.getValue()); // Assuming Year is a DatePicker
        newBook.setEspn(Long.parseLong(Espn.getText()));
        newBook.setDescription(Description.getText());
        newBook.setPrice(Double.parseDouble(Price.getText()));

        try {
            // Insert the new book into the database
            bookDB.insertBook(newBook);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }



    @FXML
    private void handleSearchAction() {
        String searchText = SearchField.getText().trim();

        // Check if the search text is empty
        if (searchText.isEmpty()) {
            // If empty, fetch all books
            fetchBooksFromDatabase();
        } else {
            // If not empty, fetch books based on the search text
            searchBooks(searchText);
        }
    }

    private void searchBooks(String searchText) {
        // Use your BookDB class or database query to search for books
        List<Book> searchResults = bookDB.searchBooks(searchText);

        // Update the TableView with the search results
        updateTableView(searchResults);
    }

    private void updateTableView(List<Book> books) {
        // Clear existing items
        booksTable.getItems().clear();

        // Add the new items
        booksTable.getItems().addAll(books);
    }

    private void clearFields() {
        Name.clear();
        Author.clear();
        Price.clear();
        Year.setValue(null); // Clear the DatePicker
        Description.clear();
        Espn.clear();
        UserName.setText("");  // Clear the UserName label
        BookName.setText("");  // Clear the BookName label
        BookAuthor.setText(""); // Clear the BookAuthor label
    }
}
