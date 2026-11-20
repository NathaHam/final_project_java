package com.example.final_project;

import java.sql.*;
import com.github.bhlangonijr.chesslib.Board;

public class ChessStorage {

    private static Connection getConnection() throws SQLException {
        String dbUrl = "jdbc:sqlite:ChessStorage.db";
        Connection connection = DriverManager.getConnection(dbUrl);
        return connection;
    }

    public static void createTables() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS game (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fen TEXT )";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    public static void saveBoard(Board board) throws SQLException {

        String sql = "INSERT INTO game (fen) VALUES (?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, board.getFen());

            preparedStatement.execute();

        }
    }

    public static String loadBoard() throws SQLException {

        String sql = "SELECT fen FROM game ORDER BY id DESC LIMIT 1";
        String fen = null;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet fenQuery = preparedStatement.executeQuery();

            if (fenQuery.next()) {
                fen = fenQuery.getString("fen");
            }

            fenQuery.close();
        }
        return fen;
    }
}
