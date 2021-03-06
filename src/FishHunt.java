import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FishHunt extends Application {
    /**
     * @author Fahirah Diarra 20034025
     * @author Hamdi Ghannem 20151932
     */
    public static final int WIDTH = 640, HEIGHT = 480;


    public static void statusView() {

        VBox main = new VBox(8);

        Text killedFishCount = new Text("");

        //Initializing lives view
        HBox lives = new HBox(8);
        Image fishLife = new Image("Image/fish/00.png");
        ImageView fishLife1 = new ImageView(fishLife);
        ImageView fishLife2 = new ImageView(fishLife);
        ImageView fishLife3 = new ImageView(fishLife);
        lives.getChildren().addAll(fishLife1, fishLife2, fishLife3);

        Text LevelCount = new Text("");
    }

    public static void iniMainMenu(VBox vBox, Button gameButton, Button scoreButton) {
        vBox.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        Image logo = new Image("/Image/logo.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(538 * 4 / 5);
        logoView.setFitHeight(367 * 4 / 5);
        vBox.getChildren().addAll(logoView, gameButton, scoreButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(40, 50, 50, 50));
    }

    public static void iniHighScore(VBox vBox, Text titleHighScore, ListView<String> listScores, HBox highScoreInput, Button menuButton) {
        titleHighScore.setFont(Font.font("serif", 25));
        vBox.getChildren().addAll(titleHighScore, listScores, highScoreInput, menuButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20, 30, 20, 30));
    }

    public static void iniGamePane(Pane pane, ImageView imageView) {
        pane.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        pane.getChildren().add(imageView);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * creation du stage
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane stackPane = new StackPane();
        Pane gamePane = new Pane();
        VBox highScoreVBox = new VBox();
        VBox mainMenu = new VBox();

        Scene menu = new Scene(mainMenu, WIDTH, HEIGHT);

        Scene highScore = new Scene(highScoreVBox, WIDTH, HEIGHT);
        Scene game = new Scene(stackPane, WIDTH, HEIGHT);

        //Initialisation de la scene Game
        Image target = new Image("/Image/cible.png");
        ImageView targetView = new ImageView(target);
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext context = canvas.getGraphicsContext2D();
        gamePane.getChildren().add(canvas);

        ///vue du statut du jeu
        VBox gameStatusView = new VBox(35);
        Text killedFishCount = new Text("1");
        killedFishCount.setFont(Font.font(30));
        killedFishCount.setFill(Color.rgb(255, 255, 255));

        //Initialisation lives view
        HBox lives = new HBox(8);
        Image fishLife = new Image("Image/fish/00.png");
        ImageView fishLife1 = new ImageView(fishLife);
        fishLife1.setFitHeight(30);
        fishLife1.setFitWidth(30);
        ImageView fishLife2 = new ImageView(fishLife);
        fishLife2.setFitHeight(30);
        fishLife2.setFitWidth(30);
        ImageView fishLife3 = new ImageView(fishLife);
        fishLife3.setFitHeight(30);
        fishLife3.setFitWidth(30);
        lives.getChildren().addAll(fishLife1, fishLife2, fishLife3);
        lives.setAlignment(Pos.CENTER);

        // Ajout du text de niveau
        Text levelCount = new Text("Level 1");
        levelCount.setFont(Font.font(75));
        levelCount.setFill(Color.rgb(255, 255, 255));
        gameStatusView.getChildren().addAll(killedFishCount, lives, levelCount);
        gameStatusView.setAlignment(Pos.TOP_CENTER);
        gameStatusView.setPadding(new Insets(20, 0, 0, 0));

        iniGamePane(gamePane, targetView);
        stackPane.getChildren().addAll(gamePane, gameStatusView);


        //creation du controller
        Controller controller = new Controller();
        game.setOnMouseMoved((event) -> {
            double w = targetView.getBoundsInLocal().getWidth();
            double h = targetView.getBoundsInLocal().getHeight();
            double x = event.getX() - w / 2;
            double y = event.getY() - h / 2;
            targetView.setX(x);
            targetView.setY(y);
        });

        game.setOnKeyPressed((value) -> {

            if (value.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }

            if (value.getCode() == KeyCode.H) {
                controller.incrementLevel();
            }

            if (value.getCode() == KeyCode.J) {
                controller.incrementScore();
            }

            if (value.getCode() == KeyCode.K) {
                controller.incrementLives();
            }

            if (value.getCode() == KeyCode.L) {
                controller.die();
            }
        });
        game.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                controller.fire(mouseEvent.getX(), mouseEvent.getY());
            }
        });

        // Initialization de laire dentree du highscore
        HBox highScoreSubmit = new HBox(4);
        highScoreSubmit.setAlignment(Pos.CENTER);
        Label label = new Label("Votre nom : ");
        TextField nameInput = new TextField();
        Text scoreText = new Text();

        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;
            private final double maxDt = 0.01;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double deltaTime = (now - lastTime) * 1e-9;
                while (deltaTime > maxDt) {
                    controller.update(maxDt);
                    deltaTime -= maxDt;
                }
                controller.update(deltaTime);
                controller.updateScore(killedFishCount);
                controller.updateLives(lives);
                controller.updateLevelText(levelCount);
                controller.updateHighScore(scoreText);
                controller.draw(context);
                lastTime = now;
            }
        };



        //Initialisation de la scene HighScore
        Text titreHighScore = new Text("Meilleurs scores");
        HighScoreManager scoreManager = new HighScoreManager();
        ListView<String> scoresView = new ListView<String>();
        scoresView.getItems().setAll(scoreManager.getScores());
        Button menuButton = new Button("Menu");
        menuButton.setOnMouseClicked(mouseEvent -> {
            highScoreSubmit.setVisible(true);
            primaryStage.setScene(menu);
        });

        Button submit = new Button("Ajouter!");
        submit.setOnAction(actionEvent -> {
            PlayerScore scoreInput = new PlayerScore(nameInput.getText(), controller.getScore());
            scoreManager.addScore(scoreInput);
            controller.resetGame();
            primaryStage.setScene(menu);
            scoresView.getItems().setAll(scoreManager.getScores());
        });
        highScoreSubmit.getChildren().addAll(label, nameInput, scoreText, submit);
        iniHighScore(highScoreVBox, titreHighScore, scoresView, highScoreSubmit, menuButton);

        //Bouton de la vue principale
        Button gameButton = new Button("Nouvelle Partie!");
        Button scoreButton = new Button("Meilleurs scores");
        gameButton.setOnMouseClicked(mouseEvent -> {
            primaryStage.setScene(game);
            controller.setLock(false);
            timer.start();
        });
        scoreButton.setOnMouseClicked(mouseEvent -> {
            highScoreSubmit.setVisible(false);
            primaryStage.setScene(highScore);
        });

        //Initialisation de la scene MainMenu
        iniMainMenu(mainMenu, gameButton, scoreButton);

        controller.setStage(primaryStage);
        controller.setHs(highScore);

        primaryStage.setScene(menu);
        primaryStage.setTitle("Fish Hunt");
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/Image/crabe.png"));

    }
}