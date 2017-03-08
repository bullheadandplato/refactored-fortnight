package com.osama.cgpacalculator;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by home on 3/8/17.
 *
 */

public class BackendCalculator {
    private static final String TAG=BackendCalculator.class.getCanonicalName();
    private static BackendCalculator instance;
    private int totalCrs=0;
    private HashMap<Integer,Float> obtainedCrs=new HashMap<>();
    private BackendCalculator(){

    }
    public void setTotalCrs(int index,int totalCrs) {
        this.totalCrs += totalCrs;
    }
    public void addObtainedCrs(int index,float number){
        this.obtainedCrs.put(index,number);
        Log.d(TAG, "addObtainedCrs: hashmap size is: "+obtainedCrs.size());
    }
    public float calculateCgpa(){
        float sum=0f;
        for (int i=0;i<obtainedCrs.size();i++){
            sum+=obtainedCrs.get(i);
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
}
