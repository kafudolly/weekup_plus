package Middleware.Time;

import Middleware.Message;
import Middleware.Time.TimeTools;

import java.util.*;

public class MyTask extends TimerTask {
    public MyTask() {
    }

    public void run() {
        TimeTools.temp += 1;
        if(TimeTools.temp >= TimeTools.TIME_COUNT) {
            TimeTools.temp = 0;
            TimeTools.currentTime+=600;
            if (TimeTools.currentTime % 100 == 60) {
                TimeTools.currentTime += 100;
                TimeTools.currentTime -= 60;
            }
            if(TimeTools.currentTime % 10000 == 6000){
                TimeTools.currentTime += 10000;
                TimeTools.currentTime -= 6000;
            }
            if (TimeTools.currentTime / 10000 == 24) {
                TimeTools.currentTime = 0;
                TimeTools.currentWeekday++;
            }
            if (TimeTools.currentWeekday == 8) {
                TimeTools.currentWeekday = 1;
                TimeTools.currentWeek++;
            }
            //System.out.println(TimeTools.currentWeekday + ":" + TimeTools.currentWeekday + ":" + TimeTools.currentTime);
        }
        try {
            Message.test();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}