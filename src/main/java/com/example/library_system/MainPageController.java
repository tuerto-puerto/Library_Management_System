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
import java.time.LocalDate;
import java.util.List;

public class MainPageController {
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
    private Label Name;
    @FXML
    private Label Author;
    @FXML
    private Label Price;
    @FXML
    private Label Year;
    @FXML
    private Label Description;
    @FXML
    private Label Espn;
    @FXML
    private Button TakeButton;
    @FXML
    private Label UserName;
    @FXML
    private Button LogoutButton;

    @FXML
    private Label BookName;
    @FXML
    private Label BookAuthor;
    @FXML
    private Label Status;

    private BookDB bookDB;
    private ReservationDB reservDB;

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
        reservDB = new ReservationDB();

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

    @FXML
    public void onBookSelected() throws SQLException {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();

        UserInfo userInfo = UserInfo.getInstance();
        int userId = userInfo.getUserId();
        if (selectedBook != null) {
            // Update labels with book information
            Name.setText(selectedBook.getTitle());
            Author.setText(selectedBook.getAuthor());
            Year.setText(String.valueOf(selectedBook.getYear()));
            BookName.setText(selectedBook.getTitle());
            BookAuthor.setText(selectedBook.getAuthor());
            Espn.setText(String.valueOf(selectedBook.getEspn()));
            Description.setText(selectedBook.getDescription());
            Price.setText(selectedBook.getPrice() + " $/per month");
            if(reservDB.isBookFree(selectedBook.getId(), userId)){
                Status.setText("Free");
            }
            else {
                Status.setText("Busy");
            }
        }
    }

    @FXML
    private void handleTakeButtonAction() {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        UserInfo userInfo = UserInfo.getInstance();
        int userId = userInfo.getUserId();

        if (selectedBook != null) {
            try {
                // Check if the user already has this book
                if (!reservDB.isBookFree(selectedBook.getId(), userId)) {
                    // User already has this book
                    showAlert("Already Taken", "You already have this book.");
                    return;
                }

                // Calculate the ending date (today + 30 days)
                LocalDate endingDate = LocalDate.now().plusDays(30);

                // Create a reservation for the user and book
                Reservation reservation = new Reservation();
                reservation.setUser_id((userId));
                reservation.setBook_id(selectedBook.getId());
                reservation.setEnding_date(Date.valueOf(endingDate));

                // Add the reservation to the database
                ReservationDB.insertReservation(reservation);

                showAlert("Success", "Book taken successfully!");

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "An error occurred while taking the book.");
            }
        } else {
            showAlert("No Book Selected", "Please select a book before taking.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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

}
