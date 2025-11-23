package game_files;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;

public class Obstacle {
    
    private Node node;
    private double x;
    private double y;

    private double delta_x;
    private double delta_y;

    private double speed = 5.0;

    private static final double IMAGE_HEIGHT = 50.0;
    private static final double IMAGE_WIDTH = 50.0;

    public Obstacle(double window_width, double window_height) {

        Image OBSTACLE_IMAGE = new Image("file:src/assets/enemy-1-model.png");
        ImageView OBSTACLE_VIEW = new ImageView(OBSTACLE_IMAGE);

        OBSTACLE_VIEW.setFitWidth(IMAGE_WIDTH);
        OBSTACLE_VIEW.setFitHeight(IMAGE_HEIGHT);
        OBSTACLE_VIEW.setPreserveRatio(true);

        this.node = OBSTACLE_VIEW;

        this.node.setMouseTransparent(true);

        int side = (int)(Math.random()*4);
        double spawnBuffer = 50.0;

        if (side == 0) {

            this.x = Math.random() * window_width;
            this.y = -spawnBuffer;

        } else if (side == 1) {

            this.x = window_width + spawnBuffer;
            this.y = Math.random() * window_height;

        } else if (side == 2) {

            this.x = Math.random() * window_width;
            this.y = window_height + spawnBuffer;

        } else {

            this.x = -spawnBuffer;
            this.y = Math.random() * window_height;

        }

        double targetX = (Math.random()*(window_width * 0.6)) + (window_width * 0.2);
        double targetY = (Math.random()*(window_height * 0.6)) + (window_height * 0.2);

        double directionX = targetX - this.x;
        double directionY = targetY - this.y;

        double magnitude = Math.sqrt(directionX * directionX + directionY * directionY);

        double normalizedelta_x = directionX/magnitude;
        double normalizedelta_y = directionY / magnitude;

        double Speed = speed;
        this.delta_x = normalizedelta_x * Speed;
        this.delta_y = normalizedelta_y * Speed;

        this.node.setLayoutX(this.x);
        this.node.setLayoutY(this.y);
    }

    public void update(double playerX, double playerY) {

        double directionX = playerX - this.x;
        double directionY = playerY - this.y;

        double magnitude = Math.sqrt(directionX * directionX + directionY * directionY);

        if(magnitude > 0){

            double normalizedistance_x = directionX/magnitude;
            double normalizedistance_y = directionY / magnitude;

            this.delta_x = normalizedistance_x * speed;
            this.delta_y = normalizedistance_y * speed;

        }
        
        this.x += this.delta_x;
        this.y += this.delta_y;

        this.node.setLayoutX(this.x);
        this.node.setLayoutY(this.y);

    }

    public void face_player(double playerX, double playerY){

        double direction_x = playerX - this.x;
        double direction_y = playerY - this.y;

        double angle = Math.toDegrees(Math.atan2(direction_y, direction_x));

        this.node.setRotate(angle + 90);

    }

    public void updateSpeed(double newSpeed){

        this.speed = newSpeed;

    }

    public boolean isOffScreen(double windowWidth, double windowHeight) {

        return(this.x <= -100 || this.x > windowHeight + 100 ||
               this.y <= -100 || this.y > windowWidth + 100);

    }

    public Node getNode() {

        return this.node;

    }
    
    public double getX() {

        return this.x;

    }

    public double getY() {

        return this.y;

    }

}
