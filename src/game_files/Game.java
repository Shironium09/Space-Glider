package game_files;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main_menu.MenuController;
import javafx.animation.AnimationTimer;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Game extends Application {

    //Window Dimensions (Might change later, idk)
    private static final double WINDOW_LENGTH = 1000;
    private static final double WINDOW_HEIGHT = 700;

    //Game Loop Declaration
    private AnimationTimer gameLoop;
    
    //Create the Player Model and Pane(or the window, whatever you wanna call it)
    private Player player;
    private Pane gamePane;
    private Scene scene;

    //This is for the movement and mouse !!!
    private HashSet<KeyCode> keysPressed = new HashSet<>();
    private HashSet<MouseButton> mouseButtonsPressed = new HashSet<>();

    //This is for the obstacles (put them in array)
    private List<Obstacle> obstacles = new ArrayList<>();

    //This is for the Bullets
    private List<Bullet> bullets = new ArrayList<>();

    //Spawn Rate for the obstacles
    private double spawnRate = 3.0;
    private double spawnInterval = (10.0*60.0) / spawnRate;
    private int spawnTimer = 0;

    //Spawn rate interval (spawn rate increases overtime)
    private int scalingTimer = 0;
    private static final int SCALING_INTERVAL_FRAMES = 10*60;
    private static final double SPAWN_RATE_INCREASE = 1.0;

    //For the bullet rate
    private double bulletRate = 1.0;
    private double bulletInterval = (3.0*60.0) / bulletRate;
    private int bulletTimer = 0;


    //For mouse directions
    private double mouse_x_position;
    private double mouse_y_position;

    @Override
    public void start(Stage primaryStage){

        gamePane = new Pane();
        gamePane.setId("root_pane");

        try{

            URL fxmlUrl = getClass().getResource("/main_menu/menu.fxml");            
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            VBox menuPane = loader.load();

            MenuController menuController = loader.getController();
            menuController.setGame(this);

            scene = new Scene(menuPane, WINDOW_LENGTH, WINDOW_HEIGHT);

            scene.setOnKeyPressed(event -> {

                keysPressed.add(event.getCode());

            });

            scene.setOnKeyReleased(event -> {

                keysPressed.remove(event.getCode());

            });

            scene.setOnMouseMoved(event -> {

                mouse_x_position = event.getX();
                mouse_y_position = event.getY();

            });

            scene.setOnMousePressed(event -> mouseButtonsPressed.add(event.getButton()));
            scene.setOnMouseReleased(event -> mouseButtonsPressed.remove(event.getButton()));

            scene.getStylesheets().add(getClass().getResource("/css/game.css").toExternalForm());
            primaryStage.setTitle("Space Glider");
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch(IOException e){

            e.printStackTrace();
            System.err.println("Error Loading the FXML File");

        }

    }
 
    public void startGame(){

        player = new Player();
        player.setPos(WINDOW_LENGTH/2, WINDOW_HEIGHT/2);

        obstacles.clear();
        bullets.clear();
        gamePane.getChildren().clear();

        gamePane.getChildren().add(player.getNode());

        initializeGameLoop();
        gameLoop.start();

        scene.setRoot(gamePane);

    }

    private void initializeGameLoop(){

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                
                if (keysPressed.contains(KeyCode.W)){player.moveUp();}
                if (keysPressed.contains(KeyCode.S)){player.moveDown();}
                if (keysPressed.contains(KeyCode.A)){player.moveLeft();}
                if (keysPressed.contains(KeyCode.D)){player.moveRight();}

                player.face_direction(mouse_x_position, mouse_y_position);

                spawnTimer++;
                bulletTimer++;

                if(bulletTimer >= bulletInterval){

                    Bullet newBullet = new Bullet(player.getX(), player.getY(), mouse_x_position, mouse_y_position);
                    bullets.add(newBullet);
                    gamePane.getChildren().add(newBullet.getNode());
                    bulletTimer = 0;

                }

                Iterator<Bullet> bulletIterator = bullets.iterator();

                while(bulletIterator.hasNext()){

                    Bullet bullet = bulletIterator.next();

                    bullet.update();
                    double bullet_x = bullet.getX();
                    double bullet_y = bullet.getY();

                    boolean bullet_hit = false;

                    Iterator<Obstacle> obstactsIterator = obstacles.iterator();

                    while(obstactsIterator.hasNext()){

                        Obstacle obstacle = obstactsIterator.next();

                        double delta_x = bullet_x - obstacle.getX();
                        double delta_y = bullet_y - obstacle.getY();

                        double distance = Math.sqrt(delta_x * delta_x + delta_y * delta_y);

                        if(distance < 30){

                            gamePane.getChildren().remove(bullet.getNode());
                            gamePane.getChildren().remove(obstacle.getNode());
                            bulletIterator.remove();
                            obstactsIterator.remove();
                            bullet_hit = true;
                            break;

                        }

                    }

                    if(!bullet_hit && bullet.isOffScreen(WINDOW_LENGTH, WINDOW_HEIGHT)){

                        gamePane.getChildren().remove(bullet.getNode());
                        bulletIterator.remove();

                    }

                }

                if(spawnTimer >= spawnInterval){

                    Obstacle newObstacle = new Obstacle(WINDOW_LENGTH, WINDOW_HEIGHT);
                    obstacles.add(newObstacle);
                    gamePane.getChildren().add(newObstacle.getNode());
                    spawnTimer = 0;

                }

                Iterator<Obstacle> obstacleIterator = obstacles.iterator();

                while(obstacleIterator.hasNext()){
                    
                    Obstacle obstacle = obstacleIterator.next();

                    double player_x = player.getX();
                    double player_y = player.getY();

                    obstacle.update(player_x, player_y);

                    double obstacle_x = obstacle.getX();
                    double obstacle_y = obstacle.getY();

                    double distance_x_player_obstacle = player_x - obstacle_x;
                    double distance_y_player_obstacle = player_y - obstacle_y;

                    obstacle.face_player(player_x, player_y);

                    double distance_player_obstacle = Math.sqrt(distance_x_player_obstacle * distance_x_player_obstacle+ distance_y_player_obstacle * distance_y_player_obstacle);
            
                    if(distance_player_obstacle < 50){

                        gamePane.getChildren().remove(player.getNode());
                        gameLoop.stop();

                        System.out.println("Game Over!");

                    }

                    if(obstacle.isOffScreen(WINDOW_LENGTH, WINDOW_HEIGHT)){

                        gamePane.getChildren().remove(obstacle.getNode());
                        obstacleIterator.remove();

                    }

                }

                scalingTimer++;

                if(scalingTimer >= SCALING_INTERVAL_FRAMES){

                    spawnRate += SPAWN_RATE_INCREASE;
                    spawnInterval = (10.0*60.0) / spawnRate;
                    scalingTimer = 0;

                }

            }
        };

    }

}
