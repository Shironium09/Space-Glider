package game_files;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class Score{

    private Text score_text;
    private int current_score;

    public Score(){

        this.current_score = 0;

        this.score_text = new Text("Score: 0");
        this.score_text.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        this.score_text.setFill(Color.WHITE);

    }

    public void addScore(int points){

        this.current_score += points;
        this.score_text.setText("Score: " + this.current_score);

    }

    public void reset(){

        this.current_score = 0;
        this.score_text.setText("Score: " + this.current_score);

    }

    public int getScore(){

        return this.current_score;

    }

    public Text getNode(){

        return this.score_text;

    }
    
}
