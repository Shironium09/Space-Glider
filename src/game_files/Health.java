package game_files;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class Health {
    
    private Text text;
    private int hp;

    private final int MAX_HP = 100;
    private static final int DAMAGE = 20;
    private static final int HEAL = 10;

    public Health(){

        this.hp = MAX_HP;
        this.text = new Text("HP: " + this.hp);
        
        this.text.setFont(Font.font("Press Start 2P", 20)); 
        this.text.setFill(Color.LIGHTGREEN);

    }

    public void takeDamage(){

        this.hp -= DAMAGE;
        if (this.hp < 0) this.hp = 0;
        updateText();

    }

    public void heal(){

        this.hp += HEAL;
        if (this.hp > MAX_HP) this.hp = MAX_HP;
        updateText();

    }

    public int getHP(){

        return this.hp;

    }

    public Text getNode(){

        return this.text;

    }

    private void updateText(){

        this.text.setText("HP: " + this.hp);
        
        if (this.hp <= 30){

            this.text.setFill(Color.RED);

        }else{

            this.text.setFill(Color.LIGHTGREEN);

        }
    }

}
