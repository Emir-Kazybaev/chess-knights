package chessknights.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChessKnightsApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        log.info("Starting application...");
        Parent root = FXMLLoader.load(ChessKnightsApplication.class.getResource("/fxml/launch.fxml"));
        primaryStage.setTitle("Chess Knights");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
