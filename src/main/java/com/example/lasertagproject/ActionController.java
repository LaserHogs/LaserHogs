package com.example.lasertagproject;

import javafx.animation.KeyValue;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ActionController implements Initializable, Runnable {


    static int roundTimer = 5;


    @FXML
    AnchorPane mainAnchorPane = new AnchorPane();
    @FXML
    private ListView<String> playerhit = new ListView<>();


    public static List<Label> scoreLabels = new ArrayList<>();

    public static List<Label> scoreLabelsGreen = new ArrayList<>();
    public static List<Label> StaticCnames = new ArrayList<>();
    public static List<String> StaticCnamesRed = new ArrayList<>();
    public static List<String> StaticCnamesGreen = new ArrayList<>();
    public static List<String> StaticIdRed = new ArrayList<>();
    public static List<String> StaticIdGreen = new ArrayList<>();

    static ObservableList<Player> redInfo;

    static ObservableList<Player> greenInfo;


    static HashMap<Integer, Integer> GreenPlayerScores = new HashMap<Integer, Integer>();//<id, score>
    static HashMap<Integer, Integer> RedPlayerScores = new HashMap<Integer, Integer>();//<id, score>


    static boolean ShooterIsGreen = false;
    static boolean ShooterIsRed = false;


    static String greenPlayerNameScore = "";
    static String redPlayerNameScore = "";

    static String greenTotalScore = "";
    static String redTotalScore = "";


    udpTrafficGen trafficGenerator = new udpTrafficGen();
    @FXML
    Label EID0;
    @FXML
    Label Ecname0;


//    Time time2 = new Time("06:01");

    @FXML
    private Text playTime;
    @FXML
    private Text TopScorerName;
    public static List<Text> TotalScoreLabels = new ArrayList<>();


    @FXML


    ObservableList<String> greenPlayerText = FXCollections.observableArrayList();
    ObservableList<String> redPlayerText = FXCollections.observableArrayList();

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();




    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        playerhit = (ListView<String>) mainAnchorPane.lookup("#playerhit");


        try {
            searchAllPlayers();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < 30; i++) {

            String labe2 = "pname" + i;
            String RedScoreLabels = "redScore" + i;
            String GreenScoreLabels = "greenScore" + i;


            Label cname = (Label) mainAnchorPane.lookup("#" + labe2);//label1
            Label scoresOfAll = (Label) mainAnchorPane.lookup("#" + RedScoreLabels);//label2
            Label scoresOfAllGreen = (Label) mainAnchorPane.lookup("#" + GreenScoreLabels);//label2


            if (i >= 0 && i <= 14) {
//                scoresOfAll.textProperty().bind(cname.textProperty());


                StaticCnames.add(cname);
                scoreLabels.add(scoresOfAll);
                scoreLabelsGreen.add(scoresOfAllGreen);


            } else if (i > 14 && i < 30) {
//                scoresOfAll.textProperty().bind(cname.textProperty());

                StaticCnames.add(cname);
//                scoreLabels.add(scoresOfAll);
                scoreLabelsGreen.add(scoresOfAllGreen);
            }

        }


        Runnable time2 = new Time("06:01");
        Thread myThread = new Thread(time2);
        myThread.start();


        Timeline timeline2 = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        e -> {
                            ((Time) time2).oneMinutePassed();
                            playTime.setText(((Time) time2).getCurrentTime());
                        }));

        playTime.setText(((Time) time2).getCurrentTime());
        timeline2.setCycleCount(361);
        timeline2.play();

        greenPlayerText.add("");
        playerhit.setItems(greenPlayerText);


        redPlayerText.add("");
        playerhit.setItems(redPlayerText);




        try

    {
        new UDPServer().start();//start server
    } catch(
    SocketException e)

    {
        throw new RuntimeException(e);
    }
        executorService.scheduleAtFixedRate(this::ViewGameAction,0,1,TimeUnit.SECONDS);


    Text RedScoreLabel = (Text) mainAnchorPane.lookup("#RedScoreLabel");
    Text GreenScoreLabel = (Text) mainAnchorPane.lookup("#GreenScoreLabel");

        TotalScoreLabels.add(GreenScoreLabel);
        TotalScoreLabels.add(RedScoreLabel);

}



    private Stage stage;
    private Scene scene;
    public void switchToScene2(Text text) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));


        stage = (Stage) text.getScene().getWindow();
        scene = new Scene(root, 1020, 640);
        stage.setScene(scene);
        stage.show();


    }



    public void setNameLabelRed( String txt2, int index)  throws IOException {

        if ( index < StaticCnames.size()){

            Label cname = StaticCnames.get(index);

            if ( cname != null){

                StaticCnames.get(index).setText(txt2);
                StaticCnamesRed.add(txt2);

//                System.out.println("StaticCnamesRed: " + StaticCnamesRed.get(index).getText());

            }
        }
    }
    public void setNameLabelGreen( String txt2, int index)  throws IOException {
        int greenIndex = index - 15;
        if ( greenIndex < StaticCnames.size()){

            Label cname = StaticCnames.get(greenIndex);

            if ( cname != null){

                StaticCnames.get(index).setText(txt2);
                StaticCnamesGreen.add(txt2);
            }
        }
    }





    @FXML
    private void searchAllPlayers() throws SQLException, ClassNotFoundException {
        try {
            //Get all red team Players information
            redInfo = PostgresConnection.searchRedPlayers();

            //Get all green team Players information
            greenInfo = PostgresConnection.searchGreenPlayers();


        } catch (SQLException e){
            System.out.println("Error occurred while getting players information from DB.\n" + e);
            throw e;
        }
    }




    private void ViewGameAction() {
//        Timeline flashScoreRed = new Timeline();
//        flashScoreRed.setCycleCount(Timeline.INDEFINITE);
//        flashScoreRed.setAutoReverse(true);
//
//        Timeline flashScoreGreen = new Timeline();
//        flashScoreGreen.setCycleCount(Timeline.INDEFINITE);
//        flashScoreGreen.setAutoReverse(true);

//        KeyValue start = new KeyValue(myLabel.opacityProperty(), 1.0);
//        KeyValue end = new KeyValue(myLabel.opacityProperty(), 0.0);
//        KeyFrame startFrame = new KeyFrame(Duration.ZERO, start);
//        KeyFrame endFrame = new KeyFrame(Duration.millis(500), end);

        try {

            for(int i = 0; i < redInfo.size(); i++) {
//                System.out.println("value of redinfoooo " + redInfo.get(i).getId() );
                StaticIdRed.add(String.valueOf(redInfo.get(i).getId()));
                RedPlayerScores.put(redInfo.get(i).getId(),0);

            }


            for(int i = 0; i < greenInfo.size(); i++) {
                StaticIdGreen.add(String.valueOf(greenInfo.get(i).getId()));
                GreenPlayerScores.put(greenInfo.get(i).getId(),0);

            }
//            System.out.println("Red players = " + StaticIdRed + " Green players = " + StaticIdGreen);



            // Start the client in a new thread
            UDPClient client = new UDPClient();
            String topPlayerScorer;

            int index =0;
            while(roundTimer != 0) {



                String PlayersKey = "";
                String PersonalizedPlayersKey = "";
                boolean BadInputFlag = false;

                //collect and validate user input
                System.out.println("\n---- ROUNDS LEFT: " + roundTimer + " ----");
                do {
                    try {
                        Scanner sc = new Scanner(System.in);
                        System.out.println("\n[ LOG ] ----- SELECT ATTACKING TEAM [Type G for green or R for red or end for closing the game] : ");
                        String userTeamChoice = trafficGenerator.shooter();
                        String userRedPlayerChoice;
                        String userGreenPlayerChoice;
                        BadInputFlag = false;



                        //generate traffic


                        //check user input
//                        System.out.println("is green? " + trafficGenerator.isGreen);
//                        System.out.println("is red? " + trafficGenerator.isRed);

                        for(int events=0; events < 10; events++){
                            PersonalizedPlayersKey = udpTrafficGen.generateTraffic(StaticIdGreen, StaticIdRed);

                            int totalGreenScores = 0;
                            int totalRedScores = 0;






                            if(udpTrafficGen.isGreen){
                                ShooterIsGreen = true;
                                ShooterIsRed = false;

                                System.out.println("\n---- GREEN TEAM SHOOTS ----");


                                String finalPersonalizedGREENPlayersKey = PersonalizedPlayersKey;
                                Platform.runLater(() -> playerhit.getItems().add(0, finalPersonalizedGREENPlayersKey));


                                for(int k=0; k< GreenPlayerScores.size(); k++){
                                    totalGreenScores += greenInfo.get(k).getScore();
                                    totalRedScores += redInfo.get(k).getScore();
                                }

                                int finalTotalGreenScores = totalGreenScores + 10;


                                totalScoreTextGreen(finalTotalGreenScores);

                                UDPClient.sendToServer(PersonalizedPlayersKey);
                                int finalTotalRedScores = totalRedScores;

                                flashGreen(finalTotalGreenScores, finalTotalRedScores);
                                flashRed(finalTotalRedScores,finalTotalGreenScores);

                            }
                            else if(udpTrafficGen.isRed){


                                ShooterIsRed = true;
                                ShooterIsGreen = false;

                                System.out.println("\n---- RED TEAM SHOOTS ----");
//                                PersonalizedPlayersKey = trafficGenerator.generateTraffic( StaticIdRed,StaticIdGreen );


                                String finalPersonalizedREDPlayersKey1 = PersonalizedPlayersKey;

                                Platform.runLater(() -> playerhit.getItems().add(0, finalPersonalizedREDPlayersKey1));


                                for(int k=0; k< RedPlayerScores.size(); k++){
                                    totalRedScores += redInfo.get(k).getScore();
                                    totalGreenScores += greenInfo.get(k).getScore();
                                }

                                int finalTotaltotalRedScores = totalRedScores + 10;

                                int finalTotalGreenScores1 = totalGreenScores + 10;

                                totalScoreTextRed(finalTotaltotalRedScores);

                                UDPClient.sendToServer(PersonalizedPlayersKey);

                                flashRed(finalTotaltotalRedScores, finalTotalGreenScores1);
                                flashGreen(finalTotalGreenScores1, finalTotaltotalRedScores);




                            }


//                            else {
//                                // Stop the animation for both labels if neither condition is true
//                                flashScore.stop();
//                                TotalScoreLabels.get(0).setOpacity(1.0);
//                                TotalScoreLabels.get(1).setOpacity(1.0);
//
//                            }


//                            else if (userTeamChoice.equalsIgnoreCase("end")){
//                                PersonalizedPlayersKey = "end";
//                                ShooterIsRed = ShooterIsGreen = false;
//                                BadInputFlag = false;
//                            }
//
//
//                            else{
//                                System.out.print(" Please enter G or R ");
//                                BadInputFlag = true;
//                            }
                        }

                    } catch (InputMismatchException e) {
                        System.out.print(" Please enter G or R ");
                        BadInputFlag = true;
                    }
                }
                while (BadInputFlag);

                //send to server


                saveInfo();

                int topGreenScorer = greenInfo.get(0).getScore();
                String topGreenplayer = greenInfo.get(0).getCodename();
                int topRedScorer = redInfo.get(0).getScore();
                String topRedPlayer = redInfo.get(0).getCodename();

                for(int j = 1; j < GreenPlayerScores.size(); j++){
                    if(greenInfo.get(j).getScore() >= topGreenScorer)
                    {
                        topGreenScorer = greenInfo.get(j).getScore();
                        topGreenplayer = greenInfo.get(j).getCodename();
                    }
                }

                for(int g = 1 ; g < RedPlayerScores.size(); g ++){
                    if(redInfo.get(g).getScore() >= topRedScorer)
                    {
                        topRedScorer = redInfo.get(g).getScore();
                        topRedPlayer = redInfo.get(g).getCodename();
                    }
                }

                if (topRedScorer > topGreenScorer)
                {
                    topPlayerScorer = topRedPlayer;
                }
                else
                    topPlayerScorer = topGreenplayer;

                System.out.println("Top Player is" + topPlayerScorer);
                TopScorerName.setText(topPlayerScorer);

                index++;
                roundTimer--;

            }

            client.close();


        }catch (Exception exception) {
            System.out.println("[-] UDPBaseServer has encountered an exception:");
            exception.printStackTrace();
        }
    }


    public static void saveInfo()
    {
        int redScoreV = 0;
        int greenScoreV =0;
        int  tmpRScores[] = new int[redInfo.size()];
        int  tmpGcores[] = new int[greenInfo.size()];
        int counter=0;

        //SETTING TEAM SCORES
        for (Integer i:RedPlayerScores.values())
        {
            redScoreV = redScoreV + i;
            tmpRScores[counter] = i;
            counter++;
        }
        counter = 0;
        for (Integer i:GreenPlayerScores.values())
        {
            greenScoreV = greenScoreV + i;
            tmpGcores[counter] = i;
            counter++;
        }


        for(int i = 0; i < redInfo.size(); i++)
        {
            redInfo.get(i).setScore(tmpRScores[i]);
        }

        for(int i = 0; i < greenInfo.size(); i++)
        {
            greenInfo.get(i).setScore(tmpGcores[i]);

        }



    }

    public static void setRedTeamScoresLabel(int index, int scoreContent){

        index -= 1;
        String labelId = "redScore" + index;
        Label scoreLabel = scoreLabels.stream()
                .filter(label -> label.getId().equals(labelId))
                .findFirst()
                .orElse(null);

        if (scoreLabel != null) {
            scoreLabel.setText(String.valueOf(scoreContent));
        }
    }

    public static void setGreenTeamScoresLabel(int index, int scoreContent){

        index -= 1;
        String labelId = "greenScore" + index;
        Label scoreLabel = scoreLabelsGreen.stream()
                .filter(label -> label.getId().equals(labelId))
                .findFirst()
                .orElse(null);

        if (scoreLabel != null) {
            scoreLabel.setText(String.valueOf(scoreContent));
        }
    }


    public static void totalScoreTextGreen(int Score){
        TotalScoreLabels.get(0).setText(String.valueOf(Score));
    }
    public static void totalScoreTextRed(int Score){
        TotalScoreLabels.get(1).setText(String.valueOf(Score));
    }

    public void flashRed(int highScore, int lowerScore){
        Timeline flashScoreRed = new Timeline();
        flashScoreRed.setCycleCount(Timeline.INDEFINITE);
        flashScoreRed.setAutoReverse(true);



        if(highScore > lowerScore){
            TotalScoreLabels.get(0).setOpacity(1.0);

            KeyValue start = new KeyValue(TotalScoreLabels.get(1).opacityProperty(), 1.0);
            KeyValue end = new KeyValue(TotalScoreLabels.get(1).opacityProperty(), 0.0);
            KeyFrame startFrame = new KeyFrame(Duration.ZERO, start);
            KeyFrame endFrame = new KeyFrame(Duration.millis(300), end);

            flashScoreRed.getKeyFrames().addAll(startFrame, endFrame);
            flashScoreRed.setOnFinished(event -> {
                TotalScoreLabels.get(1).setOpacity(1.0); // Reset the opacity of the score label
            });
            flashScoreRed.play();
        }
        else{
            flashScoreRed.stop();
            TotalScoreLabels.get(1).setOpacity(1.0); // Reset the opacity of the score label
        }



    }
    public void flashGreen(int highScore, int lowerScore){
        Timeline flashScoreGreen = new Timeline();
        flashScoreGreen.setCycleCount(Timeline.INDEFINITE);
        flashScoreGreen.setAutoReverse(true);

        if(highScore > lowerScore)//if green higher than red
        {
            TotalScoreLabels.get(1).setOpacity(1.0);

            KeyValue start = new KeyValue(TotalScoreLabels.get(0).opacityProperty(), 1.0);
            KeyValue end = new KeyValue(TotalScoreLabels.get(0).opacityProperty(), 0.0);
            KeyFrame startFrame = new KeyFrame(Duration.ZERO, start);
            KeyFrame endFrame = new KeyFrame(Duration.millis(300), end);

            flashScoreGreen.getKeyFrames().addAll(startFrame, endFrame);
            flashScoreGreen.setOnFinished(event -> {
                TotalScoreLabels.get(0).setOpacity(1.0); // Reset the opacity of the score label
            });
            flashScoreGreen.play();
        }
        else{
            flashScoreGreen.stop();
            TotalScoreLabels.get(0).setOpacity(1.0); // Reset the opacity of the score label
        }
    }



    @Override
    public void run() {

    }

}