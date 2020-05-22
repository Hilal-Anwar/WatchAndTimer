package sample;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.ResourceBundle;

public class CountDown implements Initializable {
    MediaPlayer m=new MediaPlayer(new Media(getClass().getResource("alert.wav").toString()));
    String string,mi,h,s;
    boolean Condition=false;
    Double TotalDuration;
    Button clickButton;
    @FXML
    TextArea LapTimeShow;
    @FXML
    Label LapLabel,dot1,dot2;
    @FXML
    Label Seconds,Minutes,Hours;
    @FXML
    Button startTimer;
    @FXML
    ProgressIndicator progress;
    @FXML
    ImageView PlayIcon;
    @FXML
    protected void BackToStopWatch(){
        Main.stage.setTitle("Stopwatch");
        Main.stage.setScene(Main.stopWatchScene);
    }

    @FXML
    protected void changeSeconds(ActionEvent actionEvent){
        Button button=(Button)actionEvent.getSource();
        Seconds.setText(changeTimer(Seconds.getText(),button.getId()));
        s=Seconds.getText();
        TotalDuration=Double.parseDouble(Hours.getText())*3600+Double.parseDouble(Minutes.getText())*60+Double.parseDouble(Seconds.getText());
    }
    @FXML
    protected void changeMinutes(ActionEvent actionEvent){
        Button button=(Button)actionEvent.getSource();
        Minutes.setText(changeTimer(Minutes.getText(),button.getId()));
        mi=Minutes.getText();
        TotalDuration=Double.parseDouble(Hours.getText())*3600+Double.parseDouble(Minutes.getText())*60+Double.parseDouble(Seconds.getText());
    }
    @FXML
    protected  void reset(){
        progress.setProgress(0);
        mi="00";
        s="00";
        h="00";
        m.stop();
        Condition=false;
        Seconds.setText(s);
        Minutes.setText(mi);
        Hours.setText(h);
        Seconds.setVisible(true);
        Minutes.setVisible(true);
        Hours.setVisible(true);
        startTimer.setDisable(false);
        LapTimeShow.setVisible(false);
        LapLabel.setVisible(false);
        LapTimeShow.setText("");
    }
    @FXML
    protected void mark(){
        String text= Hours.getText()+" ."+Minutes.getText()+" ."+Seconds.getText();
        LapTimeShow.setText(LapTimeShow.getText()+'\n'+text);
        LapTimeShow.setVisible(true);
        LapLabel.setVisible(true);
    }
    @FXML
    protected void changeHours(ActionEvent actionEvent){
        Button button=(Button)actionEvent.getSource();
        Hours.setText(changeTimer(Hours.getText(),button.getId()));
        h=Hours.getText();
        TotalDuration=Double.parseDouble(Hours.getText())*3600+Double.parseDouble(Minutes.getText())*60+Double.parseDouble(Seconds.getText());
    }
    @FXML
    protected void clickAction(ActionEvent actionEvent){
        clickButton = (Button) actionEvent.getSource();
        if (((ImageView)clickButton.getGraphic()).getImage().getUrl().contains("play")&&
                ((!s.equals("00"))||(!mi.equals("00"))||(!h.equals("00")))) {
            PlayIcon.setImage(new Image(getClass().getResource("pause.png").toString()));
            watch();
        }
        else
            PlayIcon.setImage(new Image(getClass().getResource("play.png").toString()));
    }
    String changeTimer(String label,String id){
        if (id.contains("1")){
            if (id.contains("U")){
                if (Integer.parseInt(""+label.charAt(1))<9)
                    label=label.charAt(0)+String.valueOf((Integer.parseInt(""+label.charAt(1))+1));
            }
            if (id.contains("D")){
                if (Integer.parseInt(""+label.charAt(1))>0)
                    label=label.charAt(0)+String.valueOf((Integer.parseInt(""+label.charAt(1))-1));
            }
        }
        if (id.contains("2"))
        {
            if (id.contains("U")){
                if (Integer.parseInt(""+label.charAt(0))<9)
                    label=String.valueOf((Integer.parseInt(""+label.charAt(0))+1))+label.charAt(1);
            }
            if (id.contains("D")){
                if (Integer.parseInt(""+label.charAt(0))>0)
                    label=String.valueOf((Integer.parseInt(""+label.charAt(0))-1))+label.charAt(1);
            }
        }
        return label;
    }
    private void watch() {
        Task<Void> sleeper = new Task<>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(workerStateEvent ->
        {
            if (((ImageView)clickButton.getGraphic()).getImage().getUrl().contains("pause")&&
                    (Integer.parseInt(Seconds.getText())!=0||
            Integer.parseInt(Minutes.getText())!=0||Integer.parseInt(Hours.getText())!=0))
            {
                if (Integer.parseInt(Seconds.getText())>0)
                {
                    string =""+(Integer.parseInt(Seconds.getText())-1);
                    if (Integer.parseInt(string)<=9)
                        Seconds.setText("0"+ string);
                    if (Integer.parseInt(string)>9)
                        Seconds.setText(string);
                }
                else if (Integer.parseInt(Seconds.getText())==0&&Integer.parseInt(Minutes.getText())>0)
                {
                    Seconds.setText("60");
                    string =""+(Integer.parseInt(Minutes.getText())-1);
                    if (Integer.parseInt(string)<=9)
                        Minutes.setText("0"+ string);
                    if (Integer.parseInt(string)>9)
                        Minutes.setText(string);
                }
                else if (Integer.parseInt(Minutes.getText())==0&&Integer.parseInt(Hours.getText())>0)
                {
                Minutes.setText("60");
                string =""+(Integer.parseInt(Hours.getText())-1);
                if (Integer.parseInt(string)<=9)
                    Hours.setText("0"+ string);
                if (Integer.parseInt(string)>9)
                    Hours.setText(string);
                }
                progress.setProgress(progress.getProgress()+1/TotalDuration);
                watch();
                System.out.println(progress.getProgress());
            }
            else if (progress.getProgress()>0&&progress.getProgress()>=0.9999999999999999)
            {
                Seconds.setText(s);
                Minutes.setText(mi);
                Hours.setText(h);
                if (Condition)
                {
                    Seconds.setVisible(true);
                    Minutes.setVisible(true);
                    Hours.setVisible(true);
                    dot1.setVisible(true);
                    dot2.setVisible(true);
                    Condition=false;
                }
                else {
                    Seconds.setVisible(false);
                    Minutes.setVisible(false);
                    Hours.setVisible(false);
                    dot1.setVisible(false);
                    dot2.setVisible(false);
                    Condition=true;
                }
                startTimer.setDisable(true);
                PlayIcon.setImage(new Image(getClass().getResource("play.png").toString()));
                m.setCycleCount(MediaPlayer.INDEFINITE);
                watch();
                m.play();
            }
        });
        new Thread(sleeper).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        h=Hours.getText();
        s=Seconds.getText();
        mi=Minutes.getText();
    }
}
