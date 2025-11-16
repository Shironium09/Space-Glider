package main_menu;

import javafx.fxml.FXML;
import game_files.Game;

public class MenuController {
    
    private Game game;

    public void setGame(Game game){

        this.game = game;
        
    }

    @FXML
    private void handleStartButton(){

        if(game != null){

            game.startGame();

        }

    }
    
    @FXML
    private void handleExitButton(){

        System.exit(0); 

    }
}