package com.osama.cgpacalculator;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SubjectsAdapter.SubjectsAdapterCallbacks {

    private RecyclerView    mSubjectsList;
    private SubjectsAdapter mAdapter;
    private int             numberOfSubjects    = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupRecyclerView();
        animateWhat();

        findViewById(R.id.calculate_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateButtonClick(view);
            }
        });
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
            case R.id.about_menuitem:{
                showAboutDialog();
                break;
            }
            case R.id.grade_detail_menuitem:{
                startActivity(new Intent(this,GradeDetailsActivity.class));
                break;
            }
        }
        return true;
    }

    private boolean isShowingCr = false;
    private boolean isShowingLi = false;

    @SuppressLint("InflateParams")
    private void showAboutDialog() {
        final ScaleAnimation animation=new ScaleAnimation(0f,1f,0f,1f);
        animation.setDuration(800);

        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.about_dialog);

        final FrameLayout frame = ((FrameLayout)dialog.findViewById(R.id.about_content_layout));
        frame.addView(getLayoutInflater().inflate(R.layout.intro_section_layout,null));

        dialog.findViewById(R.id.credit_dialog_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frame.removeAllViews();
                isShowingLi=false;
                if(!isShowingCr){
                    frame.addView(getLayoutInflater().inflate(R.layout.credits_layout,null));
                    isShowingCr=true;
                    frame.setAnimation(animation);
                }else{
                    frame.addView(getLayoutInflater().inflate(R.layout.intro_section_layout,null));
                    isShowingCr=false;
                    frame.setAnimation(animation);
                }
                frame.animate();
            }
        });

        dialog.findViewById(R.id.license_dialog_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frame.removeAllViews();
                isShowingCr=false;
                if(!isShowingLi){
                    frame.addView(getLayoutInflater().inflate(R.layout.license_layout,null));
                    isShowingLi=true;
                    frame.setAnimation(animation);
                }else{
                    frame.addView(getLayoutInflater().inflate(R.layout.intro_section_layout,null));
                    isShowingLi=false;
                    frame.setAnimation(animation);
                }
                frame.animate();
            }
        });

        dialog.show();
    }

    private void setupRecyclerView() {
        mSubjectsList   = (RecyclerView)findViewById(R.id.subjects_list);
        mAdapter        = new SubjectsAdapter(this,numberOfSubjects);

        mSubjectsList.setLayoutManager(new LinearLayoutManager(this));
        mSubjectsList.setAdapter(mAdapter);

    }
    private static final int MAX=6;
    public void addNewSubject(View view){
        if (numberOfSubjects > MAX) {
            Snackbar.make(view,"Do you really want to add another subject?",Snackbar.LENGTH_LONG)
                    .setAction("Yes", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addSubject();
                        }
                    }).show();
        }else {
            addSubject();
        }

    }
    private void addSubject(){
        mAdapter.setNumberofSubjects(++numberOfSubjects);
        mAdapter.notifyItemInserted(numberOfSubjects-1);
    }
    private int getCgpaColor(float cgpa){
        if (cgpa>=3){
            return ContextCompat.getColor(this,R.color.colorGradeAPlus);
        }else if (cgpa>=2.5){
            return ContextCompat.getColor(this,R.color.colorGradeBPlus);
        }else if (cgpa>=2){
            return ContextCompat.getColor(this,R.color.colorGradeCPlus);
        }else if (cgpa>=1.5){
            return ContextCompat.getColor(this,R.color.colorGradeD);
        }else {
            return ContextCompat.getColor(this,R.color.colorGradeF);
        }
    }

    @SuppressLint("SetTextI18n")
    public void calculateButtonClick(View view){
        float goo =
                BackendCalculator.getInstance().calculateCgpa(
                        mAdapter.getPreviousCrHrs(),
                        mAdapter.getPreviousCgpa()
                );

        TextView textView   = ((TextView)findViewById(R.id.show_cgpa_textview));
        textView.setText("GPA: "+goo);
        gpaAnimation(textView);
        textView.setTextColor(getCgpaColor(goo));

    }
    //trying to animate something
    private void animateWhat(){
        Animation animation = new ScaleAnimation(0f,1f,0f,1f);
        animation.setDuration(800);
        mSubjectsList.setAnimation(animation);
        mSubjectsList.animate();
    }
    private void gpaAnimation(View view){
        ScaleAnimation animation = new ScaleAnimation(0f,1f,1f,1f);
        animation.setDuration(500);
        view.setAnimation(animation);
        view.animate();
    }

    @Override
    public void moveToPos(int pos) {
        if (pos==-1){
            return;
        }
        mSubjectsList.scrollToPosition(pos);
    }
}
