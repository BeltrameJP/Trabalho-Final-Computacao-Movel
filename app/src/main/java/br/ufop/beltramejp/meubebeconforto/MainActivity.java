package br.ufop.beltramejp.meubebeconforto;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BabyData babyData;
    private TextView headerNameBaby;
    private Spinner spinnerAcitivities;
    private ListView listViewAcitivities;
    private ArrayList<BabyActivities> babyActivitiesSelected;
    private Button buttonOrder;
    private boolean orderGrowing = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_activity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it_addActivity = new Intent(MainActivity.this, BabyActivitiesRegister.class);
                it_addActivity.setAction(Intent.ACTION_MAIN);
                startActivity(it_addActivity);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //My Code Starts Here
        babyData = BabyData.getInstance(this);

        spinnerAcitivities = findViewById(R.id.spinnerActivitiesMain);
        initSpinner();

        listViewAcitivities = findViewById(R.id.listActivities);
        listViewAcitivities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(MainActivity.this, BabyActivitiesRegister.class);
                int realPosition = babyData.returnPositionByID(babyActivitiesSelected.get(position).getIdActivity());
                it.putExtra("position", realPosition);
                it.setAction(Intent.ACTION_EDIT);
                startActivity(it);
            }
        });

        buttonOrder = findViewById(R.id.buttonOrder);
        babyActivitiesSelected = babyData.getBabyActivitiesSelectedArrayList(spinnerAcitivities.getSelectedItemPosition());

        if(!babyData.isBabyCadastred()){
            Intent it = new Intent(this,BabyRegister.class);
            startActivity(it);
        }


    }


    public void initSpinner() {
        ArrayAdapter<String> activities = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, BabyActivities.ACTIVITIES);

        spinnerAcitivities.setAdapter(activities);
        spinnerAcitivities.setSelection(BabyActivities.ACTIVITIES.length - 1);
        spinnerAcitivities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                babyActivitiesSelected = babyData.getBabyActivitiesSelectedArrayList(spinnerAcitivities.getSelectedItemPosition());
                refreshBabyAcitivitiesAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void onResume() {
        super.onResume();
        refreshBabyAcitivitiesAdapter();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_my_baby:
                Intent it_myBaby = new Intent(this, BabyProfile.class);
                startActivity(it_myBaby);
                break;
            case R.id.nav_add_activity:
                Intent it_addActivity = new Intent(this, BabyActivitiesRegister.class);
                it_addActivity.setAction(Intent.ACTION_MAIN);
                startActivity(it_addActivity);
                break;
            case R.id.nav_baby_preferences:
                Intent it_babyPreferences = new Intent(this, BabyRegister.class);
                startActivity(it_babyPreferences);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void refreshBabyAcitivitiesAdapter() {
        listViewAcitivities.setAdapter(new BabyActivitiesAdapter(this, babyActivitiesSelected));
    }

    public void onClickOrder(View view) {
        orderGrowing = !orderGrowing;
        if (orderGrowing) {
            Log.v("order", "Order Growing");
            Collections.sort(babyData.getBabyActivitiesArrayList(), new Comparator<BabyActivities>() {
                @Override
                public int compare(BabyActivities o1, BabyActivities o2) {
                    return o1.getDateAndHour().compareTo(o2.getDateAndHour());
                }
            });
            buttonOrder.setText(R.string.order_down);
        } else {
            Log.v("order", "Order Not");
            Collections.sort(babyData.getBabyActivitiesArrayList(), new Comparator<BabyActivities>() {
                @Override
                public int compare(BabyActivities o1, BabyActivities o2) {
                    return -1*o1.getDateAndHour().compareTo(o2.getDateAndHour());
                }
            });
            buttonOrder.setText(R.string.order_up);
        }

        babyActivitiesSelected = babyData.getBabyActivitiesSelectedArrayList(spinnerAcitivities.getSelectedItemPosition());
        refreshBabyAcitivitiesAdapter();
    }
}
