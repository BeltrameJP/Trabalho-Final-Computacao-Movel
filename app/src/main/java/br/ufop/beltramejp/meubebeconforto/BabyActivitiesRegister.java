package br.ufop.beltramejp.meubebeconforto;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class BabyActivitiesRegister extends AppCompatActivity {

    public static final int BABY_REGISTER_ADD = 0;
    public static final int BABY_REGISTER_EDIT = 1;

    private EditText dateStartText, hourStartText, amountText, notesText, durationText;
    private TextView activityRegister;
    private Button saveButton, deleteButton;
    private Spinner spinnerActivities;
    private int spinnerPosition;

    private BabyData babyData;

    private Intent it;
    private int it_position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        babyData = BabyData.getInstance(BabyActivitiesRegister.this);

        setContentView(R.layout.activity_baby_activities_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dateStartText = findViewById(R.id.dateStartActivity);
        hourStartText = findViewById(R.id.hourStartActivity);

        amountText = findViewById(R.id.amountActivity);
        notesText = findViewById(R.id.notesActivity);

        durationText = findViewById(R.id.durationText);

        saveButton = findViewById(R.id.buttonSave);
        deleteButton = findViewById(R.id.buttonDelete);

        //Spinner
        spinnerActivities = findViewById(R.id.spinnerActivities);
        initSpinnerActivities();

        activityRegister = findViewById(R.id.activityRegisterText);

        it = getIntent();
        it_position = it.getIntExtra("position", 0);

        if (it.getAction().equals(Intent.ACTION_EDIT)) {
            activityRegister.setText(R.string.activity_Edit);

            BabyActivities babyActivities = BabyData.getInstance(this).
                    getBabyActivitiesArrayList().
                    get(it_position);

            dateStartText.setText(babyActivities.getDateStart());
            hourStartText.setText(babyActivities.getHourStart());
            amountText.setText("" + babyActivities.getAmount());
            durationText.setText("" + babyActivities.getDuration());
            notesText.setText(babyActivities.getNotes());

            deleteButton.setVisibility(Button.VISIBLE);

        } else if (it.getAction().equals(Intent.ACTION_MAIN)) {
            Calendar calInst = Calendar.getInstance();

            String dateString = String.format("%02d/%02d/%d",
                                calInst.get(calInst.DAY_OF_MONTH), calInst.get(calInst.MONTH),calInst.get(calInst.YEAR));
            String hourString = String.format("%02d:%02d",
                                calInst.get(calInst.HOUR_OF_DAY),calInst.get(calInst.MINUTE));

            dateStartText.setText(dateString);
            hourStartText.setText(hourString);

            activityRegister.setText(R.string.activity_Register);
        }


    }

    public void onClickSaveActivity(View view) {
        int amount, duration;

        String dateStart, dateEnd, hourStart, hourEnd, notes;

        babyData = BabyData.getInstance(this);

        dateStart = dateStartText.getText().toString();

        hourStart = hourStartText.getText().toString();

        if (amountText.getText().toString().isEmpty()) {
            amount = 0;
        } else {
            amount = Integer.parseInt(amountText.getText().toString());
        }

        if (durationText.getText().toString().isEmpty()) {
            duration = 0;
        } else {
            duration = Integer.parseInt(durationText.getText().toString());
        }

        notes = notesText.getText().toString();
        BabyActivities babyActivity;

        try {
            babyActivity = new BabyActivities(spinnerPosition, notes, dateStart, hourStart, duration, amount);
            if (it.getAction().equals(Intent.ACTION_MAIN)) {
                babyData.addBabyActivity(BabyActivitiesRegister.this, babyActivity);
            } else if (it.getAction().equals(Intent.ACTION_EDIT)) {
                babyData.getBabyActivitiesArrayList().set(it_position, babyActivity);
            }
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Dados Preenchidos Errados.", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void initSpinnerActivities() {
        ArrayAdapter<String> activities = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, BabyActivities.ACTIVITIES);

        spinnerActivities.setAdapter(activities);
        spinnerActivities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerPosition = 0;
            }
        });

    }

    public void onClickDelete(View view) {
        if(it.getAction() == Intent.ACTION_EDIT){
            babyData.getBabyActivitiesArrayList().remove(it_position);
        }
        finish();
    }

    public void onClickDateStartDatePicker(View view) {
        Calendar currentTime = Calendar.getInstance();

        int day = currentTime.get(currentTime.DAY_OF_MONTH);
        int month = currentTime.get(currentTime.MONTH);
        int year = currentTime.get(currentTime.YEAR);

        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int newMonth = month+1;
                dateStartText.setText(dayOfMonth + "/" + newMonth + "/" + year);
            }
        }, year, month, day);

        datePickerDialog.setTitle("Selecione a Data");
        datePickerDialog.show();
    }

    public void onClickHourStartTimePicker(View view) {
        Calendar currentTime = Calendar.getInstance();

        int hour = currentTime.get(currentTime.HOUR_OF_DAY);
        int minute = currentTime.get(currentTime.MINUTE);

        TimePickerDialog timePicker;
        timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hourStartText.setText(String.format("%02d:%02d",hourOfDay,minute));
            }
        }, hour, minute, true); //True para 24h

        timePicker.setTitle("Escolha a Hora");
        timePicker.show();

    }
}
