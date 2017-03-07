package com.osama.cgpacalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mSubjectsList;
    private SubjectsAdapter mAdapter;
    private static final int INITIAL_NUMBER_OF_SUBJECTS=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mSubjectsList=(RecyclerView)findViewById(R.id.subjects_list);
        mAdapter=new SubjectsAdapter(this,INITIAL_NUMBER_OF_SUBJECTS);
        mSubjectsList.setLayoutManager(new LinearLayoutManager(this));
        mSubjectsList.setAdapter(mAdapter);
    }
}
