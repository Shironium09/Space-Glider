package main_menu;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import game_files.Game;
import backend.DatabaseManager;
import java.util.List;

public class LeaderboardController {

    private Game game;

    @FXML
    private ListView<String> scoreList;

    public void setGame(Game game){

        this.game = game;
        loadScores();

    }

    private void loadScores(){

        List<String> scores = DatabaseManager.getFormattedScores();

        scoreList.getItems().addAll(scores);

    }

    @FXML
    private void handleBack(){

        game.goToMenu();

    }
    
}
