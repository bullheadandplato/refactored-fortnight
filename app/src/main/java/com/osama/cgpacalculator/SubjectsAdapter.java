package com.osama.cgpacalculator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by home on 3/7/17.
 *
 */

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.ViewHolder> {

    private Context mContext;
    private int numberOfSubjects;
    private static final int VIEW_TWO=132;
    private static final String TAG=SubjectsAdapter.class.getCanonicalName();

    public SubjectsAdapter(Context context,int numberOfSubjects){
        this.mContext=context;
        this.numberOfSubjects=numberOfSubjects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==VIEW_TWO){
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.add_new_layout,parent,false));
        }
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cardview_subjects,parent,false));
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

    }

    @Override
    public int getItemCount() {
        return numberOfSubjects+1;

    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setNumberofSubjects(int number){
        this.numberOfSubjects=number;
    }
}
