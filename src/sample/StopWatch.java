package sample;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.ResourceBundle;

public class StopWatch implements Initializable {
    @FXML
    AnchorPane visible_page;
    @FXML
    AnchorPane home_pane;
    @FXML
    Label Hour;
    @FXML
    Label Minute;
    @FXML
    Label Second;
    @FXML
    Label MiniSecond;
    @FXML
    Button start;
    @FXML
    Button mark;
    @FXML
    Button reset;
    @FXML
    ImageView PlayIcon;
    @FXML
    TextArea LapTimeShow;
    @FXML
    Label LapLabel;
    @FXML
    ProgressIndicator progress;
    private Button clickButton;
    private int MiniSecond_Value = 0, Second_Value = 0, Minute_Value = 0, Hour_Value = 0;
    private final MediaPlayer Start_Sound = new MediaPlayer(new Media(getClass().getResource("Speech Sleep.wav").toString()));
    private final MediaPlayer Stop_Sound = new MediaPlayer(new Media(getClass().getResource("Speech On.wav").toString()));
    private final MediaPlayer Restart_Sound = new MediaPlayer(new Media(getClass().getResource("Speech Off.wav").toString()));

    @FXML
    protected void side_pane(ActionEvent actionEvent) {
        Button clickButton = (Button) actionEvent.getSource();
        if (clickButton.getGraphic().getId().equals("home")) {
            home_pane.setVisible(true);
            visible_page.setVisible(true);
        } else {
            home_pane.setVisible(false);
            visible_page.setVisible(false);
        }
    }

    @FXML
    protected void ResetStopWatch() {
        LapLabel.setVisible(false);
        LapTimeShow.setVisible(false);
        LapTimeShow.setText("");
        PlayIcon.setImage(new Image(getClass().getResource("play.png").toString()));
        MiniSecond.setText("00");
        Second.setText("00");
        Minute.setText("00");
        Hour.setText("00");
        MiniSecond_Value = 0;
        Second_Value = 0;
        Minute_Value = 0;
        Hour_Value = 0;
        progress.setProgress(0);
        Restart_Sound.play();
        Start_Sound.stop();
        Stop_Sound.stop();
    }

    @FXML
    protected void MarkStopWatch() {
        String text = Hour_Value + " ." + Minute_Value + " ." + Second_Value;
        LapTimeShow.setText(LapTimeShow.getText() + '\n' + text);
        LapTimeShow.setVisible(true);
        LapLabel.setVisible(true);
    }

    @FXML
    protected void clickAction(ActionEvent actionEvent) {
        clickButton = (Button) actionEvent.getSource();
        if (((ImageView) clickButton.getGraphic()).getImage().getUrl().contains("play")) {
            Restart_Sound.stop();
            Stop_Sound.stop();
            Start_Sound.play();
            PlayIcon.setImage(new Image(getClass().getResource("pause.png").toString()));
            watch();
        } else {
            PlayIcon.setImage(new Image(getClass().getResource("play.png").toString()));
            Stop_Sound.play();
            Restart_Sound.stop();
            Start_Sound.stop();
        }
    }

    private void watch() {
        Task<Void> sleeper = new Task<>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(15);
                } catch (InterruptedException ignored) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(workerStateEvent ->
        {
            if (((ImageView) clickButton.getGraphic()).getImage().getUrl().contains("pause")) {
                MiniSecond_Value++;
                if (MiniSecond_Value <= 9)
                    MiniSecond.setText("0" + MiniSecond_Value);
                if (MiniSecond_Value > 9)
                    MiniSecond.setText("" + MiniSecond_Value);
                if (MiniSecond_Value >= 60) {
                    MiniSecond_Value = 0;
                    Second_Value++;
                }
                if (Second_Value <= 9)
                    Second.setText("0" + Second_Value);
                if (Second_Value > 9)
                    Second.setText("" + Second_Value);
                if (Second_Value >= 60) {
                    Second_Value = 0;
                    Minute_Value++;
                }
                if (Minute_Value <= 9)
                    Minute.setText("0" + Minute_Value);
                if (Minute_Value > 9)
                    Minute.setText("" + Minute_Value);
                if (Minute_Value >= 60) {
                    Minute_Value = 0;
                    Hour_Value++;
                }
                if (Hour_Value <= 9)
                    Hour.setText("0" + Hour_Value);
                if (Hour_Value > 9)
                    Hour.setText("" + Hour_Value);
                progress.setProgress((double) Second_Value / 60);
                watch();
            }
        });
        new Thread(sleeper).start();
    }

    @FXML
    protected void Timer(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        if (button.getText().equals("Timer")) {
            Main.stage.setTitle("Timer");
            Main.stage.setScene(Main.CountDownScene);
        } else {
            Main.stage.setTitle("About");
            Main.stage.setScene(Main.AboutScene);
        }
    }

    Button CircularButton(Button bt, String tip) {
        bt.setTooltip(new Tooltip(tip));
        return bt;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        start = CircularButton(start, "Start");
        reset = CircularButton(reset, "Reset");
        mark = CircularButton(mark, "Mark");
    }
}