package com.osama.cgpacalculator;

import java.util.ArrayList;

/**
 * Created by home on 3/8/17.
 *
 */

public class BackendCalculator {
    private int totalCrs=0;
    private ArrayList<Float> obtainedCrs=new ArrayList<>();

    public void setTotalCrs(int totalCrs) {
        this.totalCrs = totalCrs;
    }
    public void addObtainedCrs(int index,float number){
        this.obtainedCrs.add(number);
    }
}
