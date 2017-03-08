package com.osama.cgpacalculator;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by home on 3/8/17.
 *
 */

public class BackendCalculator {
    private static final String TAG=BackendCalculator.class.getCanonicalName();
    private static BackendCalculator instance;
    private int totalCrs=0;
    private ArrayList<Float> obtainedCrs=new ArrayList<>();
    private ArrayList<Integer> selectedIndices=new ArrayList<>();
    private ArrayList<Integer> creditHours=new ArrayList<>();
    private BackendCalculator(){

    }
    public void setTotalCrs(int index,int totalCrs) {
        if(checkExistence(index)!=-1){
            totalCrs-=creditHours.get(index);
        }
        creditHours.add(totalCrs);
        this.totalCrs += totalCrs;
    }
    public void addObtainedCrs(int index,float number){
        if(checkExistence(index)!=-1){
            obtainedCrs.remove(index);
        }
        selectedIndices.add(index);
        this.obtainedCrs.add(number);
        Log.d(TAG, "addObtainedCrs: hashmap size is: "+obtainedCrs.size());
    }
    public float calculateCgpa(){
        float sum=0f;
        
        for (Float value:obtainedCrs){
            Log.d(TAG, "calculateCgpa: value is: "+value);
            sum+=value;
        }
        sum=sum/totalCrs;
        return sum;
    }
    public static BackendCalculator getInstance(){
        if(instance==null){
            instance=new BackendCalculator();
        }
        return instance;
    }
    private int checkExistence(int index){
        int toReturn=-1;
        for (Integer value:selectedIndices) {
            if(value==index){
                Log.d("remove", "checkExistence: Index already selected");
                toReturn=value;
                return toReturn;
            }
        }
        return toReturn;
    }
}
