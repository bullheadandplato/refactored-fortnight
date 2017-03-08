package com.osama.cgpacalculator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by home on 3/7/17.
 *
 */

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.ViewHolder> {

    private Context mContext;
    private int numberOfSubjects;
    private BackendCalculator calculator;
    private static final int VIEW_TWO=132;
    private static final String TAG=SubjectsAdapter.class.getCanonicalName();
    private static final int A_PLUS=1;
    private static final int A=2;
    private static final int B_PLUS=3;
    private static final int B=4;
    private static final int B_MINUS=5;
    private static final int C_PLUS=6;
    private static final int C=7;
    private static final int D=8;
    private static final int F=9;

    public SubjectsAdapter(Context context,int numberOfSubjects){
        this.mContext=context;
        this.numberOfSubjects=numberOfSubjects;
        calculator=BackendCalculator.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==VIEW_TWO){
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.add_new_layout,parent,false),VIEW_TWO);
        }
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cardview_subjects,parent,false),0);
    }

    @Override
    public int getItemViewType(int position) {
        if(position>=numberOfSubjects){
            Log.d(TAG, "getItemViewType: returning new view");
            return VIEW_TWO;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position>=numberOfSubjects){
            return;
        }
        Spinner spinner=holder.spinner;
        TextView view=holder.textView;
        Spinner spinner2=holder.spinner2;
        setItemChangeListener(spinner,spinner2,view);
        setOnCrChange(spinner2,spinner,view);
    }
    private void setItemChangeListener(Spinner spinner, final Spinner spinner2, final TextView textView){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    return;
                }
                final int crNumbers=getCrs(spinner2.getSelectedItemPosition());
                float number=getGradeNumber(i);
                Log.d(TAG, "onItemSelected: credit number is: "+crNumbers+" grade is: "+number);

                number=number*crNumbers;
                calculator.setTotalCrs(i-1,crNumbers);
                calculator.addObtainedCrs(i-1,number);
                Log.d(TAG, "onItemSelected: setting textview to: "+number);

                textView.setText(""+number);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private int getCrs(int selectedItemPosition) {
        Log.d("test", "getCrs: selected item pos is: "+selectedItemPosition);
        switch (selectedItemPosition){
            case 1:
                return 4;
            case 2:
                return 3;
            case 3:
                return 2;
            case 4:
                return 1;
            default: return 0;
        }
    }

    private void setOnCrChange(final Spinner spinner,final Spinner spinner2, final TextView textView){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    return;
                }
                int crNumber=getCrs(i);
                final float gradeNumber=getGradeNumber(spinner2.getSelectedItemPosition());

                Log.d(TAG, "onItemSelected: credit number is: "+crNumber+" grade is: "+gradeNumber);

                float number=gradeNumber*crNumber;
                Log.d(TAG, "onItemSelected: setting textview to: "+number);
                calculator.setTotalCrs(i-1,crNumber);
                calculator.addObtainedCrs(i-1,number);
                textView.setText(""+number);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private float getGradeNumber(int selectedItemPosition) {
        Log.d("test", "getGradeNumber: selected item pos is: "+selectedItemPosition);
        switch (selectedItemPosition){
            case A_PLUS:
                return 4.00f;
            case A:
                return 3.70f;
            case B_PLUS:
                return 3.40f;
            case B:
                return 3.00f;
            case B_MINUS:
                return 2.50f;
            case C_PLUS:
                return 2.00f;
            case C:
                return 1.50f;
            case D:
                return 1.00f;
            default:
                return 0.00f;
        }
    }

    @Override
    public int getItemCount() {
        return numberOfSubjects+1;

    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        Spinner spinner;
        TextView textView;
        Spinner spinner2;
        public ViewHolder(final View itemView,int pos) {
            super(itemView);
            if(pos==VIEW_TWO){
                return;
            }
            spinner=(Spinner)itemView.findViewById(R.id.grade_names_spinner);
            textView=(TextView)itemView.findViewById(R.id.grade_number_textview);
            spinner2=(Spinner)itemView.findViewById(R.id.cr_hours_spinner);
        }

    }

    public void setNumberofSubjects(int number){
        this.numberOfSubjects=number;
    }
}
