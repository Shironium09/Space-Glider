package game_files;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;

public class Bullet {
    
    private double x;
    private double y;

    private double delta_x;
    private double delta_y;

    private static final double SPEED = 10.0;

    private static final double IMAGE_HEIGHT = 60.0;
    private static final double IMAGE_WIDTH = 60.0;

    private Node node;

    public Bullet(double startX, double startY, double targetX, double targetY){

        Image BULLET_IMAGE = new Image("file:src/assets/player-bullet.png");
        ImageView BULLET_VIEW = new ImageView(BULLET_IMAGE);

        BULLET_VIEW.setFitWidth(IMAGE_WIDTH);
        BULLET_VIEW.setFitHeight(IMAGE_HEIGHT);
        BULLET_VIEW.setPreserveRatio(true);

        this.node = BULLET_VIEW;

        this.x = startX;
        this.y = startY;

        this.node.setLayoutX(this.x - (IMAGE_WIDTH/2));
        this.node.setLayoutY(this.y - (IMAGE_HEIGHT/2));

        double direction_x = targetX - this.x;
        double direction_y = targetY - this.y;

        double magnitude = Math.sqrt(direction_x * direction_x + direction_y * direction_y);

        if(magnitude == 0){

            this.delta_x = 0;
            this.delta_y = -SPEED;

        }else{

            double normalized_x = direction_x/magnitude;
            double normalized_y = direction_y/magnitude;

            this.delta_x = normalized_x * SPEED;
            this.delta_y = normalized_y * SPEED;

        }

    }

    public void update(){

        this.x += this.delta_x;
        this.y += this.delta_y;

        this.node.setLayoutX(this.x - (IMAGE_WIDTH/2));
        this.node.setLayoutY(this.y - (IMAGE_HEIGHT/2));

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

    public boolean isOffScreen(double windowWidth, double windowHeight){

        return(this.x <= 0 || this.x > windowWidth ||
               this.y <= 0 || this.y > windowHeight);

    }

}
