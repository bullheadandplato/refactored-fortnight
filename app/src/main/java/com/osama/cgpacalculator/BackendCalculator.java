package com.osama.cgpacalculator;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by home on 3/8/17.
 *
 */

public class BackendCalculator {
    private static final String TAG=BackendCalculator.class.getCanonicalName();
    private static BackendCalculator instance;
    private int totalCrs=0;
    private HashMap<Integer,Float> obtainedCrs=new HashMap<>();
    private HashMap<Integer,Integer> creditHours=new HashMap<>();
    private BackendCalculator(){

    }
    public void setTotalCrs(int index,int totalCrs) {

        creditHours.put(index,totalCrs);
    }
    public void addObtainedCrs(int index,float number){
        this.obtainedCrs.put(index,number);
        Log.d(TAG, "addObtainedCrs: hashmap size is: "+obtainedCrs.size());
    }
    public float calculateCgpa(){
        float sum=0f;
        Object[] values=obtainedCrs.values().toArray();
        for (Object value:values){
            Log.d(TAG, "calculateCgpa: value is: "+value);
            sum+=(float)value;
        }
        values=creditHours.values().toArray();
        for (Object val:values) {
            totalCrs+=(int)val;
        }
        Log.d(TAG, "calculateCgpa: total credit hours: "+totalCrs);
        Log.d(TAG, "calculateCgpa: Sum is: "+sum);
        sum=sum/totalCrs;
        totalCrs=0;
        return sum;
    }
    public static BackendCalculator getInstance(){
        if(instance==null){
            instance=new BackendCalculator();
        }
        return instance;
    }

    public void removeIfAdded(int adapterPosition) {
        if(obtainedCrs.containsKey(adapterPosition)){
            obtainedCrs.remove(adapterPosition);
        }
        if(creditHours.containsKey(adapterPosition)){
            creditHours.remove(adapterPosition);
        }
    }
}
