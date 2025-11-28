package game_files;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PowerUp {
    
    private Node node;
    private double x;
    private double y;

    //0 for health, 1 for bullet
    private int type;

    private static final double IMAGE_WIDTH = 50.0;
    private static final double IMAGE_HEIGHT = 50.0;

    public PowerUp(double windowWidth, double windowHeight){

        this.type = (Math.random() < 0.5) ? 0 : 1;

        Image PLAYER_IMAGE;

        if(this.type == 1){

            PLAYER_IMAGE = new Image("file:src/assets/shoot.png");

        }else{

            PLAYER_IMAGE = new Image("file:src/assets/health.png");

        }

        ImageView PLAYER_VIEW = new ImageView(PLAYER_IMAGE);

        PLAYER_VIEW.setFitWidth(IMAGE_WIDTH);
        PLAYER_VIEW.setFitHeight(IMAGE_HEIGHT);
        PLAYER_VIEW.setPreserveRatio(true);

        this.node = PLAYER_VIEW;
        this.node.setMouseTransparent(true);

        this.x = Math.random() * (windowWidth - 100) + 50;
        this.y = Math.random() * (windowHeight - 100) + 50;

        this.node.setLayoutX(this.x);
        this.node.setLayoutY(this.y);

    }

    public Node getNode(){

        return this.node;

    }

    public double getX(){

        return this.x;

    }

    public double getY(){

        return this.y;

    }

    public int getType(){

        return this.type;
        
    }

}
