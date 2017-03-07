package com.osama.cgpacalculator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

    public SubjectsAdapter(Context context,int numberOfSubjects){
        this.mContext=context;
        this.numberOfSubjects=numberOfSubjects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.single_item,parent));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return numberOfSubjects;

    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
