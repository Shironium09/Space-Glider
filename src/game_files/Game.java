package game_files;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;
import javafx.stage.Stage;
import main_menu.MenuController;
import main_menu.PauseController;
import javafx.animation.AnimationTimer;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.application.Platform;


public class Game extends Application {

    //Window Dimensions (Might change later, idk)
    private static final double WINDOW_LENGTH = 900;
    private static final double WINDOW_HEIGHT = 700;

    //Game Loop Declaration
    private AnimationTimer gameLoop;
    
    //Create the Player Model and Pane(or the window, whatever you wanna call it)
    private Player player;
    private Pane gamePane;
    private Scene scene;

    //For gameover / retrying
    private Parent retryMenu;

    //For pausing
    private StackPane gameRoot;
    private Parent pauseMenu;
    private boolean isPaused = false;
    private Parent menuPane;

    //This is for the movement and mouse !!!
    private HashSet<KeyCode> keysPressed = new HashSet<>();
    private HashSet<MouseButton> mouseButtonsPressed = new HashSet<>();

    //This is for the obstacles (put them in array)
    private List<Obstacle> obstacles = new ArrayList<>();

    //This is for the Bullets (also put them in array)
    private List<Bullet> bullets = new ArrayList<>();

    //Spawn Rate for the obstacles
    private double spawnRate = 1.0;
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

    //For time and score
    private Score score;
    private Time time;

    //For the name
    private String playerName = "NULL";

