package com.serenitydojo;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalTime;
import java.util.Locale;

public class SpeakingClockTest {

    @ParameterizedTest
    @CsvSource({
            "12,0, It's noon",
           "0,0, It's midnight",
            "1,0, It's one in the morning",
            "5,0, It's five in the morning",
            "11, 0, It's eleven in the morning",
            "13,0, It's one in the afternoon",
            "17,0, It's evening",
            "20,0, It's eight in the evening",
            "21,0, It's night",
            "23,0, It's eleven in the night"
    })
    public void speakTheHour(int hour, int minute, String expectedSpeech) {
        LocalTime time = LocalTime.of(hour,minute);
       SpeakingClock clock = new SpeakingClock();
       String actualSpeech = clock.tellMeTheTime(time);
        Assertions.assertThat(actualSpeech).isEqualTo(expectedSpeech);
    }

    @ParameterizedTest
    @CsvSource({
            "12, 20, It's twenty past twelve in the afternoon",
            "12, 31, It's twenty nine to one in the afternoon",
            "11, 31, It's twenty nine to twelve in the morning",
            "23,40, It's twenty to twelve in the night",
            "0,20, It's twenty past twelve in the morning",
            "0,35, It's twenty five to one in the morning"
    })
    public void speakTheHourWithMinutes(int hour, int minute, String expectedSpeech){
        LocalTime time = LocalTime.of(hour, minute);
        SpeakingClock clock = new SpeakingClock();
        String actualSpeech = clock.tellMeTheTime(time);

        Assertions.assertThat(actualSpeech).isEqualTo(expectedSpeech);
    }

    //TO DO: Remove excessive test cases
    // Human-like responses: "It's quarter past X"
    @ParameterizedTest
    @CsvSource({
            "00,15,It's quarter past twelve in the morning",
            "1,15,It's quarter past one in the morning",
            "6,15,It's quarter past six in the morning",
            "12,15,It's quarter past twelve in the afternoon",
            "13,15, It's quarter past one in the afternoon",
            "16,15,It's quarter past four in the afternoon",
            "17,15,It's quarter past five in the evening",
            "19,15,It's quarter past seven in the evening",
            "21,15,It's quarter past nine in the night",
            "23,15,It's quarter past eleven in the night"
    })
    public void speakQuarterPastHour(int hour, int minute, String expectedSpeech){
        LocalTime time = LocalTime.of(hour, minute);
        String actualSpeech =   new SpeakingClock().tellMeTheTime(time);

        Assertions.assertThat(actualSpeech).isEqualTo(expectedSpeech);
    }

    //TO DO: Remove excessive test cases
    // Human-like responses: "It's half past X"
    @ParameterizedTest
    @CsvSource({
            "00,30,It's half past twelve in the morning",
            "1,30,It's half past one in the morning",
            "6,30,It's half past six in the morning",
            "12,30,It's half past twelve in the afternoon",
            "13,30, It's half past one in the afternoon",
            "16,30,It's half past four in the afternoon",
            "17,30,It's half past five in the evening",
            "19,30,It's half past seven in the evening",
            "21,30,It's half past nine in the night",
            "23,30,It's half past eleven in the night"
    })
    public void speakHalfPastHour(int hour, int minute, String expectedSpeech){
        LocalTime time = LocalTime.of(hour, minute);
        String actualSpeech =   new SpeakingClock().tellMeTheTime(time);

        Assertions.assertThat(actualSpeech).isEqualTo(expectedSpeech);
    }
    
    //Human-like responses: "It's quarter to Y"
    @ParameterizedTest
    @CsvSource({
            "11,45,It's quarter to twelve in the morning",
            "00,45,It's quarter to one in the morning",
            "8,45,It's quarter to nine in the morning",
            "11,45,It's quarter to twelve in the morning",
            "12,45, It's quarter to one in the afternoon",
            "13,45,It's quarter to two in the afternoon",
            "16,45,It's quarter to five in the afternoon",
            "17,45,It's quarter to six in the evening",
            "20,45,It's quarter to nine in the evening",
            "21,45,It's quarter to ten in the night",
            "23,45,It's quarter to twelve in the night"
    })
    public void speakQuarterToHour(int hour, int minute, String expectedSpeech){
        LocalTime time = LocalTime.of(hour, minute);
        String actualSpeech =   new SpeakingClock().tellMeTheTime(time);

        Assertions.assertThat(actualSpeech).isEqualTo(expectedSpeech);
    }

    //Fuzzy responses: "It's almost Y"
    @ParameterizedTest
    @CsvSource({
            "00,55, It's almost one in the morning",
            "10,55, It's almost eleven in the morning",
            "11,55, It's almost noon",
            "12,55, It's almost one in the afternoon",
            "16,55, It's almost evening",
            "20,55, It's almost night",
            "21,55, It's almost ten in the night",
            "23,55, It's almost midnight"
    })
    public void speakItsAlmostNextHour(int hour, int min, String expectedSpeech){
        LocalTime time = LocalTime.of(hour, min);
        String actualSpeech = new SpeakingClock().tellMeTheTime(time);

        Assertions.assertThat(actualSpeech).isEqualTo(expectedSpeech);
    }

    //Fuzzy responses: "It's just after X"
    @ParameterizedTest
    @CsvSource({
            "00,5, It's just after midnight",
            "10,5, It's just after ten in the morning",
            "12,05, It's just after noon",
            "13,05, It's just after one in the afternoon",
            "17,05, It's just after evening",
            "18,05, It's just after six in the evening",
            "21,05, It's just after night",
            "23,05, It's just after eleven in the night",
    })
    public void speakItsJustAfterHour(int hour, int min, String expectedSpeech){
        LocalTime time = LocalTime.of(hour, min);
        String actualSpeech = new SpeakingClock().tellMeTheTime(time);

        Assertions.assertThat(actualSpeech).isEqualTo(expectedSpeech);
    }
}
