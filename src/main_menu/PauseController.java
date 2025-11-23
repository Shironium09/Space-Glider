package main_menu;
import javafx.fxml.FXML;
import game_files.Game;

public class PauseController{
    
    private Game game;

    public void setGame(Game game){

        this.game = game;

    }

    @FXML
    private void handleResume(){

        game.resumeGame();

    }

    @FXML
    private void handleBackToMenu(){

        if(game != null){

            game.goToMenu();

        }

    }

}
