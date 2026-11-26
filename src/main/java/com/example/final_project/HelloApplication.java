package com.example.final_project;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        /* stage.setScene(new Scene(new BoardView()));
        stage.show(); -> was using this for board UI testing */

        // orginal code
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();

        ChessStorage.createTables();

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));

        Scene scene = new Scene(loader.load());

        stage.setTitle("Java Chess - Login");
        stage.setScene(scene);

        stage.setWidth(500);
        stage.setHeight(450);

        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
