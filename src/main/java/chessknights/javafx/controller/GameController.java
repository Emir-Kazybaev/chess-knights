package chessknights.javafx.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;
import chessknights.state.ChessKnightsState;

import javax.inject.Inject;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
public class GameController {

    @Inject
    private FXMLLoader fxmlLoader;

    @Inject
//    private GameResultDao gameResultDao;

    private String firstPlayerName;
    private String secondPlayerName;
    private String playerName;
    private ChessKnightsState gameState;
    private IntegerProperty steps = new SimpleIntegerProperty();
    private Instant startTime;
    private List<Image> knightImages;

    @FXML
    private Label messageLabel;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Label stepsLabel;

    @FXML
    private Label stopWatchLabel;

    private Timeline stopWatchTimeline;

    @FXML
    private Button resetButton;

    @FXML
    private Button giveUpButton;

    private BooleanProperty gameOver = new SimpleBooleanProperty();

    public void setFirstPlayerName(String firstPlayerName) {
        this.firstPlayerName = firstPlayerName;
    }

    public void setSecondPlayerName(String secondPlayerName) {
        this.secondPlayerName = secondPlayerName;
    }


    @FXML
    public void initialize() {
        knightImages = List.of(
                new Image(getClass().getResource("/images/blackKnight.png").toExternalForm()),
                new Image(getClass().getResource("/images/whiteKnight.png").toExternalForm())
        );
        stepsLabel.textProperty().bind(steps.asString());
        gameOver.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                log.info("Game is over");
                log.debug("Saving result to database...");
//                gameResultDao.persist(createGameResult());
                stopWatchTimeline.stop();
            }
        });
        resetGame();
    }

    private void resetGame() {
        gameState = new ChessKnightsState(ChessKnightsState.INITIAL);
        steps.set(0);
        startTime = Instant.now();
        gameOver.setValue(false);
        displayGameState();
        createStopWatch();
        Platform.runLater(() -> messageLabel.setText(firstPlayerName + " VS " + secondPlayerName));
    }

    private void displayGameState() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ImageView view = (ImageView) gameGrid.getChildren().get(i * 8 + j);
                if (view.getImage() != null) {
                    log.trace("Image({}, {}) = {}", i, j, view.getImage().getUrl());
                }
                view.setImage(knightImages.get(gameState.getTray()[i][j].getValue()));
            }
        }
    }

    public void handleClickToMove(MouseEvent mouseEvent) {
        int player = 1;
        int row = GridPane.getRowIndex((Node) mouseEvent.getSource());
        int col = GridPane.getColumnIndex((Node) mouseEvent.getSource());
        log.debug("Cube ({}, {}) is pressed", row, col);
        if (! gameState.isFinished(player) && gameState.isValidMove(row, col,player)) {
            steps.set(steps.get() + 1);
            gameState.moveTo(row, col,player);
            if (gameState.isFinished(player)) {
                gameOver.setValue(true);
                playerName = (player == 1? firstPlayerName: secondPlayerName);
                log.info("Player {} won the game in {} steps", playerName, steps.get());
                messageLabel.setText("Congratulations, " + playerName + "!");
                resetButton.setDisable(true);
                giveUpButton.setText("Finish");
            }
        }
        if (player == 1){
            player = 2;
        }else{
            player = 1;
        }
        displayGameState();
    }

    public void handleResetButton(ActionEvent actionEvent)  {
        log.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        log.info("Resetting game...");
        stopWatchTimeline.stop();
        resetGame();
    }

    public void handleGiveUpButton(ActionEvent actionEvent) throws IOException {
        String buttonText = ((Button) actionEvent.getSource()).getText();
        log.debug("{} is pressed", buttonText);
        if (buttonText.equals("Give Up")) {
            log.info("The game has been given up");
        }
        gameOver.setValue(true);
        log.info("Loading high scores scene...");
//        fxmlLoader.setLocation(getClass().getResource("/fxml/highscores.fxml"));
//        Parent root = fxmlLoader.load();
//        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//        stage.setScene(new Scene(root));
//        stage.show();
    }
//
//    private GameResult createGameResult() {
//        GameResult result = GameResult.builder()
//                .player(playerName)
//                .solved(gameState.isSolved())
//                .duration(Duration.between(startTime, Instant.now()))
//                .steps(steps.get())
//                .build();
//        return result;
//    }

    private void createStopWatch() {
        stopWatchTimeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            long millisElapsed = startTime.until(Instant.now(), ChronoUnit.MILLIS);
            stopWatchLabel.setText(DurationFormatUtils.formatDuration(millisElapsed, "HH:mm:ss"));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        stopWatchTimeline.setCycleCount(Animation.INDEFINITE);
        stopWatchTimeline.play();
    }

}