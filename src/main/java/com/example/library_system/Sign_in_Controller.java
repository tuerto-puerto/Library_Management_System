package com.example.library_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class Sign_in_Controller {

    @FXML
    private ImageView Car;
    @FXML
    private Button signIn;

//    @FXML
//    public void onClickSignInButton(){
//        navigateToMainPage();
//    }

    private Stage primaryStage;

    // Set the primary stage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private Hyperlink SignUp;

    @FXML
    private TextField LoginField;
    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button SignIn;

    @FXML
    public void initialize(){

    }

/*    @FXML
    private void onClickButtonSignIn(){
        User user = new User();
        user.setUser_email(LoginField.getText());
        user.setUser_phone(LoginField.getText());
        user.setUser_password(PasswordField.getText());
        boolean userExists = UserDB.isUserExist(user);
        boolean adminExists = UserDB.isAdminExist(user);

        if (userExists) {
            user = UserDB.getUserInfo(user);
            LibraryMainApplication.updateUserInfo(user.getUser_name() + " " + user.getUser_surname(),user.getUser_id());
            System.out.println("User in database");
            navigateToMainPage();
        } else if (adminExists) {
            user = UserDB.getAdminInfo(user);
            LibraryMainApplication.updateUserInfo(user.getUser_name() + " " + user.getUser_surname(),user.getUser_id());
            System.out.println("Admin in database");
            navigateToAdminPage();
        } else {
            System.out.println("Failed to find user");
            // Add code for handling the failure to create a user
        }
    }*/


    @FXML
    private void onClickLinkSignUp() {
        try {
            // Load the FXML file for the sign-in page
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Sign_up.fxml"));
            Parent signUpPage = fxmlLoader.load();

            Sign_up_Controller signUpController = fxmlLoader.getController();
            signUpController.setPrimaryStage(primaryStage);

            // Set the sign-up page as the root of the existing scene
            primaryStage.getScene().setRoot(signUpPage);
            primaryStage.setTitle("Sign in page");
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    /*private void navigateToMainPage(){
        try {
            // Load the FXML file for the sign-in page
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main_Page.fxml"));
            Parent mainPage = fxmlLoader.load();

            MainPageController mainPageController = fxmlLoader.getController();
            mainPageController.setPrimaryStage(primaryStage);

            // Set the sign-up page as the root of the existing scene
            primaryStage.getScene().setRoot(mainPage);
            primaryStage.setTitle("Main page");
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }*/

    /*private void navigateToAdminPage(){
        try {
            // Load the FXML file for the sign-in page
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Admin_Page.fxml"));
            Parent mainPage = fxmlLoader.load();

            AdminPageController adminPageController = fxmlLoader.getController();
            adminPageController.setPrimaryStage(primaryStage);

            // Set the sign-up page as the root of the existing scene
            primaryStage.getScene().setRoot(mainPage);
            primaryStage.setTitle("Admin page");
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }*/
}
