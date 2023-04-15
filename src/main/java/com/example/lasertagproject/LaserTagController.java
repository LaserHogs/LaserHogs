package com.example.lasertagproject;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class LaserTagController extends ActionController implements Initializable{

    String url = "jdbc:postgresql://db.mphzfoxdwxmdbxykkdad.supabase.co:5432/postgres?user=postgres&password=laserHogs2023";
    String username = "postgres";
    String password = "laserHogs2023";
    PostgresConnection conn = new PostgresConnection(url, username, password);

    boolean f5pressed = false;

    @FXML
    private AnchorPane EntryAnchorPane = new AnchorPane();

    @FXML
    private Text message;

    Time time1 = new Time("00:30");
    @FXML
    private Text countDownTimer;
    Timeline timeline1 = new Timeline(
            new KeyFrame(Duration.seconds(1),
                    e -> {
                        time1.oneSecondPassed();
                        countDownTimer.setText(time1.getCurrentTime());
                    }));

    private List<TextField> textFields = new ArrayList<>();
    private List<TextField> cnames = new ArrayList<>();
    private int maxData = 30;


    @FXML
    private TextField redScoreField;

    @FXML
    private TextField greenScoreField;

    @FXML
    private TextField hitPlayerField;


    public LaserTagController() throws IOException {
        //constructor
    }


    @Override
    public void initialize(URL url, ResourceBundle rb){


        for (int i = 0; i < maxData; i++) {
            String textFieldId = "IDFIELD" + i;
            String textFieldcname = "cname" + i;
            TextField textField = (TextField) EntryAnchorPane.lookup("#" + textFieldId);
            TextField cnamefield = (TextField) EntryAnchorPane.lookup("#" + textFieldcname);

            cnames.add(cnamefield);
            textFields.add(textField);

        }
    }

    Timeline timeline3 = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> {
                try {
                    checkTimeline();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            })
    );

    public void startTimer() {
        timeline1.setCycleCount(30);
        timeline1.play();

        timeline3.setCycleCount(30);
        timeline3.play();

    }

    public void checkTimeline() throws IOException {
        if(timeline1.getStatus() == Animation.Status.STOPPED)
        {
            if(countDownTimer != null) {
                switchToScene2(countDownTimer);
                setInfo();

                Runnable myRunnable = new ActionController();
                Thread myThread = new Thread(myRunnable);
                myThread.start();


            }
        }
    }
    public void sendInformationToSupabase() {

        for (int i = 0; i < textFields.size() && i < cnames.size(); i++) {
                String idSend = textFields.get(i).getText();

                String cnameSend = cnames.get(i).getText();

                    if (!idSend.isEmpty() && !cnameSend.isEmpty()) {

                            if(i >= 0 && i <=14){
                                conn.addToRedTable(Integer.parseInt(idSend), cnameSend);
                            }
                            if(i >= 15 && i <= 29){
                                conn.addToGreenTable(Integer.parseInt(idSend), cnameSend);
                            }
                        }
            }
    }

    public void setInfo() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
//        Parent root = loader.load();
        ActionController actionController = new ActionController();

        for (int i = 0; i < textFields.size() && i < cnames.size(); i++) {
            String idSend = textFields.get(i).getText();

            String cnameSend = cnames.get(i).getText();

            if (!idSend.isEmpty() && !cnameSend.isEmpty()) {

                if(i >= 0 && i <=14){
                    actionController.setNameLabelRed( cnameSend, i);
//                    actionController.getIdRed(idSend, i);
                }
                if(i >= 15 && i <= 29){
                    actionController.setNameLabelGreen( cnameSend, i);
//                    actionController.getIdGreen((idSend), i);
                }
            }
        }
    }

    @FXML
    private void handleKeyPress(KeyEvent event) throws IOException{
        if (event.getCode() == KeyCode.F5)
        {
            sendInformationToSupabase();
            countDownTimer.setText(time1.getCurrentTime());
            startTimer();
        }
    }
}