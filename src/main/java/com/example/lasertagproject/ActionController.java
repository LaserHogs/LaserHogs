package com.example.lasertagproject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ActionController implements Initializable {

    @FXML
    AnchorPane mainAnchorPane = new AnchorPane();
    @FXML
    private List<Label> MaintextFieldsID = new ArrayList<>();
    @FXML
    private List<Label> Maincnames = new ArrayList<>();

    public static List<Label> StaticFieldsID = new ArrayList<>();

    public static List<Label> StaticCnames = new ArrayList<>();
    @FXML
    Label EID0 ;
    @FXML
    Label Ecname0;

    Time time2 = new Time("06:01");
    @FXML
    private Text playTime;

    Timeline timeline2 = new Timeline(
            new KeyFrame(Duration.seconds(1),
                    e -> {
                        time2.oneMinutePassed();
                        playTime.setText(time2.getCurrentTime());
                    }));
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (int i = 0; i < 30; i++) {

            String labe2 = "pname" + i;

            Label cname = (Label) mainAnchorPane.lookup("#" + labe2);

            StaticCnames.add(cname);
//            System.out.printf("pname " + labe2);
        }

        playTime.setText(time2.getCurrentTime());
        timeline2.setCycleCount(361);
        timeline2.play();

    }


    private void init(){


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
//            Label field = StaticFieldsID.get(index);
            Label cname = StaticCnames.get(index);
            if ( cname != null){
//                StaticFieldsID.get(index).setText(txt1);
                StaticCnames.get(index).setText(txt2);

            }
        }
    }
    public void setNameLabelGreen( String txt2, int index)  throws IOException {
//        System.out.printf(" sixe " + StaticFieldsID.size());


        if ( index < StaticCnames.size()){
//            Label field = StaticFieldsID.get(index);
            Label cname = StaticCnames.get(index);
            if ( cname != null){
//                StaticFieldsID.get(index).setText(txt1);
                StaticCnames.get(index).setText(txt2);

            }
        }
    }
}
