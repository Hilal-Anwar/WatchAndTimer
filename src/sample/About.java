package sample;

import javafx.fxml.FXML;

public class About {
    @FXML
    protected void backtoClock(){
        Main.stage.setScene(Main.stopWatchScene);
        Main.stage.setTitle("StopWatch");
    }
}
