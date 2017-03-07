package com.osama.cgpacalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mSubjectsList;
    private SubjectsAdapter mAdapter;
    private int numberOfSubjects=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mSubjectsList=(RecyclerView)findViewById(R.id.subjects_list);
        mAdapter=new SubjectsAdapter(this,numberOfSubjects);
        mSubjectsList.setLayoutManager(new LinearLayoutManager(this));
        mSubjectsList.setAdapter(mAdapter);
    }
    public void addNewSubject(View view){
        mAdapter.setNumberofSubjects(++numberOfSubjects);
        mAdapter.notifyItemInserted(numberOfSubjects-1);
    }
}
