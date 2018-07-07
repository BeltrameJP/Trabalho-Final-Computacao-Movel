package br.ufop.beltramejp.meubebeconforto;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BabyActivitiesAdapter extends BaseAdapter {

    private ArrayList<BabyActivities> babyActivitiesList;
    private Context context;
    private TextView textActivity, textData, textHour;

    public BabyActivitiesAdapter() {    }

    public BabyActivitiesAdapter(Context context, ArrayList<BabyActivities> babyActivitiesList) {
        this.babyActivitiesList = babyActivitiesList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return babyActivitiesList.size();
    }

    @Override
    public Object getItem(int position) {
        return babyActivitiesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return babyActivitiesList.get(position).getIdActivity();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BabyActivities babyActivities = babyActivitiesList.get(position);

        LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_baby_activities_adapter, null);

        textActivity = view.findViewById(R.id.textActivityAdapter);
        textData = view.findViewById(R.id.textDataAdapter);
        textHour = view.findViewById(R.id.textHourAdapter);

        textActivity.setText(babyActivities.getActivityText());
        textData.setText(babyActivities.getDateStart());
        textHour.setText(babyActivities.getHourStart());

        return view;

    }
}
