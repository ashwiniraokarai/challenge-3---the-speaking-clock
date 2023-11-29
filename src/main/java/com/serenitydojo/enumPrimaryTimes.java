package com.serenitydojo;

import java.time.LocalTime;

public enum enumPrimaryTimes {
    NOON(LocalTime.of(12,0)),
    MIDNIGHT(LocalTime.of(0,0)),
    EVENING(LocalTime.of(17,0)),
    NIGHT(LocalTime.of(21,0));

    private LocalTime time;
     enumPrimaryTimes(LocalTime time){
        this.time = time;
    }

    public LocalTime getTime(){
         return time;
    }

    public int getHour(){
        return time.getHour();
    }
}
