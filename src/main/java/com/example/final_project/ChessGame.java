package com.example.final_project;

import java.time.LocalDateTime;
import java.util.*;

public class ChessGame {

    private int gameId;

    private User WhitePlayer;
    private User BlackPlayer;

    private LocalDateTime dateCreated;
    private LocalDateTime dateEnded;

    private String status;
    private String result;

    private String fen;

    private int moveNumber;

    private List<String> moveHistory;


    public ChessGame(int gameId, User whitePlayer, User blackPlayer) {
        this.gameId = gameId;

        WhitePlayer = whitePlayer;
        BlackPlayer = blackPlayer;

        this.dateCreated = LocalDateTime.now();
        this.dateEnded = null;

        this.status = "In_Progress";
        this.result = "In_Progress";

        this.fen = null;

        this.moveNumber = 0;
        this.moveHistory = new ArrayList<>();
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public User getWhitePlayer() {
        return WhitePlayer;
    }

    public void setWhitePlayer(User whitePlayer) {
        WhitePlayer = whitePlayer;
    }

    public User getBlackPlayer() {
        return BlackPlayer;
    }

    public void setBlackPlayer(User blackPlayer) {
        BlackPlayer = blackPlayer;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateEnded() {
        return dateEnded;
    }

    public void setDateEnded(LocalDateTime dateEnded) {
        this.dateEnded = dateEnded;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFen() {
        return fen;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public List<String> getMoveHistory() {
        return moveHistory;
    }

    public void setMoveHistory(List<String> moveHistory) {
        this.moveHistory = moveHistory;
    }

    public void addMove(String move){
        moveHistory.add(move);
        moveNumber++;
    }
    public boolean isFinished(){
        return "Finished".equals(status);
    }
}
