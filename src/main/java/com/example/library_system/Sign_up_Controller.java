package com.example.library_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.io.IOException;
import java.sql.Date;


public class Sign_up_Controller  {
    @FXML
    private ImageView Car;

    @FXML
    private AnchorPane signUp1;

    @FXML
    private AnchorPane signUp2;
    private Stage primaryStage;
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private Hyperlink SignIn;
    @FXML
    private TextField NameField;
    @FXML
    private TextField SurnameField;
    @FXML
    private TextField EmailField;
    @FXML
    private TextField PhoneField;
    @FXML
    private TextField AddressField;
    @FXML
    private DatePicker BirthdayPick;
    @FXML
    private RadioButton MaleGender;
    @FXML
    private RadioButton FemaleGender;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private PasswordField ConfirmPassword;
    @FXML
    private Button SignUpButton;
    @FXML
    private Circle Page1;
    @FXML
    private Circle Page2;
    @FXML
    private Label CautionLabel;
    @FXML
    private TextField Passport;
    @FXML
    private TextField DriverLicence;
    @FXML
    private CheckBox A;
    @FXML
    private CheckBox A1;
    @FXML
    private CheckBox B;
    @FXML
    private CheckBox B1;
    @FXML
    private CheckBox BE;
    @FXML
    private CheckBox C;
    @FXML
    private CheckBox C1;
    @FXML
    private CheckBox C1E;
    @FXML
    private CheckBox CE;
    @FXML
    private CheckBox D;
    @FXML
    private CheckBox DE;
    @FXML
    private CheckBox D1;
    @FXML
    private CheckBox D1E;
    @FXML
    private CheckBox T;

    @FXML
    public void initialize() {
    }
    /*@FXML
    public void onClickSignUpButton() {
        if (NameField.getText().isEmpty() ||
                EmailField.getText().isEmpty() ||
                PhoneField.getText().isEmpty() ||
                AddressField.getText().isEmpty() ||
                (BirthdayPick.getValue() == null) ||
                PasswordField.getText().isEmpty() ||
                Passport.getText().isEmpty() ||
                DriverLicence.getText().isEmpty() ||
                ConfirmPassword.getText().isEmpty()) {
            CautionLabel.setText("Every field should be filled");

        }else if(!PasswordField.getText().equals(ConfirmPassword.getText())){
            CautionLabel.setText("Password and Confirm Password do not match.");
        } else {

            CautionLabel.setText("");
            User newUser = new User();
            newUser.setUser_name(NameField.getText());
            newUser.setUser_surname(SurnameField.getText());
            newUser.setUser_email(EmailField.getText());
            newUser.setUser_phone(PhoneField.getText());
            newUser.setUser_address(AddressField.getText());
            newUser.setUser_password(PasswordField.getText());
            newUser.setUser_licenseNumber(DriverLicence.getText());
            newUser.setUser_passportNumber(Passport.getText());
            newUser.setUser_birthday(Date.valueOf(BirthdayPick.getValue()));
            LibraryMainApplication.updateUserInfo(newUser.getUser_name() + " " + newUser.getUser_surname(),newUser.getUser_id());
            String category = "";
            if (A.isSelected()) category += "A ";
            if (A1.isSelected()) category += "A1 ";
            if (B.isSelected()) category += "B ";
            if (B1.isSelected()) category += "B1 ";
            if (BE.isSelected()) category += "BE ";
            if (C.isSelected()) category += "C ";
            if (C1.isSelected()) category += "C1 ";
            if (C1E.isSelected()) category += "C1E ";
            if (CE.isSelected()) category += "CE ";
            if (D.isSelected()) category += "D ";
            if (DE.isSelected()) category += "DE ";
            if (D1.isSelected()) category += "D1 ";
            if (D1E.isSelected()) category += "D1E ";
            if (T.isSelected()) category += "T ";
            newUser.setUser_licenseCategoria(category);


            int result = UserDB.createUser(newUser);
            if (result > 1) {
                System.out.println("User created successfully");
                navigateToMainPage();
                // Add code for any further actions after successful user creation
            } else {
                System.out.println("Failed to create user");
                // Add code for handling the failure to create a user
            }


        }
    }*/



    @FXML
    public void onClickCirclePage1() {
        navigateToSignUp();
    }

    @FXML
    public void onClickCirclePage2() {
        navigateToSignUp2();
    }

    @FXML
    private void onClickLinkSignIn() {
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
    private void navigateToSignUp() {
        primaryStage.setTitle("Sign Up Page");
        signUp1.setVisible(true);
        signUp2.setVisible(false);
    }
    private void navigateToSignUp2() {
        signUp1.setVisible(false);
        signUp2.setVisible(true);
        primaryStage.setTitle("Sign Up Page2");
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

}
