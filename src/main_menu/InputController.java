package main_menu;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import game_files.Game;
import game_files.Player;
import backend.DatabaseManager;

public class InputController {
    
    private Game game;
    private Player player;

    @FXML
    private TextField nameField;

    public void setData(Game game, Player player){

        this.game = game;
        this.player = player;

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue.length() > 4){

                nameField.setText(oldValue);

            }

        });

    }

    @FXML
    private void handleConfirm(){

        String name = nameField.getText().trim();

        if(name.isEmpty()){

            name = "NULL";

        }

        name = name.toUpperCase();

        player.setName(name);
        System.out.println("--- RUN DETAILS ---");
        System.out.println("Pilot: " + player.getName());
        System.out.println("Score: " + player.getScore());
        System.out.println("Time:  " + player.getTime());
        System.out.println("-------------------");
        
        game.setPlayerName(name);

        DatabaseManager.SaveData(player.getName(), player.getScore(), player.getTime());

        game.showRetryScreen();
            
    }

}
