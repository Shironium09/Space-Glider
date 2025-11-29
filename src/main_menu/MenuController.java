package main_menu;

import javafx.fxml.FXML;
import game_files.Game;
import game_files.SoundFXManager;
import game_files.SoundManager;

public class MenuController {
    
    private Game game;
    private SoundFXManager fx = new SoundFXManager();
    private SoundManager sound = new SoundManager();

    public void setGame(Game game){

        this.game = game;
        
    }

    @FXML
    private void handleStartButton(){

        fx.click();
        game.startGame();


    }

    @FXML
    private void handleLeaderboards(){

        fx.click();
        game.showLeaderboard();

    }
    
    @FXML
    private void handleExitButton(){
    

        fx.click();
        sound.stopCurrent();
        fx.exit();

        try{

            Thread.sleep(1500);

        }catch(InterruptedException e){

            Thread.currentThread().interrupt();
            System.out.println("Timeout Interrupted");

        }

        System.exit(0); 

    }
}