    @Override
    public void start(Stage primaryStage){

        //This is for the window
        gamePane = new Pane();
        gamePane.setId("root_pane");

        try{

            //This is for the main menu (FXML) thing
            URL fxmlUrl = getClass().getResource("/main_menu/menu.fxml");            
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            menuPane = loader.load();

            MenuController menuController = loader.getController();
            menuController.setGame(this);

            scene = new Scene(menuPane, WINDOW_LENGTH, WINDOW_HEIGHT);

            URL pauseUrl = getClass().getResource("/main_menu/pause.fxml");
            FXMLLoader pauseLoader = new FXMLLoader(pauseUrl);
            pauseMenu = pauseLoader.load();

            PauseController pauseController = pauseLoader.getController();
            pauseController.setGame(this); 

            //For detecting keyboard inputs (for the movement)

            scene.setOnKeyPressed(event -> {

                keysPressed.add(event.getCode());

                if(event.getCode() == KeyCode.ESCAPE){

                    if(isPaused){

                        resumeGame();

                    }else{

                        pauseGame();

                    }

                }

            });

            scene.setOnKeyReleased(event -> {

                keysPressed.remove(event.getCode());

            });

            scene.setOnMouseMoved(event -> {

                if(!isPaused){

                    mouse_x_position = event.getX();
                    mouse_y_position = event.getY();

                }

            });

            scene.setOnMousePressed(event -> {

                mouseButtonsPressed.add(event.getButton());

            });
            scene.setOnMouseReleased(event -> {

                mouseButtonsPressed.remove(event.getButton());

            });

            //Link to CSS!!!!
            scene.getStylesheets().add(getClass().getResource("/css/game.css").toExternalForm());

            //Setting up the window thing
            primaryStage.setTitle("Space Glider");
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch(IOException e){

            e.printStackTrace();
            System.err.println("Error Loading the FXML File");

        }

    }
 
    public void startGame(){

        //Create new player / load player object
        player = new Player();
        player.setPos(WINDOW_LENGTH/2, WINDOW_HEIGHT/2);

        //Create a score and time object
        score = new Score();
        time = new Time();

        AnchorPane uiLayer = new AnchorPane();

        uiLayer.getChildren().addAll(score.getNode(), time.getNode());
        uiLayer.setMouseTransparent(true);

        AnchorPane.setTopAnchor(time.getNode(), 20.0);
        AnchorPane.setLeftAnchor(time.getNode(), 20.0);

        AnchorPane.setTopAnchor(score.getNode(), 20.0);
        AnchorPane.setRightAnchor(score.getNode(), 20.0);

        //Clear the everything first (start with empty canvas with no enemies or bullets or something idk)
        obstacles.clear();
        bullets.clear();
        gamePane.getChildren().clear();

        gamePane.setMouseTransparent(true);

        //Put the player into the gamepane (or the window)
        gamePane.getChildren().add(player.getNode());

        gameRoot = new StackPane();
        gameRoot.getChildren().addAll(gamePane, uiLayer);

        //Call and start the game loop
        initializeGameLoop();
        gameLoop.start();

        //Set the scene (or the window) and put it on the screen
        scene.setRoot(gameRoot);

    }

    public void pauseGame(){

        if(!isPaused){

            isPaused = true;
            gameLoop.stop();

            if(!gameRoot.getChildren().contains(pauseMenu)){

                gameRoot.getChildren().add(pauseMenu);

            }

        }

    }

    public void resumeGame(){

        if(isPaused){

            isPaused = false;
            gameLoop.start();

            gameRoot.getChildren().remove(pauseMenu);

        }

    }

    public void goToMenu(){
        

        if(gameLoop != null){

            gameLoop.stop();

        }

        isPaused = false;

        gamePane.getChildren().clear();
        obstacles.clear();
        bullets.clear();

        mouseButtonsPressed.clear();
        keysPressed.clear();

        scene.setRoot(menuPane);

    }

    private void initializeGameLoop(){

        //This is the game loop, essentially, the loop that updates every frame (loops but way faster)
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                
                time.update();

                //This is for the player movement
                if (keysPressed.contains(KeyCode.W)){player.moveUp();}
                if (keysPressed.contains(KeyCode.S)){player.moveDown(WINDOW_HEIGHT);}
                if (keysPressed.contains(KeyCode.A)){player.moveLeft();}
                if (keysPressed.contains(KeyCode.D)){player.moveRight(WINDOW_LENGTH);}

                //Get the position of the cursor, make the player face there
                player.face_direction(mouse_x_position, mouse_y_position);

                //Increment spawntimer nad bullet time every frame
                spawnTimer++;
                bulletTimer++;

                //If the bulletTimer is equal to the bullet interval, then shoot
                if(bulletTimer >= bulletInterval){

                    //Create a new bullet
                    Bullet newBullet = new Bullet(player.getX(), player.getY(), mouse_x_position, mouse_y_position);
                    bullets.add(newBullet);
                    gamePane.getChildren().add(newBullet.getNode());
                    bulletTimer = 0;

                }

                //Render all of the bullets
                Iterator<Bullet> bulletIterator = bullets.iterator();

                //While there are still bullets, check the distance of each obstacles there are
                //I am checking for the distance between them, if it's less than 30, then it means
                //it is "shot", so I remove the obstacle
                while(bulletIterator.hasNext()){

                    Bullet bullet = bulletIterator.next();

                    bullet.update();
                    double bullet_x = bullet.getX();
                    double bullet_y = bullet.getY();

                    boolean bullet_hit = false;

                    Iterator<Obstacle> obstactsIterator = obstacles.iterator();

                    //Iterate through all the obstacles and check if the distance between them and
                    //the bullet is less that 30
                    while(obstactsIterator.hasNext()){

                        Obstacle obstacle = obstactsIterator.next();

                        double delta_x = bullet_x - obstacle.getX();
                        double delta_y = bullet_y - obstacle.getY();

                        double distance = Math.sqrt(delta_x * delta_x + delta_y * delta_y);
                        
                        //If its less than 30, then shoot, bye obstacle
                        if(distance < 30){

                            Platform.runLater(() -> {
                                
                                gamePane.getChildren().remove(bullet.getNode());
                                gamePane.getChildren().remove(obstacle.getNode());

                            });

                            score.addScore(100);
                            bulletIterator.remove();
                            obstactsIterator.remove();
                            bullet_hit = true;
                            break;

                        }

                    }

                    //This is if the bullet is off the screen, then we can remove it (not needed anymore lol)
                    if(!bullet_hit && bullet.isOffScreen(WINDOW_LENGTH, WINDOW_HEIGHT)){

                        Platform.runLater(() -> gamePane.getChildren().remove(bullet.getNode()));
                        bulletIterator.remove();

                    }

                }

                //Same idea with the bullets, just spawn in everytime lol
                if(spawnTimer >= spawnInterval){

                    Obstacle newObstacle = new Obstacle(WINDOW_LENGTH, WINDOW_HEIGHT);
                    obstacles.add(newObstacle);
                    gamePane.getChildren().add(newObstacle.getNode());
                    spawnTimer = 0;

                }

                //Iterate all 
                Iterator<Obstacle> obstacleIterator = obstacles.iterator();

                //For iterating and checking all the obstacles there are currently
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
            
                    //If the obstacle "touches" the player, remove the player and stop the program, meaning
                    //Game over
                    //TODO: Once the game is over, make a FXML file that asks if retry or not
                    //Instead of stopping the game/program

                    if(distance_player_obstacle < 50){

                        player.setScore(score.getScore()); 
                        player.setTime(time.getTime());

                        Platform.runLater(() -> {

                            gamePane.getChildren().remove(player.getNode());
                            showInputScreen(); 

                        });

                        player.setScore(score.getScore());
                        player.setTime(time.getTime());

                        gameLoop.stop();

                        System.out.println("Game Over!");

                    }

                }

                //This part makes it so that the obstacles are scaling / the obstacles are increasing
                //as the game goes on
                scalingTimer++;

                if(scalingTimer >= SCALING_INTERVAL_FRAMES){

                    spawnRate += SPAWN_RATE_INCREASE;
                    spawnInterval = (10.0*60.0) / spawnRate;
                    scalingTimer = 0;

                }

            } 
        };

    }

    public void showRetryScreen(){

        Platform.runLater(() -> {

            try{

                if(gameRoot.getChildren().size() > 1){

                    gameRoot.getChildren().remove(gameRoot.getChildren().size() - 1);

                }

                URL retryUrl = getClass().getResource("/main_menu/gameover.fxml");
                FXMLLoader loader = new FXMLLoader(retryUrl);
                retryMenu = loader.load();

                main_menu.GameOverController controller = loader.getController();
                controller.setGame(this);

                gameRoot.getChildren().add(retryMenu);

            }catch(IOException e){

                e.printStackTrace();

            }

        });

    }
    
    public void removeRetryScreen(){

        gameRoot.getChildren().remove(retryMenu);

    }

    public void showInputScreen(){

        Platform.runLater(() -> {

            try{

                URL inputUrl = getClass().getResource("/main_menu/input.fxml");
                FXMLLoader loader = new FXMLLoader(inputUrl);
                Parent inputRoot = loader.load();

                main_menu.InputController controller = loader.getController();

                controller.setData(this, player);

                gameRoot.getChildren().add(inputRoot);

            }catch(IOException e){

                e.printStackTrace();

            }

        });

    }

    public void setPlayerName(String name){

        this.playerName = name;
    
    }

    public void showLeaderboard(){

        Platform.runLater(() -> {

            try{

                URL lbUrl = getClass().getResource("/main_menu/leaderboard.fxml");
                FXMLLoader loader = new FXMLLoader(lbUrl);
                Parent lbRoot = loader.load();

                main_menu.LeaderboardController controller = loader.getController();
                controller.setGame(this);
                scene.setRoot(lbRoot);

            }catch(IOException e){

                e.printStackTrace();

            }

        });

    }

}
