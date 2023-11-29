package com.serenitydojo;

import java.time.LocalTime;
import java.util.*;

public class SpeakingClock {
    private LocalTime time;
    private int hour;
    private static final String[] TWELVE_HOUR_WORD_ARRAY = {"twelve", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"
    };
    private int minute;

    //for human-like responses elements fifteen and thirty were eventually replaced quarter and half, in the array
    private static final String[] MINUTES_IN_WORDS_ARRAY = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven",
            "twelve", "thirteen", "fourteen", "quarter", "sixteen", "seventeen", "eighteen", "nineteen", "twenty", "twenty one", "twenty two",
            "twenty three", "twenty four", "twenty five", "twenty six", "twenty seven", "twenty eight", "twenty nine", "half"
    };

    private String preposition;
    private static final int MINUTES_IN_ONE_HOUR = 60;
    Map<String, String> spokenMessageMap = new HashMap<String, String>();

    public String tellMeTheTime(LocalTime time){
        setTime(time);
        hour = getTime().getHour();
        System.out.println("Hour extracted from 24 hour time object: " + hour);
        minute = getMinute(getTime());

        //this method does some heavy lifting
        reviseHourOrMinute();
        buildSpokenMessageMap();

        return speakMessageForTimeOfDay();
    }

    //This conversion exists only because the wordy hour array is limited to 12 hour format. Gee.
    private int get12HourNotation(LocalTime time24HourFormat){
      int hour24HourFormat = time24HourFormat.getHour();
        //code for noon check
        if(hour24HourFormat == enumPrimaryTimes.NOON.getHour()) {
            //doing a mod 12 (12%12) would yield 0. This can then be confused with twelve in the midnight which also happens to yield 0 (0%12 = 0)
            //modding noon with 24 instead will result in number (12) can help differentiate it from 00 in the midnight
            return hour24HourFormat % 24;
        } else {
            return  hour24HourFormat %12;
        }
    }

    private int getMinute(LocalTime time){
        return time.getMinute();
    }

    //this revision lays the foundation to determining number values through which we can help derive a human like messages
    private void reviseHourOrMinute() {
        int minutesLeftForNextHour;

        if (getMinute() == 0) {
            //it is the current hour with no minutes passed
            setHour(get12HourNotation(getTime()));
            System.out.println("12 hour formatted integer hour to pull wordy hour from array: " + hour);

            setPreposition(enumPrepositions.empty);
        } else if (getMinute() <= 5) {
            //It is the current hour with barely a few minutes passed which qualify for "just after the hour" case
            setMinute(0);
            setHour(get12HourNotation(getTime()));
            System.out.println("12 hour formatted integer hour to pull wordy hour from array: " + hour);

            //primary hours need special treatment since they are defined on "LocalTime" type and not by split up hour and minute combo
            setTimeToPrimaryIfEligible();
            setPreposition(enumPrepositions.just_after);
        } else if (getMinute() > 30) {
            getFollowingHour();

            minutesLeftForNextHour = MINUTES_IN_ONE_HOUR - getMinute();
            setMinute(minutesLeftForNextHour);
            setPreposition(enumPrepositions.to);

            if(getMinute() <= 5) {
                //negligible minutes have passed after the hour which we want to ignore for fuzzy messaging, so set minute to 0
                setMinute(0);

                //primary hours need special treatment since they are defined on "LocalTime" type and not by split up hour and minute combo
                setTimeToPrimaryIfEligible();
                setPreposition(enumPrepositions.almost);
            }
        } else {
            setHour(get12HourNotation(getTime()));
            System.out.println("12 hour formatted integer hour to pull wordy hour from array: " + hour);

            setPreposition(enumPrepositions.past);
        }
    }

    public void getFollowingHour() {
        if (getHour() == enumPrimaryTimes.NOON.getHour()) {
            //12 + 1 => 13  //direct addition yields 13 and therefore hour[13] in hour array causing it to go out of range
            // 13%12 => 1   //but modding 13 by 12 instead gets it to go to hour[1] which is what we want
            setHour((getHour() + 1) % 12);
        } else if(getHour() == 23){
            //if hour is 23 (11 pm), then next hour should be 0 (midnight)
            setHour(enumPrimaryTimes.MIDNIGHT.getHour());  //working code
         } else {
                setHour(get12HourNotation(getTime()));
                setHour(getHour() + 1);
            }
        System.out.println("Incremented hour: " + getHour());
    }
    
    public void setTimeToPrimaryIfEligible(){
            if(getHour() == enumPrimaryTimes.NOON.getHour()){
                setTime(enumPrimaryTimes.NOON.getTime());
            } else if(getHour() == get12HourNotation(enumPrimaryTimes.EVENING.getTime())) {
                setTime(enumPrimaryTimes.EVENING.getTime());
            } else if(getHour() == get12HourNotation(enumPrimaryTimes.NIGHT.getTime())){
                setTime(enumPrimaryTimes.NIGHT.getTime());
            } else if(getHour() == get12HourNotation(enumPrimaryTimes.MIDNIGHT.getTime()) ){
                setTime(enumPrimaryTimes.MIDNIGHT.getTime());
            }
    }

    public String messageForPartOfTheDay(String partOfTheDay){
        return spokenMessageMap.get(partOfTheDay);
    }

    public void buildSpokenMessageMap(){
        String minutesInWords = MINUTES_IN_WORDS_ARRAY[getMinute()];
        String hourInWords = TWELVE_HOUR_WORD_ARRAY[getHour()];

        if(minutesInWords.isEmpty()){
            //fuzzy cases like "It's almost eleven in the morning" where exact minute is not important in the message
            ;
        } else {
            //cases with concrete minutes like "It's quarter to one in the morning"
            minutesInWords = minutesInWords + " ";
        };

        String commonPhrase = "It's " + minutesInWords + getPreposition() + hourInWords;

        String morningSpeech = commonPhrase + " in the morning";
        String afternoonSpeech = commonPhrase +" in the afternoon";
        String eveningSpeech = commonPhrase +" in the evening";
        String nightSpeech = commonPhrase +" in the night";

        spokenMessageMap.put("morning", morningSpeech);
        spokenMessageMap.put("afternoon", afternoonSpeech);
        spokenMessageMap.put("evening", eveningSpeech);
        spokenMessageMap.put("night", nightSpeech);
    }

    public String speakMessageForTimeOfDay(){
        System.out.println("Note: Evaluating part of the day based on og 24 hour format Time object: " + getTime() );
        if(getTime().equals(enumPrimaryTimes.NOON.getTime())){
            return "It's " + getPreposition()  + "noon";
        } else if (getTime().equals(enumPrimaryTimes.MIDNIGHT.getTime())) {
            return "It's " + getPreposition()  + "midnight";
        } else if (getTime().equals(enumPrimaryTimes.EVENING.getTime())) {
            return "It's " + getPreposition()  + "evening";
        } else if(getTime().equals(enumPrimaryTimes.NIGHT.getTime())) {
            return "It's " + getPreposition()  + "night";
        } else if(getTime().isAfter(enumPrimaryTimes.MIDNIGHT.getTime()) && time.isBefore(enumPrimaryTimes.NOON.getTime())){
            return messageForPartOfTheDay("morning");
        } else if(getTime().isAfter(enumPrimaryTimes.NOON.getTime()) && time.isBefore(enumPrimaryTimes.EVENING.getTime())){
            return messageForPartOfTheDay("afternoon");
        } else if(getTime().isAfter(enumPrimaryTimes.EVENING.getTime()) && time.isBefore(enumPrimaryTimes.NIGHT.getTime())) {
            return messageForPartOfTheDay("evening");
        } else if(getTime().isAfter(enumPrimaryTimes.NIGHT.getTime())){
            return messageForPartOfTheDay("night");
        } else {
            return "I don't recognize this time";
        }
    }

    public void setPreposition(enumPrepositions enumConstant) {
        this.preposition = enumConstant.getString();
    }

    public String getPreposition() {
        return preposition;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getMinute() {
        return minute;
    }

    public void setHour(int hour){
        this.hour = hour;
    }

    public int getHour(){
        return hour;
    }

    public void setTime(LocalTime time){
        this.time = time;
    }

    public LocalTime getTime(){
        return time;
    }
}
