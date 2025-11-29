package main_menu;

import javafx.fxml.FXML;
import game_files.Game;
import game_files.SoundFXManager;

public class GameOverController {
    
    private Game game;
    private SoundFXManager fx = new SoundFXManager();

    public void setGame(Game game){

        this.game = game;

    }

    @FXML
    private void handleRetry(){

        fx.click();
        game.removeRetryScreen();
        game.startGame();

    }

    @FXML
    private void handleMenu(){

        fx.click();
        game.removeRetryScreen();
        game.goToMenu();

    }

}
