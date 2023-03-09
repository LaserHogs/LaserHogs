package com.example.lasertagproject;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.*;

import javafx.scene.input.KeyEvent;

public class LaserTagController extends ActionController implements Initializable{
    String url = "jdbc:postgresql://db.mphzfoxdwxmdbxykkdad.supabase.co:5432/postgres?user=postgres&password=laserHogs2023";
    String username = "postgres";
    String password = "laserHogs2023";
    PostgresConnection conn = new PostgresConnection(url, username, password);

    @FXML
    private AnchorPane EntryAnchorPane = new AnchorPane();


    private List<TextField> textFields = new ArrayList<>();
    private List<TextField> cnames = new ArrayList<>();
    private int maxData = 30;


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




    public void sendInformationToSupabase(Event event) throws  IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        Parent root = loader.load();
        ActionController actionController = loader.getController();

        for (int i = 0; i < textFields.size() && i < cnames.size(); i++) {
                String idSend = textFields.get(i).getText();

                String cnameSend = cnames.get(i).getText();

                    if (!idSend.isEmpty() && !cnameSend.isEmpty()) {
                        System.out.printf("TRACK ");

                            conn.addIDAndCodename(Integer.parseInt(idSend), cnameSend);
                            if(i >= 0 && i <=14){
                                actionController.setNameLabelRed( cnameSend, i);

                            }
                            System.out.printf("value of i " + i);
                            if(i >= 15 && i <=30){
                                actionController.setNameLabelGreen( cnameSend, i);
                            }
                        }
            }

    }




    @FXML
    private void handleKeyPress(KeyEvent event) throws IOException{
        if (event.getCode() == KeyCode.F5) {
            switchToScene2(event);
            sendInformationToSupabase(event);

        }
    }





    void button(ActionEvent event) throws IOException{

    }

}