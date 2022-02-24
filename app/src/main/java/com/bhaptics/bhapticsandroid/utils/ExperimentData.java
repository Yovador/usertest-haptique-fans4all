package com.bhaptics.bhapticsandroid.utils;

import java.util.Calendar;
import java.util.Date;

public class ExperimentData {

    public ExperimentData(String eventText){
        this.date = Calendar.getInstance().getTime();
        this.eventText = eventText;
    }

    Date date;
    String eventText;

}
