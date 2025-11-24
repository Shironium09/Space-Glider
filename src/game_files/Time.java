package game_files;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class Time {
    
    private Text time_text;
    private double time;

    public Time(){

        this.time = 0;

        this.time_text = new Text("Time: 0s");
        this.time_text.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        this.time_text.setFill(Color.WHITE);

    }

    public void update(){

        //There is approximately 0.0167 in every second in 60 FPS
        this.time += 0.0167;
        this.time_text.setText("Time: " + (int)this.time);
            
    }
    
    public void reset(){

        this.time = 0;
        this.time_text.setText("Time: 0");

    }

    public double getTime(){

        return this.time;

    }

    public Text getNode(){

        return this.time_text;

    }

}
