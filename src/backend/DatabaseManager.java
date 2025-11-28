package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:spaceglider.db";

    public static void initDB(){

        String sql = "CREATE TABLE IF NOT EXISTS scores ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT NOT NULL,"
                    + "score INTEGER NOT NULL,"
                    + "time DOUBLE NOT NULL,"
                    + "date_played TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                    + ");";

        try(Connection con = DriverManager.getConnection(URL); 
            Statement stmt = con.createStatement()){

                stmt.execute(sql);
                System.out.println("Database Connected");

        }catch(SQLException e){

            System.out.println("Error: " + e.getMessage());

        }

    }

    public static void SaveData(String name, int score, double time){

        String sql = "INSERT INTO scores(name, score, time) VALUES(?, ?, ?)";

        try(Connection con = DriverManager.getConnection(URL);
            PreparedStatement pstmt = con.prepareStatement(sql)){

                pstmt.setString(1, name);
                pstmt.setInt(2, score);
                pstmt.setDouble(3, time);

                pstmt.executeUpdate();
                System.out.println("Saved in Database: " + name + " " + score + " " + time);

        }catch(SQLException e){

            System.out.println("Error saving in DB: " + e);

        }

    }
    
    public static List<String> getFormattedScores(){

        List<String> results = new ArrayList<>();

        String sql = "SELECT name, score, time FROM scores ORDER BY score DESC LIMIT 5";

        try(Connection con = DriverManager.getConnection(URL);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

                int rank = 1;

                while(rs.next()){

                    String name = rs.getString("name");
                    int score = rs.getInt("score");
                    double time = rs.getDouble("time");

                    String entry = String.format("%d. %s - %d pts (%.1fs)", rank, name, score, time);
                    results.add(entry);
                    rank++;

                }

            }catch(SQLException e){

                System.out.println("Error fetching scores: " + e.getMessage());

            }
    
    return results;
    }

}
