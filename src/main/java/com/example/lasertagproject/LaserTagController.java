package com.example.lasertagproject;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import okhttp3.*;

public class LaserTagController{

    @FXML
    private AnchorPane mainAnchorPane = new AnchorPane();

    private List<TextField> textFields = new ArrayList<>();
    private List<TextField> cnames = new ArrayList<>();
    private int maxData = 30;

    @FXML
    void initialize(){

        for (int i = 0; i < maxData; i++) {
            String textFieldId = "IDFIELD" + i;
            String textFieldcname = "cname" + i;
            TextField textField = (TextField) mainAnchorPane.lookup("#" + textFieldId);
            TextField cnamefield = (TextField) mainAnchorPane.lookup("#" + textFieldcname);
            cnames.add(cnamefield);
            textFields.add(textField);

        }
    }

    @FXML
    private Button SUBMIT = new Button();

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Connection connection;


    //this is entry screen
    public void switchToMainScreen(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




    @FXML
    private void sendInformationToSupabase(Event event) throws  IOException{
        String url = "jdbc:postgresql://db.mphzfoxdwxmdbxykkdad.supabase.co:5432/postgres?user=postgres&password=laserHogs2023";
        String username = "postgres";
        String password = "laserHogs2023";
        PostgresConnection conn = new PostgresConnection(url, username, password);

            if (!textFields.isEmpty() && !cnames.isEmpty()) {
                int counter = 0;
                while (counter < textFields.size() && counter < cnames.size()) {
                    if (textFields.get(counter) != null && cnames.get(counter) != null) {
                        String idSend = textFields.get(counter).getText();
                        String cnameSend = cnames.get(counter).getText();
                        if (!idSend.isEmpty() && !cnameSend.isEmpty()) {
                            conn.addIDAndCodename(Integer.parseInt(idSend), cnameSend);
//                            if(Integer.parseInt(idSend).exists)
                        }

                        counter++;
                    }
                }
            }
    }
        @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    void button(ActionEvent event) throws IOException{

    }

}