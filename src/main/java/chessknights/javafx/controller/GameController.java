package chessknights.javafx.controller;

import chessknights.resultsJAXB.GameResult1;
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

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


@Slf4j
public class GameController {


    private String firstPlayerName;
    private String secondPlayerName;
    private String playerName;
    private ChessKnightsState gameState;
    private IntegerProperty steps = new SimpleIntegerProperty();
    private Instant startTime;
    private int player = 1;
    private ImageView whiteKnight;
    private ImageView blackKnight;

    @FXML
    private Label messageLabel;

    @FXML
    private Label playerTurnLabel;

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

    public void setPlayerName(String firstPlayerName){
        this.playerName = firstPlayerName;
    }


    @FXML
    public void initialize() {
        whiteKnight = new ImageView(new Image(getClass().getResource("/images/whiteKnight.png").toExternalForm()));
        blackKnight = new ImageView(new Image(getClass().getResource("/images/blackKnight.png").toExternalForm()));
        whiteKnight.setFitHeight(60);
        whiteKnight.setFitWidth(60);
        blackKnight.setFitHeight(60);
        blackKnight.setFitWidth(60);
        stepsLabel.textProperty().bind(steps.asString());
        gameOver.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                log.info("Game is over");
                log.debug("Saving result to database...");
                createGameResult();
                stopWatchTimeline.stop();
            }
        });
        resetGame();
    }

    private void resetGame() {
        gameGrid.getChildren().remove(whiteKnight);
        gameGrid.getChildren().remove(blackKnight);
        gameState = new ChessKnightsState(ChessKnightsState.INITIAL);
        steps.set(0);
        startTime = Instant.now();
        gameOver.setValue(false);
        player = 1;
        gameGrid.add(whiteKnight,gameState.getWhiteCol(),gameState.getWhiteRow());
        gameGrid.add(blackKnight,gameState.getBlackCol(),gameState.getBlackRow());
        displayGameState();
        createStopWatch();
        Platform.runLater(() -> playerTurnLabel.setText(firstPlayerName));
        Platform.runLater(() -> messageLabel.setText(firstPlayerName + " vs " + secondPlayerName));
    }

    private void displayGameState() {
        GridPane.setConstraints(whiteKnight,gameState.getWhiteCol(),gameState.getWhiteRow());
        GridPane.setConstraints(blackKnight,gameState.getBlackCol(),gameState.getBlackRow());
//        System.out.println(gameState);
    }

    public void handleClickToMove(MouseEvent mouseEvent) {
        int row = GridPane.getRowIndex((Node) mouseEvent.getSource());
        int col = GridPane.getColumnIndex((Node) mouseEvent.getSource());
        log.debug("Cell ({}, {}) is pressed", row, col);
        if (! gameState.isFinished(player) && gameState.isValidMove(row, col,player)) {
            steps.set(steps.get() + 1);
            gameState.moveTo(row, col,player);
            if (player == 1){
                player = 2;
                playerTurnLabel.setText(secondPlayerName);
            }else{
                player = 1;
                playerTurnLabel.setText(firstPlayerName);
            }
            if (gameState.isFinished(player)) {
                gameOver.setValue(true);
                setPlayerName((player == 1? secondPlayerName: firstPlayerName));
                log.info("Player {} won the game in {} steps", playerName, steps.get());
                messageLabel.setText(playerName + " won the match!");
                resetButton.setDisable(true);
                giveUpButton.setText("Finish");
            }
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
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/highscores.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void createGameResult() {
        if(gameState.isFinished(1) || gameState.isFinished(2))
            new GameResult1().createRecords(player == 1? secondPlayerName: firstPlayerName);
    }

    private void createStopWatch() {
        stopWatchTimeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            long millisElapsed = startTime.until(Instant.now(), ChronoUnit.MILLIS);
            stopWatchLabel.setText(DurationFormatUtils.formatDuration(millisElapsed, "HH:mm:ss"));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        stopWatchTimeline.setCycleCount(Animation.INDEFINITE);
        stopWatchTimeline.play();
    }

}