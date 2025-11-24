package main_menu;

import javafx.fxml.FXML;
import game_files.Game;

public class GameOverController {
    
    private Game game;

    public void setGame(Game game){

        this.game = game;

    }

    @FXML
    private void handleRetry(){

        game.removeRetryScreen();
        game.startGame();

    }

    @FXML
    private void handleMenu(){

        game.removeRetryScreen();
        game.goToMenu();

    }

}
