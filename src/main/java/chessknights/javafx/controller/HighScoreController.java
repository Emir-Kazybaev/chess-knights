package chessknights.javafx.controller;

import chessknights.resultsJAXB.GameResult1;
import chessknights.resultsJAXB.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;
import java.util.List;

@Slf4j
public class HighScoreController {

//    @Inject
    private FXMLLoader fxmlLoader = new FXMLLoader();


    @FXML
    private TableView<Record> highScoreTable;

    @FXML
    private TableColumn<Record, String> player;

    @FXML
    private TableColumn<Record, Integer> totalWins;


    @FXML
    private void initialize() {
        log.debug("Loading high scores...");
        List<Record> highScoreList = new GameResult1().getGameResults();
        player.setCellValueFactory(new PropertyValueFactory<>("player"));
        totalWins.setCellValueFactory(new PropertyValueFactory<>("totalWins"));
        ObservableList<Record> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(highScoreList);

        highScoreTable.setItems(observableResult);
    }

    public void handleRestartButton(ActionEvent actionEvent) throws IOException {
        log.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        log.info("Loading launch scene...");
        fxmlLoader.setLocation(getClass().getResource("/fxml/launch.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
