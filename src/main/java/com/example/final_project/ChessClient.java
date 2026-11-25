package com.example.final_project;

import java.io.*;
import java.net.*;
import java.util.Date;

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


public class ChessClient extends Application {

    // Indicate whether the player has the turn
    private boolean myTurn = false;

    //Either Black or White
    private String playerColor;

    // Create and initialize a title label
    private Label lblTitle = new Label();

    // Create and initialize a status label
    private Label lblStatus = new Label();

    private DataInputStream fromServer;
    private DataOutputStream toServer;

    // Host name or ip
    private String host = "localhost";

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {

        GridPane pane = new GridPane();
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(lblTitle);
        borderPane.setCenter(pane);
        borderPane.setBottom(lblStatus);

        Scene scene = new Scene(borderPane, 320, 350);
        primaryStage.setTitle("ChessClient"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        // Connect to the server
        connectToServer();

    }

    private void connectToServer() {
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket(host, 8000);

            // Create an input stream to receive data from the server
            fromServer = new DataInputStream(socket.getInputStream());

            // Create an output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            lblStatus.setText("Cannot connect to server");
            return;
        }

        // Control the game on a separate thread
        new Thread(() -> {
            try {
                // Get notification from the server
                playerColor = fromServer.readUTF();

                // Am I player 1 or 2?
                if (playerColor.equals("WHITE")) {
                    Platform.runLater(() -> {
                        lblTitle.setText("Player 1 : WHITE");
                        lblStatus.setText("Waiting for player 2 to join");
                    });

                    // Receive startup notification from the server
                    fromServer.readUTF(); // Whatever read is ignored

                    // The other player has joined
                    Platform.runLater(() ->
                            lblStatus.setText("Player 2 has joined. WHITE start first"));

                    // It is my turn
                    myTurn = true;
                }
                else if (playerColor.equals("BLACK")) {
                    Platform.runLater(() -> {
                        lblTitle.setText("Player 2 : BLACK");
                        lblStatus.setText("Waiting for player 1 to move");
                    });
                }

//                // Continue to play
//                while (continueToPlay) {
//                    if (player == PLAYER1) {
//                        waitForPlayerAction(); // Wait for player 1 to move
//                        sendMove(); // Send the move to the server
//                        receiveInfoFromServer(); // Receive info from the server
//                    }
//                    else if (player == PLAYER2) {
//                        receiveInfoFromServer(); // Receive info from the server
//                        waitForPlayerAction(); // Wait for player 2 to move
//                        sendMove(); // Send player 2's move to the server
//                    }
//                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args){
        launch(args);
    }
}
