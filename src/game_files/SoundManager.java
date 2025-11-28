package game_files;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class SoundManager{

    private MediaPlayer menuMusic;
    private MediaPlayer gameMusic;
    private MediaPlayer gameOverMusic;

    private MediaPlayer current;

    public SoundManager(){

        menuMusic = loadSound("/assets/music/menu.mp3");
        gameMusic = loadSound("/assets/music/game.mp3");
        gameOverMusic = loadSound("/assets/music/gameover.mp3");

    }

    private MediaPlayer loadSound(String path){

        try{

            URL resource = getClass().getResource(path);
            if(resource == null){

                return null;

            }

            Media media = new Media(resource.toExternalForm());
            MediaPlayer player = new MediaPlayer(media);

            player.setVolume(0.1);

            player.setCycleCount(MediaPlayer.INDEFINITE);
           
            return player;

        }catch(Exception e){

            System.out.println("Error loading sound: " + e.getMessage());
            return null;

        }

    }
    
    public void playMenuMusic(){

        stopCurrent();
        if(menuMusic != null){

            menuMusic.play();
            current = menuMusic;

        }

    }

    public void playGameMusic(){

        stopCurrent();
        if(gameMusic != null){

            gameMusic.play();
            current = gameMusic;

        }

    }

    public void playGameOverMusic(){

        stopCurrent();
        if(gameOverMusic != null){

            gameOverMusic.play();
            current = gameOverMusic;

        }

    }

    public void stopAll(){

        stopCurrent();

    }

    public void stopCurrent(){

        if(current != null){

            current.stop();

        }

    }
    
}
