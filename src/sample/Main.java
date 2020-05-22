package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * @author helal anwar
 * @ 2020-21
 * Stopwatch using the JavaFX API.
 */
public class Main extends Application {
    static Stage stage=new Stage();
    static Scene stopWatchScene,CountDownScene,AboutScene;
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        stopWatchScene = new Scene(FXMLLoader.load(getClass().getResource("sample.fxml")),772,300);
        CountDownScene=new Scene(FXMLLoader.load(getClass().getResource("Timer.fxml")));
        AboutScene=new Scene(FXMLLoader.load(getClass().getResource("about.fxml")));
        stage.setTitle("Stopwatch");
        stage.setScene(stopWatchScene);
        stage.setOpacity(0.9);
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResource("icon.png").toString()));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
