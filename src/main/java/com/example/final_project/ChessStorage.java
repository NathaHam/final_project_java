package com.example.final_project;

import java.sql.*;
import com.github.bhlangonijr.chesslib.Board;

public class ChessStorage {

    private static Connection getConnection() throws SQLException {
        String dbUrl = "jdbc:sqlite:ChessStorage.db";
        Connection connection = DriverManager.getConnection(dbUrl);
        connection.createStatement().execute("PRAGMA foreign_keys = ON"); // i got this one line from chatgpt as a solution to a bunch of errors i was having
        return connection;
    }

    public static void createTables() throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS board (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fen TEXT )";

        String usersTable = """
                CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                date_created TEXT NOT NULL,
                games_played INTEGER DEFAULT 0,
                wins INTEGER DEFAULT 0,
                losses INTEGER DEFAULT 0,
                draws INTEGER DEFAULT 0
                )
                """;

        String gamesTable = """
                CREATE TABLE IF NOT EXISTS game (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                white_player_id INTEGER,
                black_player_id INTEGER,
                fen TEXT,
                status TEXT DEFAULT 'In_Progress',
                result TEXT,
                date_created TEXT DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (white_player_id) REFERENCES users(id),
                FOREIGN KEY (black_player_id) REFERENCES users(id)
                )
                """;

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {

            statement.execute(usersTable);
            statement.execute(gamesTable);
            statement.execute(sql);
        }
    }

    public static boolean registerUser(String username) throws SQLException{

        String sql = """
                INSERT INTO users (username, date_created)
                VALUES (?, datetime('now'))
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,username.trim());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e){

            if(e.getErrorCode() == 19){
                return false;
            }
            throw e;
        }
    }

    public static User loginUser(String username) throws SQLException {

        String sql = """
                SELECT * FROM users
                WHERE username = ?
                """;
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, username.trim());

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){

                User user = new User(
                    rs.getInt("id"),
                            rs.getString("username"),
                            true,
                            rs.getTimestamp("date_created")
                                    .toLocalDateTime()
                );
                user.setGamesPlayed(rs.getInt("games_played"));
                user.setWins(rs.getInt("wins"));
                user.setLosses(rs.getInt("losses"));
                user.setDraws(rs.getInt("draws"));
                return user;
            }
        }
        return null;
    }
    public static void saveBoard(Board board) throws SQLException {

        String sql = "INSERT INTO board (fen) VALUES (?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, board.getFen());

            preparedStatement.execute();

        }
    }

    public static String loadBoard() throws SQLException {

        String sql = "SELECT fen FROM board ORDER BY id DESC LIMIT 1";
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
