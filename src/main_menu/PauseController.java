package main_menu;

import javafx.fxml.FXML;
import game_files.Game;
import game_files.SoundFXManager;

public class PauseController{
    
    private Game game;
    private SoundFXManager fx = new SoundFXManager();

    public void setGame(Game game){

        this.game = game;

    }

    @FXML
    private void handleResume(){

        fx.click();
        game.resumeGame();

    }

    @FXML
    private void handleBackToMenu(){

        fx.click();
        game.goToMenu();

    }

}
