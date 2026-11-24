package com.example.final_project;

import java.io.*;
import java.net.*;
import java.util.Date;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ChessServer extends Application {

    private int sessionNo = 1; // Number a session

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        TextArea taLog = new TextArea();

        // Create a scene and place it in the stage
        Scene scene = new Scene(new ScrollPane(taLog), 450, 200);
        primaryStage.setTitle("ChessServer"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
        new Thread( () -> {
            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(8000);
                Platform.runLater(() -> taLog.appendText(new Date() +
                        ": Server started at socket 8000\n"));

                // Ready to create a session for every two players
                while(true){
                    Platform.runLater(() -> taLog.appendText(new Date() +
                            ": Wait for players to join session " + sessionNo + '\n'));

                    //Connect to whitePlayer
                    Socket whitePlayer = serverSocket.accept();

                    Platform.runLater(() -> {
                        taLog.appendText(new Date() + ": White Player has joined session "
                                + sessionNo + '\n');
                        taLog.appendText("White Player's IP address" +
                                whitePlayer.getInetAddress().getHostAddress() + '\n');
                    });

                    // Notify that the player is White Player
                    new DataOutputStream(
                            whitePlayer.getOutputStream()).writeUTF("WHITE");

                    // Connect to blackPlayer
                    Socket blackPlayer = serverSocket.accept();

                    Platform.runLater(() -> {
                        taLog.appendText(new Date() +
                                ": Black player has joined session " + sessionNo + '\n');
                        taLog.appendText("Black player's IP address" +
                                blackPlayer.getInetAddress().getHostAddress() + '\n');
                    });

                    // Notify that the player is Black player
                    new DataOutputStream(
                            blackPlayer.getOutputStream()).writeUTF("BLACK");

                    // Display this session and increment session number
                    Platform.runLater(() ->
                            taLog.appendText(new Date() +
                                    ": Start a thread for session " + sessionNo++ + '\n'));

                    // Launch a new thread for this session of two players
                    new Thread(new HandleASession(whitePlayer, blackPlayer)).start();
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }).start();
    }

    // Define the thread class for handling a new session for two players
    class HandleASession implements Runnable {

        private Socket whitePlayer;
        private Socket blackPlayer;

        private DataInputStream fromWhitePlayer;
        private DataOutputStream toWhitePlayer;
        private DataInputStream fromBlackPlayer;
        private DataOutputStream toBlackPlayer;

        private ChessGame chessGame;
        private Board board = new Board();

        // Continue to play
        private boolean continueToPlay = true;

        /** Construct a thread */
        public HandleASession(Socket whitePlayer, Socket blackPlayer) {
            chessGame = new ChessGame(0, null, null);
            this.whitePlayer = whitePlayer;
            this.blackPlayer = blackPlayer;
        }

        /** Implement the run() method for the thread */
        public void run() {
            try {
                // Create data input and output streams
                fromWhitePlayer = new DataInputStream(
                        whitePlayer.getInputStream());
                toWhitePlayer = new DataOutputStream(
                        whitePlayer.getOutputStream());
                fromBlackPlayer = new DataInputStream(
                        blackPlayer.getInputStream());
                toBlackPlayer = new DataOutputStream(
                        blackPlayer.getOutputStream());

                // Write anything to notify player 1 to start
                // This is just to let player 1 know to start
                toWhitePlayer.writeUTF("START");

                // Continuously serve the players and determine and report
                // the game status to the players
//                while (true) {
//                    // Receive a move from player 1
//                    int row = fromPlayer1.readInt();
//                    int column = fromPlayer1.readInt();
//                    cell[row][column] = 'X';
//
//                    // Check if Player 1 wins
//                    if (isWon('X')) {
//                        toPlayer1.writeInt(PLAYER1_WON);
//                        toPlayer2.writeInt(PLAYER1_WON);
//                        sendMove(toPlayer2, row, column);
//                        break; // Break the loop
//                    }
//                    else if (isFull()) { // Check if all cells are filled
//                        toPlayer1.writeInt(DRAW);
//                        toPlayer2.writeInt(DRAW);
//                        sendMove(toPlayer2, row, column);
//                        break;
//                    }
//                    else {
//                        // Notify player 2 to take the turn
//                        toPlayer2.writeInt(CONTINUE);
//
//                        // Send player 1's selected row and column to player 2
//                        sendMove(toPlayer2, row, column);
//                    }
//
//                    // Receive a move from Player 2
//                    row = fromPlayer2.readInt();
//                    column = fromPlayer2.readInt();
//                    cell[row][column] = 'O';
//
//                    // Check if Player 2 wins
//                    if (isWon('O')) {
//                        toPlayer1.writeInt(PLAYER2_WON);
//                        toPlayer2.writeInt(PLAYER2_WON);
//                        sendMove(toPlayer1, row, column);
//                        break;
//                    }
//                    else {
//                        // Notify player 1 to take the turn
//                        toPlayer1.writeInt(CONTINUE);
//
//                        // Send player 2's selected row and column to player 1
//                        sendMove(toPlayer1, row, column);
//                    }
//                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }

        /** Determine the winner */
        private String isWon() {

            if (board.isMated()){
                chessGame.setStatus("Finished");
                Side loserSide = board.getSideToMove();
                if (loserSide.equals(Side.BLACK)){
                    chessGame.setResult("WHITE win!");
                }
                else{
                    chessGame.setResult("BLACK win!");
                }

            }

            if (board.isDraw()){
                chessGame.setStatus("Finished");
                chessGame.setResult("Draw!");
            }

            if (chessGame.isFinished()){
                return chessGame.getResult();
            }

            /** All checked, but no winner */
            return null;
        }
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */

    public static void main(String[] args){
        launch(args);
    }
}
