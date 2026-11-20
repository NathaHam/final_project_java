package com.example.final_project;

import java.time.LocalDateTime;

public class User{

    private int id;
    private int username;
    private boolean connected;
    private LocalDateTime dateCreated;

    private int gamesPlayed;
    private int wins;
    private int losses;
    private int draws;

    public User(int id, int username, boolean connected, LocalDateTime dateCreated) {
        this.id = id;
        this.username = username;
        this.connected = connected;
        this.dateCreated = dateCreated;
        this.gamesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsername() {
        return username;
    }

    public void setUsername(int username) {
        this.username = username;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public void recordWins(){
        wins++;
        gamesPlayed++;

    }

    public void recordLosses(){
        losses++;
        gamesPlayed++;
    }

    public void recordDraws(){
        draws++;
        gamesPlayed++;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username=" + username +
                ", connected=" + connected +
                ", dateCreated=" + dateCreated +
                ", gamesPlayed=" + gamesPlayed +
                ", wins=" + wins +
                ", losses=" + losses +
                ", draws=" + draws +
                '}';
    }
}