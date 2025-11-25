import game_files.Game;
import backend.DatabaseManager;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {

        DatabaseManager.initDB();

        Application.launch(Game.class, args);

    }
    
}   