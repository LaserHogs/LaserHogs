package com.example.lasertagproject;

import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.net.*;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer extends Thread {
    byte[] buffer = new byte[1024];
//    DatagramPacket packet;
//    InetAddress localIP = InetAddress.getByName("127.0.0.1");
    private DatagramSocket serverSocket;
    private boolean running;
    private String shooterCodename = "";
    private String targetCodename = "";
    private int shooterScore = 0;
    private int targetScore = 0;
    public static String   to_print;

    PostgresConnection persistence = PostgresConnection.getInstance();


    public UDPServer() throws SocketException {
        System.out.println("UDPServer Started IN SERVER");
        serverSocket = new DatagramSocket(7501);
    }

    public void run() {


        running = true;
        while (running) {

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                serverSocket.receive(packet);

                InetAddress address = packet.getAddress();
                int port = packet.getPort();

//                String received = new String(packet.getData(), 0, packet.getLength());
                String received = this.dataGenerator(buffer).toString().replaceAll(" ", "");

                System.out.println(" datos de lo recivido: " + received);

                processData(received);

                byte[] sendData = received.getBytes();

//                System.out.println("Received from client: " + received);

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);

                serverSocket.send(sendPacket);
//                received = this.data(buffer).toString().replaceAll(" ", "");




                if (received.contains("end")) {

                    running = false;
                    //close the game
                    System.exit(0);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        serverSocket.close();
    }

    private void processData(String data){


        String[] dataChar = data.split("-----HIT-----");
        int shooter = Integer.parseInt(dataChar[0]);
        int target = Integer.parseInt(dataChar[1].trim());

        String action = "";

        // If green player hits red player
        if(ActionController.ShooterIsGreen) {
            if (searchInGreenTeamTable(shooter)) {
                if (searchInRedTeamTable(target)) {

                    //here's all data to be stored in an action event
                    shooterScore = calculateGreenPlayerScore(shooter);
                    shooterCodename = searchCodenameInGreenTeamTable(shooter);
//                    int shooterIndex = shooter.
                    targetCodename = searchCodenameInRedTeamTable(target);
                    targetScore = calculateRedPlayerScore(target);

//                    action = shooterCodename + "---- hit ----" + targetCodename;
//
//                    System.out.println("[ LOG ] ----- " + action);

                    System.out.println("[ LOG ] ----- SCORE: " + shooterCodename + " = " + shooterScore + " " + targetCodename + " = " + targetScore);
                    ActionController.greenPlayerNameScore = shooterCodename + "    " +String.valueOf(shooterScore);



                    int finalShooter1 = shooter;
                    Platform.runLater(() -> {
                        ActionController.setGreenTeamScoresLabel(persistence.getIndexFromGreenTable(finalShooter1), shooterScore);
                    });




                    System.out.println("[ LOG ] ----- GREEN TEAM SCORES = " + ActionController.GreenPlayerScores);
                    System.out.println("[ LOG ] ----- RED TEAM SCORES = " + ActionController.RedPlayerScores);
                    Integer greenTeamScore = ActionController.GreenPlayerScores.values().stream().mapToInt(d-> d).sum();
                    ActionController.greenTotalScore = String.valueOf(greenTeamScore);

//                    ActionController.setActions(action);
//
                    ActionController.saveInfo();

                }
            }
        }



        // if red player hits green player
        if(ActionController.ShooterIsRed == true) {

            if (searchInRedTeamTable(shooter)) {
                if (searchInGreenTeamTable(target)) {
                    shooterCodename = searchCodenameInRedTeamTable(shooter);
                    shooterScore = calculateRedPlayerScore(shooter);
                    targetCodename = searchCodenameInGreenTeamTable(target);
                    targetScore = calculateGreenPlayerScore(target);

//                    action = shooterCodename + "---- hit ----" + targetCodename;
//                    System.out.println("[ LOG ] ----- Action: " + action);
                    System.out.println("[ LOG ] ----- SCORE: " + shooterCodename + " = " + shooterScore +" "+ targetCodename + " = " + targetScore);
                    ActionController.redPlayerNameScore = shooterCodename + "    " +String.valueOf(shooterScore);



                    int finalShooter = shooter;
//                    System.out.println("shooter is: " + shooter);
//                    System.out.println("index of shooter: " + persistence.getIndexFromRedTable(finalShooter));



                    Platform.runLater(() -> {
                        ActionController.setRedTeamScoresLabel(persistence.getIndexFromRedTable(finalShooter), shooterScore);
                    });

                    System.out.println("[ LOG ] ----- GREEN TEAM SCORES = " + ActionController.GreenPlayerScores);
                    System.out.println("[ LOG ] ----- RED TEAM SCORES = " + ActionController.RedPlayerScores);
                    Integer greenTeamScore = ActionController.RedPlayerScores.values().stream().mapToInt(d-> d).sum();
                    ActionController.redTotalScore = String.valueOf(greenTeamScore);



//                    ActionController.setActions(action);
//
                    ActionController.saveInfo();

                }
            }
        }



        to_print = action;
        shooter = target = 0;


    }


    private boolean searchInGreenTeamTable(int playerId ){

        ObservableList<Player> greenPlayer = persistence.getGreenTeamPlayer(playerId);

        //to find a green player, iterate over the green player table and look for the specific id
        for(int i = 0; i < greenPlayer.size(); i++){
            if(greenPlayer.get(i).getId() == (playerId) )
            {

                return true;
            }
        } //if it gets to the end of the list and the id wasn't found, player doesn't exit
        System.out.println("[ LOG ] ----- player "+ playerId+" doesn't exist");
        return false;
    }

    private boolean searchInRedTeamTable(int playerId ){
        ObservableList<Player> redPlayer = persistence.getRedTeamPlayer(playerId);
//        boolean flag = true;

        System.out.println("size of redPlayer: " + redPlayer.size());

        for(int i = 0; i < redPlayer.size(); i++){
            if(redPlayer.get(i).getId() == (playerId))
            {
                return true;
                //get player ID and codename here
            }

        }
        System.out.println("[ LOG ] ----- player "+ playerId+" doesn't exist");

        return false;
    }

    private String searchCodenameInRedTeamTable(int playerId ){

        ObservableList<Player> redPlayer = persistence.getRedTeamPlayer(playerId);

        //to find a red player, iterate over the green player table and look for the specific id
        for(int i = 0; i < redPlayer.size(); i++){
            if(redPlayer.get(i).getId() == (playerId) )
            {
                //codename
                String codename = redPlayer.get(i).getCodename();
                return codename;
            }
        } //if it gets to the end of the list and the id wasn't found, player doesn't exit
        return "Unknown";
    }

    private String searchCodenameInGreenTeamTable(int playerId ){

        ObservableList<Player> greenPlayer = persistence.getGreenTeamPlayer(playerId);

        //to find a green player, iterate over the green player table and look for the specific id
        for(int i=0; greenPlayer.size()>i; i++){
            if(greenPlayer.get(i).getId() == (playerId) )
            {
                //codename
                String codename = greenPlayer.get(i).getCodename();
                return codename;
            }

        }//if it gets to the end of the list and the id wasn't found, player doesn't exit
        return "Unknown";
    }


    private int calculateGreenPlayerScore(int playerKey){
        int score = 0;
        if(ActionController.GreenPlayerScores.get(playerKey) !=null){

            score = ActionController.GreenPlayerScores.get(playerKey);
            /*determine if points are going to be added or subtracted
                    ShooterIsGreen = TRUE means adding points to this green shooter player
                    ShooterIsGreen = FALSE means subtracting points to this green target player
            */
            if (ActionController.ShooterIsGreen) {
                //add ten points to current shooter score
                score += 10;
            } else {
                //this avoids scores from going negative
                if (score <= 10) {
                    score = 0;
                }
                //if current score allows to subtract 10 points
                else if (score > 10) {
                    score -= 10;
                }
            }

            //update value in hashMap
            ActionController.GreenPlayerScores.replace(playerKey, score);
        }
        return  score;
    }

    private int calculateRedPlayerScore(int playerKey){
        int score =0;
        if(ActionController.RedPlayerScores.get(playerKey) != null) {
            score = ActionController.RedPlayerScores.get(playerKey);
            /*determine if points are going to be added or subtracted
                    ShooterIsRed = TRUE means adding points to this red shooter player
                    ShooterIsRed = FALSE means subtracting points to this red target player
            */
            if (ActionController.ShooterIsRed) {
                //add ten points to current shooter score
                score += 10;
            }
            else {
                //this avoids scores from going negative
                if (score <= 10) {
                    score = 0;
                }
                //if current score allows to subtract 10 points
                else if (score > 10) {
                    score -= 10;
                }
            }

            //update value in hashMap
            ActionController.RedPlayerScores.replace(playerKey, score);
        }
        return  score;
    }

    private StringBuilder dataGenerator(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);

            i++;
        }
        return ret;
    }

}

