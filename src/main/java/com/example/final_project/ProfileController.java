package com.example.final_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileController {

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblPlayerId;

    @FXML
    private Label lblDateCreated;

    @FXML
    private Label lblGames;

    @FXML
    private Label lblWins;

    @FXML
    private Label lblLosses;

    @FXML
    private Label lblDraws;

    @FXML
    private Label lblWinPercentage;

    private User currentUser;

    public void setUser(User user){
        currentUser = user;

        lblUsername.setText(user.getUsername());

        lblPlayerId.setText("Player ID: " + user.getId());

        lblDateCreated.setText("Date Created: " + user.getDateCreated());

        lblGames.setText(String.valueOf(user.getGamesPlayed()));

        lblWins.setText(String.valueOf(user.getWins()));

        lblLosses.setText(String.valueOf(user.getLosses()));

        lblDraws.setText(String.valueOf(user.getDraws()));

        double percentage = 0;

        if (user.getGamesPlayed() > 0){
            percentage = (double) (user.getWins() * 100) / user.getGamesPlayed();
        }
        lblWinPercentage.setText(String.format("Win Percentage: %.1f%%",percentage));
    }

    /// the play chess button and function is only temporarily in profile scene
    /// will add a new scene later for join/creating chess games when multiplayer connection is added
    /// this is just a placeholder
    @FXML
    private void playChess() {
        System.out.println ( currentUser.getUsername() + "wants to play chess.");
    }

    @FXML
    private void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) lblUsername.getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Java Chess - Login");
            stage.setWidth(500);
            stage.setHeight(450);
            stage.centerOnScreen();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
