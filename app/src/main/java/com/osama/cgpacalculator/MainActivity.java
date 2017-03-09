package com.osama.cgpacalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.reset_menuitem:{
                recreate();
                break;
            }
        }
        return true;
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
    public void calculateButtonClick(View view){
       float goo= BackendCalculator.getInstance().calculateCgpa();
        ((TextView)findViewById(R.id.show_cgpa_textview)).setText("GPA: "+goo);
    }

}
