package com.example.lasertagproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class PostgresConnection {
    private String url;
    private String username;
    private String password;
    private static Connection connection;
    private static PostgresConnection instance;

    public static PostgresConnection getInstance(){
        if (instance == null) {
            String url = "jdbc:postgresql://db.mphzfoxdwxmdbxykkdad.supabase.co:5432/postgres?user=postgres&password=laserHogs2023";
            String username = "postgres";
            String password = "laserHogs2023";
            instance = new PostgresConnection(url, username, password);
        }
        return instance;
    }

    public PostgresConnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;

        try {
            // create a new connection to the database
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addIDAndCodename(int id, String codename) {
        try {
            // create a prepared statement with a parameterized SQL query
            PreparedStatement statement = connection.prepareStatement("INSERT INTO player (id, codename) VALUES (?, ?)");
            connection.prepareStatement("SELECT * FROM player WHERE id = 'id';");

            // set the parameter values for the statement
            statement.setInt(1, id);
            statement.setString(2, codename);

            // execute the statement to insert the data into the table
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " rows inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addToRedTable(int id, String codename){
        try {
            editIndexColumnRedTable();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO red_team (id, codename) VALUES (?, ?)");
//            connection.prepareStatement("SELECT * FROM player WHERE id = 'id';");

            // set the parameter values for the statement
            statement.setInt(1, id);
            statement.setString(2, codename);

            // execute the statement to insert the data into the table
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " rows inserted to red team.");
            editIndexColumnRedTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addToGreenTable(int id, String codename){
        try {
            editIndexColumnGreenTable();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO green_team (id, codename) VALUES (?, ?)");
//            connection.prepareStatement("SELECT * FROM player WHERE id = 'id';");

            // set the parameter values for the statement
            statement.setInt(1, id);
            statement.setString(2, codename);

            // execute the statement to insert the data into the table
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " rows inserted to green team.");
            editIndexColumnGreenTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editIndexColumnGreenTable() throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE green_team SET index = new_index " +
                    "FROM (" +
                    "  SELECT id, ROW_NUMBER() OVER (ORDER BY id) AS new_index" +
                    "  FROM green_team" +
                    ") AS subquery " +
                    "WHERE green_team.id = subquery.id;");
            int affectedRows = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void editIndexColumnRedTable() throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE red_team SET index = new_index " +
                    "FROM (" +
                    "  SELECT id, ROW_NUMBER() OVER (ORDER BY id) AS new_index" +
                    "  FROM red_team" +
                    ") AS subquery " +
                    "WHERE red_team.id = subquery.id;");
            int affectedRows = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static ObservableList<Player> getGreenTeamPlayer(int playerId) {
        ObservableList<Player> playerList = null;
        try {
            String sql = "SELECT id, codename "
                    + "FROM green_team "
                    + "WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1,playerId);
            ResultSet sqlReturnValues = stmt.executeQuery();
            if(sqlReturnValues != null) {
                playerList = getPlayerList(sqlReturnValues);
            }

        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
        return playerList;
    }


    public  static int getIndexFromGreenTable(int playerId){
        int index = 0;
        try {
            String sql = "SELECT index FROM green_team WHERE id = '" +playerId+"'";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) { // Call next() method to move cursor to first row
                index = rs.getInt("index");
                System.out.println("Value of index: " + index);
            }
            rs.close();
            stmt.close();
        }  catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return index;
    }


    public  static int getIndexFromRedTable(int playerId){
        int index = 0;
        try {
            String sql = "SELECT index FROM red_team WHERE id = '" +playerId+"'";
            PreparedStatement stmt = connection.prepareStatement(sql);
//            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) { // Call next() method to move cursor to first row
                index = rs.getInt("index");
                System.out.println("Value of index: " + index);
            }
            rs.close();
            stmt.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return index;
    }


    public static ObservableList<Player> getRedTeamPlayer(int playerId) {
        ObservableList<Player> playerList = null;
        try {
            String sql = "SELECT id, codename "
                    + "FROM red_team "
                    + "WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1,playerId);
            ResultSet sqlReturnValues = stmt.executeQuery();
            if(sqlReturnValues != null) {
                playerList = getPlayerList(sqlReturnValues);
            }

        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
        return playerList;
    }


    public static ObservableList<Player> searchRedPlayers() throws SQLException, ClassNotFoundException {
        try {
            PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT * FROM red_team;");

            ResultSet rsPlayers = selectStatement.executeQuery();
            ObservableList<Player> playerList = getPlayerList(rsPlayers);
            return playerList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }

    public static ObservableList<Player> searchGreenPlayers() throws SQLException, ClassNotFoundException {
        try {
            PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT * FROM green_team;");
            ResultSet rsPlayers = selectStatement.executeQuery();
            ObservableList<Player> playerList = getPlayerList(rsPlayers);
            return playerList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }

    private static ObservableList<Player> getPlayerList(ResultSet rs) throws SQLException, ClassNotFoundException {

        ObservableList<Player> playerList = FXCollections.observableArrayList();
        while (rs.next()) {
            Player player = new Player();
            player.setId(rs.getInt("id"));
            player.setCodename(rs.getString("codename"));
            playerList.add(player);
        }
        return playerList;
    }

}
