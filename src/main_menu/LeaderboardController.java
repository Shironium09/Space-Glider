package main_menu;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import game_files.Game;
import game_files.SoundFXManager;
import backend.DatabaseManager;
import java.util.List;

public class LeaderboardController {

    private Game game;
    private SoundFXManager fx = new SoundFXManager();

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

        fx.click();
        game.goToMenu();

    }
    
}
