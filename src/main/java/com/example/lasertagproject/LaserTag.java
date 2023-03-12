package com.example.lasertagproject;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class LaserTag extends Application {
//    LaserTagController laserTagController = loader.getController();;
    String url = "jdbc:postgresql://db.mphzfoxdwxmdbxykkdad.supabase.co:5432/postgres?user=postgres&password=laserHogs2023";
    String username = "postgres";
    String password = "laserHogs2023";
    boolean onMain = false;
    @Override
    public void start(Stage stage) throws IOException {

        try{
            Parent root1 = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("EntryScreenTest.fxml"));
            Parent root2 = loader.load();

            Scene scene1 = new Scene(root1, 1020, 640);
            Scene scene2 = new Scene(root2, 1020, 640);

            stage.setTitle("--LASER HOGS--");
            stage.setScene(scene1);
            stage.show();
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished( event -> stage.setScene(scene2) );
            delay.play();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();

    }
}