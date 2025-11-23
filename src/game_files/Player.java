package game_files;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;

//TODO: Different levels of player characters

public class Player {
    
    private Node node;
    private double x;
    private double y;

    private static final double MOVE_SPEED = 7.0;

    private static final double IMAGE_WIDTH = 80.0;
    private static final double IMAGE_HEIGHT = 80.0;

    public Player() {

        Image PLAYER_IMAGE = new Image("file:src/assets/player-model.png");
        ImageView PLAYER_VIEW = new ImageView(PLAYER_IMAGE);

        PLAYER_VIEW.setFitWidth(IMAGE_WIDTH);
        PLAYER_VIEW.setFitHeight(IMAGE_HEIGHT);
        PLAYER_VIEW.setPreserveRatio(true);

        this.node = PLAYER_VIEW;

        this.node.setMouseTransparent(true);

        this.x = 0;
        this.y = 0;

    }

    //Player Model Getter

    public Node getNode(){

        return this.node;

    }

    //Player Position Setter

    public void setPos(double NewX, double NewY){

        this.x = NewX;
        this.y = NewY;
        this.node.setLayoutX(this.x - (IMAGE_WIDTH/2));
        this.node.setLayoutY(this.y - (IMAGE_HEIGHT/2));

    }

    //Direction Facing Function

    public void face_direction(double directionX, double directionY){

        double direction_x = directionX - this.x;
        double direction_y = directionY - this.y;

        double angle = Math.toDegrees(Math.atan2(direction_y, direction_x));

        this.node.setRotate(angle + 90);

    }

    //Movement Functions

    public void moveUp() {

        this.y -= MOVE_SPEED; 
        this.node.setLayoutY(this.y- (IMAGE_HEIGHT/2));

    }
    
    public void moveDown() {

        this.y += MOVE_SPEED; 
        this.node.setLayoutY(this.y- (IMAGE_HEIGHT/2));

    }

    public void moveLeft() {

        this.x -= MOVE_SPEED;
        this.node.setLayoutX(this.x - (IMAGE_WIDTH/2));

    }

    public void moveRight() {

        this.x += MOVE_SPEED;
        this.node.setLayoutX(this.x - (IMAGE_WIDTH/2));

    }

    //Getters for Postion

    public double getX() {

        return this.x;

    }

    public double getY() {

        return this.y;

    }

}
