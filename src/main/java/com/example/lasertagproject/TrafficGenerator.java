package com.example.lasertagproject;


import java.util.*;

public class TrafficGenerator {

    public static List<String> getRandomKeys(List idList) {
        List<String> keys = new ArrayList<>(idList);
        Collections.shuffle(keys);

        return keys;
    }


    public static String combineKeys(List<String> shooterKeys, List<String> targetKeys, String separator){
        String actionPlayersKey = "";
        Random randomGenerator = new Random();
        for(int i = 0; i < 1; i++){
            actionPlayersKey = shooterKeys.get(randomGenerator.nextInt(shooterKeys.size() -1)) + separator;
        }

        actionPlayersKey += targetKeys.get(randomGenerator.nextInt(targetKeys.size() - 1));//last element does not need separator.


        return actionPlayersKey;
    }


    public static String generateCustomUserKeys(List<String> shooterKeys, List<String> targetKeys) {
        String customUserKey = "";
        boolean badInputFlag = false;
        do {
            try {
                Scanner sc = new Scanner(System.in);
                String userShooterPlayerChoice ="";
                String userTargetPlayerChoice ="";
                badInputFlag = false;
                //get specific players
                System.out.println(" SELECT A SHOOTER PLAYER ID FROM : " + shooterKeys);
                userShooterPlayerChoice = sc.nextLine();
                while(shooterKeys.contains(userShooterPlayerChoice) == false){
                    System.out.println(" MAKE SURE THE ID IS IN : " + shooterKeys);
                    userShooterPlayerChoice = sc.nextLine();
                }

                System.out.println(" SELECT A TARGET PLAYER ID FROM : " + targetKeys);
                userTargetPlayerChoice = sc.nextLine();
                while(targetKeys.contains(userTargetPlayerChoice) == false){
                    System.out.println(" MAKE SURE THE ID IS IN : " + targetKeys);
                    userTargetPlayerChoice = sc.nextLine();
                }

                customUserKey = userShooterPlayerChoice + ":" + userTargetPlayerChoice;
            } catch (InputMismatchException e) {
                System.out.print(" PLEASE ENTER A NUMBER");
                badInputFlag = true;
            }
        }
        while(badInputFlag);
        return customUserKey;
    }



}
