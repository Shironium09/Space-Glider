package game_files;

import javafx.scene.media.AudioClip;
import java.net.URL;


public class SoundFXManager {
 
    private AudioClip shoot;
    private AudioClip defeat;
    private AudioClip powerup;
    private AudioClip click;
    private AudioClip exit;
    private AudioClip gameover;
    private AudioClip damage;

    public SoundFXManager(){

        shoot = loadSound("/assets/music/shoot.mp3");
        defeat = loadSound("/assets/music/defeat.mp3");
        powerup = loadSound("/assets/music/powerup.mp3");
        click = loadSound("/assets/music/click.mp3");
        exit = loadSound("/assets/music/seeya.mp3");
        gameover = loadSound("/assets/music/gameoverfx.mp3");
        damage = loadSound("/assets/music/damage.mp3");
        

    }

        private AudioClip loadSound(String path){

        try{

            URL resource = getClass().getResource(path);
            if(resource == null){

                return null;

            }

            AudioClip clip = new AudioClip(resource.toExternalForm());

            clip.setVolume(0.2);
           
            return clip;

        }catch(Exception e){

            System.out.println("Error loading sound: " + e.getMessage());
            return null;

        }

    }

    public void shoot(){

        if(shoot != null){

            shoot.play();

        }

    }

    public void powerup(){

        if(powerup != null){

            powerup.play();

        }

    }

    public void defeat(){

        if(defeat != null){

            defeat.play();

        }

    }

    public void click(){

        if(click != null){

            click.play();

        }
    }

    public void exit(){

        if(exit != null){

            exit.play();

        }

    }

    public void gameover(){

        if(gameover != null){

            gameover.play();

        }

    }

    public void damage(){

        if(damage != null){

            damage.play();

        }   

    }

}
