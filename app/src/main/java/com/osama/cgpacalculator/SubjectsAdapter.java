package com.osama.cgpacalculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
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

    private Context             mContext;
    private int                 numberOfSubjects;
    private BackendCalculator   calculator;
    private ViewHolder          thirdViewHolder;
    private SubjectsAdapterCallbacks    mCallbacks;

    private static final String TAG         = SubjectsAdapter.class.getCanonicalName();
    private static final int    VIEW_TWO    = 132;
    private static final int    VIEW_THREE  = 324;
    private static final int    A_PLUS      = 1;
    private static final int    A           = 2;
    private static final int    B_PLUS      = 3;
    private static final int    B           = 4;
    private static final int    B_MINUS     = 5;
    private static final int    C_PLUS      = 6;
    private static final int    C           = 7;
    private static final int    D           = 8;

    public SubjectsAdapter(Context context,int numberOfSubjects){
        Log.i(TAG, "SubjectsAdapter: created new instance of Adapter");
        this.mContext           = context;
        this.numberOfSubjects   = numberOfSubjects;
        calculator              = BackendCalculator.getInstance();
        try{
            mCallbacks= (SubjectsAdapterCallbacks) mContext;
        }catch (ClassCastException ex){
            throw new IllegalArgumentException("Must implement "+SubjectsAdapterCallbacks.class.getName());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==VIEW_TWO){
            return new ViewHolder(
                    LayoutInflater.from(mContext).inflate(R.layout.add_new_layout,parent,false),
                    VIEW_TWO
            );
        }else if(viewType==VIEW_THREE){
            return thirdViewHolder  = new ViewHolder(
                    LayoutInflater.from(mContext).inflate(R.layout.previous_cgpa_layout,parent,false),
                    VIEW_THREE
            );
        }
        return new ViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.cardview_subjects,parent,false),
                0
        );
    }

    @Override
    public int getItemViewType(int position) {
        if(position==numberOfSubjects){
            return VIEW_TWO;
        }else if(position>numberOfSubjects){
            return VIEW_THREE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return numberOfSubjects+2;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        Spinner             spinner;
        TextView            textView;
        Spinner             spinner2;
        TextInputEditText   previousCgpaEdit;
        TextInputEditText   previousCrHrsEdit;

        public ViewHolder(final View itemView,int pos) {
            super(itemView);
            if(pos==VIEW_TWO){
                return;
            }else if(pos==VIEW_THREE){
                previousCgpaEdit    = (TextInputEditText)itemView.findViewById(R.id.previous_cgpa);
                previousCrHrsEdit   = (TextInputEditText)itemView.findViewById(R.id.previous_credit_hours);
                return;
            }

            spinner     = (Spinner)itemView.findViewById(R.id.grade_names_spinner);
            textView    = (TextView)itemView.findViewById(R.id.grade_number_textview);
            spinner2    = (Spinner)itemView.findViewById(R.id.cr_hours_spinner);

            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                    int         crNumber    = getCrs(i);
                    final float gradeNumber = getGradeNumber(spinner.getSelectedItemPosition());
                    float       number      = gradeNumber*crNumber;
                    mCallbacks.moveToPos(getAdapterPosition());
                    if((i>0)){
                        calculator.setTotalCrs(getAdapterPosition(),crNumber);
                        calculator.addObtainedCrs(getAdapterPosition(),number);
                        textView.setText(""+number);
                    }else{
                        calculator.removeIfAdded(getAdapterPosition());
                    }
                    textView.setText(""+number);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    final int crNumbers = getCrs(spinner2.getSelectedItemPosition());
                    float     number    = getGradeNumber(i);
                    mCallbacks.moveToPos(getAdapterPosition());
                    number  = number*crNumbers;
                    if (i>0){
                        itemView.setBackgroundColor(getGradeColor(i));
                    }else{
                        itemView.setBackgroundColor(Color.WHITE);
                    }

                    if((crNumbers>0 && i>0)){
                        calculator.setTotalCrs(getAdapterPosition(),crNumbers);
                        calculator.addObtainedCrs(getAdapterPosition(),number);
                    }else{
                        calculator.removeIfAdded(getAdapterPosition());
                    }
                    textView.setText(""+number);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }

    }

    public float getPreviousCgpa(){
        String cgpa = thirdViewHolder.previousCgpaEdit.getText().toString();
        if (cgpa.length()<1){
            return 0;
        }
        try {
            return Float.valueOf(cgpa);
        }catch (NumberFormatException ex){
            thirdViewHolder.previousCgpaEdit.setError("Please input correct cgpa");
            return 0f;
        }
    }

    public int getPreviousCrHrs(){
        String crs=thirdViewHolder.previousCrHrsEdit.getText().toString();
        if (crs.length()<1){
            return 0;
        }
        try{
            return Integer.valueOf(crs);
        }catch (NumberFormatException ex){
            ex.printStackTrace();
            thirdViewHolder.previousCrHrsEdit.setError("Please input correct number of credit hours");
            return 0;
        }
    }
    public void setNumberofSubjects(int number){
        this.numberOfSubjects   = number;
    }

    private int getCrs(int selectedItemPosition) {
        switch (selectedItemPosition){
            case 1:  return 4;
            case 2:  return 3;
            case 3:  return 2;
            case 4:  return 1;
            default: return 0;
        }
    }

    private float getGradeNumber(int selectedItemPosition) {
        switch (selectedItemPosition){
            case A_PLUS:    return 4.00f;
            case A:         return 3.70f;
            case B_PLUS:    return 3.40f;
            case B:         return 3.00f;
            case B_MINUS:   return 2.50f;
            case C_PLUS:    return 2.00f;
            case C:         return 1.50f;
            case D:         return 1.00f;
            default:        return 0.00f;
        }
    }
    private int getGradeColor(int gradeNumber){
        switch (gradeNumber){
            case A_PLUS:    return ContextCompat.getColor(mContext,R.color.colorGradeAPlus);
            case A:         return ContextCompat.getColor(mContext,R.color.colorGradeA);
            case B_PLUS:    return ContextCompat.getColor(mContext,R.color.colorGradeBPlus);
            case B:         return ContextCompat.getColor(mContext,R.color.colorGradeB);
            case B_MINUS:   return ContextCompat.getColor(mContext,R.color.colorGradeBMinus);
            case C_PLUS:    return ContextCompat.getColor(mContext,R.color.colorGradeCPlus);
            case C:         return ContextCompat.getColor(mContext,R.color.colorGradeC);
            case D:         return ContextCompat.getColor(mContext,R.color.colorGradeD);
            default:        return ContextCompat.getColor(mContext,R.color.colorGradeF);
        }
    }
    public interface SubjectsAdapterCallbacks{
        void moveToPos(int pos);
    }

}
