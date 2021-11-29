package com.bhaptics.bhapticsandroid.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class JSONManager {

    ArrayList<ExperimentData> experimentDatas = new ArrayList() {
    };

    public void AddExperiment(ExperimentData experimentData){
        boolean canAdd = true;
        for (ExperimentData data:experimentDatas) {
            Log.d( "Yova" ,  " List : " + data.date + " / " + data.fileName );
            if(experimentData.date == data.date){
                canAdd = false;
            }
        }
        if(canAdd){
            experimentDatas.add(experimentData);
        }
        Log.d( "Yova" ,  experimentData.date + " / " + experimentData.fileName );

    }

}
