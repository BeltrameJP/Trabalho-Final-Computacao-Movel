package br.ufop.beltramejp.meubebeconforto;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;


public class BabyProfile extends AppCompatActivity {

    private TextView babyName, babyBirthday, babyGender, babyLastActivity, babyMostly;
    private BabyData babyData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.edit_baby_profile);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BabyProfile.this, BabyRegister.class));
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        babyData = BabyData.getInstance(this);

        babyName = findViewById(R.id.baby_name);
        babyBirthday = findViewById(R.id.baby_birthday);
        babyGender = findViewById(R.id.baby_gender);
        babyLastActivity = findViewById(R.id.baby_last_activity);
        babyMostly = findViewById(R.id.baby_mostly_recurring);



    }

    @Override
    protected void onResume() {
        super.onResume();
        babyName.setText(babyData.getName());
        babyBirthday.setText(babyData.getDateBirth());
        babyGender.setText("" + babyData.getSex());

        if(babyData.getBabyActivitiesArrayList().size() > 0){
            babyLastActivity.setText(babyData.getBabyActivitiesArrayList()
                    .get(babyData.getBabyActivitiesArrayList().size()-1)
                    .getActivityText());
        }

        babyMostly.setText(" ");

    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}
