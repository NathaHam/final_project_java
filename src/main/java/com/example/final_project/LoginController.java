package com.example.final_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.*;

import java.io.IOException;
import java.sql.SQLException;


public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private Label lblMessage;

    @FXML
    private void login() {
        String username = txtUsername.getText().trim();

        if (username.isEmpty()) {
            lblMessage.setText("Please enter a username");
            return;
        }
        try {
            User user = ChessStorage.loginUser(username);

            if (user == null) {
                lblMessage.setText("Account not found.Please register.");
                return;
            }
            openProfile(user);
        } catch (SQLException | IOException e) {
            lblMessage.setText("Logic error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void register() {

        String username = txtUsername.getText().trim();

        if (username.isEmpty()) {
            lblMessage.setText("Please enter a username");
            return;
        }

        try {
            boolean registered = ChessStorage.registerUser(username);

            if (!registered) {
                lblMessage.setText("Username already exists. Please login");
                return;
            }
            User user = ChessStorage.loginUser(username);
            openProfile(user);
        } catch (SQLException | IOException e) {
            lblMessage.setText("Registration error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void openProfile(User user) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("profile-view.fxml"));

        Scene scene = new Scene(loader.load());

        ProfileController controller  = loader.getController();

        controller.setUser(user);

        Stage stage = (Stage) txtUsername.getScene().getWindow();

        stage.setTitle("Chess - Player Profile");
        stage.setScene(scene);
        stage.setWidth(700);
        stage.setHeight(650);
        stage.centerOnScreen();
    }
}
