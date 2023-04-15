package com.example.lasertagproject;

public class Time implements Runnable{
    @Override
    public void run() {

    }
    private int minute;

    private int second;

    public Time (int minute, int second)
    {
        this.minute = minute;
        this.second = second;
    }

    public Time (String currentTime)
    {
        String[] time= currentTime.split(":");
        minute = Integer.parseInt(time[0]);
        second = Integer.parseInt(time [1]);
    }

    public String getCurrentTime()
    {
        return (String.format("%02d:%02d", minute, second));
    }

    public void oneSecondPassed()
    {
        second--;
        if (second == 0)
        {
            minute = 0;
        }
    }

    public void oneMinutePassed()
    {
        if(second == 0)
        {
            minute--;
            second = 60;
        }
        second--;
    }
}
