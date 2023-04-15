package com.example.lasertagproject;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Player {


    private IntegerProperty id;
    private StringProperty codename;

    private int score;


    public Player() {
        this.id = new SimpleIntegerProperty();
        this.codename = new SimpleStringProperty();
        this.score = 0;
    }

    //player_id
    public int getId() {
        return id.get();
    }
    public void setId(int id){
        this.id.set(id);
    }
    public IntegerProperty idProperty(){
        return id;
    }

    public int getScore(){return score;};
    //Player_Codename
    public String getCodename () {
        return codename.get();
    }
    public void setCodename(String codename){
        this.codename.set(codename);
    }
    public StringProperty codenameProperty() {
        return codename;
    }

    public void setScore(int score){this.score = score;};



}