package com.example.lasertagproject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;

public class udpTrafficGen
{

    static boolean isGreen = false;
    static boolean isRed = false;
//    public static void main(String[] args) throws InterruptedException {
//        List<String> shooterKeys = Arrays.asList("11", "12", "16");
//        List<String> targetKeys = Arrays.asList("21", "22", "23");
//        generateTraffic(shooterKeys, targetKeys);
//        System.out.println(generateTraffic(shooterKeys, targetKeys));
//        System.out.println(isGreen);
//        System.out.println(isRed);
////        generateTraffic( targetKeys, shooterKeys);
//    }

    public String shooter(){
        String shoots = "";
        int counter = 5;
        for (int i = 0; i < counter; i++) {
            if (Math.random() < 0.5) {
                shoots = "r";
            }
            else {
                shoots = "g";
            }
        }
        return shoots;
    }

    public static String generateTraffic(List<String> shooterKeys, List<String> targetKeys) throws InterruptedException {
        String message = "";

        //            int counter = 5;

        // Counter number of events, random player and order
        int min = 1;
        int max = shooterKeys.size();
        int range = max - min + 1;


        String redPlayer = "", greenPlayer = "";
        if (Math.random() < 0.5) {
            isGreen = true;
            isRed = false;
            redPlayer = shooterKeys.get((int) (Math.random() * range));
            greenPlayer = targetKeys.get((int) (Math.random() * range));
            Thread.sleep((int)(Math.random() * 1000) + 1);
            message = redPlayer + "-----HIT-----" + greenPlayer;
            return message;
        } else {
            isGreen = false;
            isRed = true;
            redPlayer = shooterKeys.get((int) (Math.random() * range));
            greenPlayer = targetKeys.get((int) (Math.random() * range));
            Thread.sleep((int)(Math.random() * 1000) + 1);
            message = greenPlayer + "-----HIT-----" + redPlayer;
            return message;
        }
//                System.out.println(message);

//            Thread.sleep((int)(Math.random() * 1000) + 1); // Sleep for random time between 1 to 1000 milliseconds


    }
}