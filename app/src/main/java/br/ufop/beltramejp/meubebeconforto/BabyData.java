package br.ufop.beltramejp.meubebeconforto;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jp22_ on 15/05/2018.
 */

public class BabyData implements Serializable{

    private static BabyData babyData;
    private static final String ARCHIVE_NAME = "babyData.tmp";

    public static int COUNT_ACTIVITIES = 0;
    private String name = "";
    private String dateBirth = "";
    private char sex = ' ';
    private boolean babyCadastred = false;
    private ArrayList<BabyActivities> babyActivitiesArrayList;

    private BabyData(){
        COUNT_ACTIVITIES = 0;
        name = "";
        dateBirth = "";
        sex = ' ';
        babyActivitiesArrayList = new ArrayList<>();
    }


    public static BabyData getInstance(Context context){
        if(babyData == null){
            babyData = new BabyData();
            loadBabyData(context);
        }

        return babyData;
    }


    public static void saveBabyData(Context context){
        FileOutputStream fos;
        try{
            fos = context.openFileOutput(ARCHIVE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(babyData);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadBabyData(Context context){
        FileInputStream fis;
        try{
            fis = context.openFileInput(ARCHIVE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            babyData = (BabyData) ois.readObject();
            Log.v("vector size", "Size: " + babyData.getBabyActivitiesArrayList().size());
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int returnPositionByID(int id){
        for(int i=0; i<babyActivitiesArrayList.size();i++){
            if(babyActivitiesArrayList.get(i).getIdActivity() == id){
                return i;
            }
        }
        return -1;
    }

    public String getName() {
        return name;
    }

    public void setName(Context context, String name) {
        this.name = name;
        saveBabyData(context);
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Context context, String dateBirth) {
        this.dateBirth = dateBirth;
        saveBabyData(context);
    }

    public char getSex() {
        return sex;
    }

    public void setSex(Context context, char sex) {
        this.sex = sex;
        saveBabyData(context);
    }

    public static int activityNextID(){
        COUNT_ACTIVITIES++;
        return COUNT_ACTIVITIES;
    }

    public void addBabyActivity(Context context, BabyActivities babyActivity){
        babyActivitiesArrayList.add(babyActivity);
        saveBabyData(context);
    }

    public ArrayList<BabyActivities> getBabyActivitiesArrayList() {
        return babyActivitiesArrayList;
    }


    public ArrayList<BabyActivities> getBabyActivitiesSelectedArrayList(int activity){
        if(activity == BabyActivities.ACTIVITIES.length-1){
            return babyActivitiesArrayList;
        }

        ArrayList<BabyActivities> babyActivitiesSelectedArrayList = new ArrayList<BabyActivities>();

        for(BabyActivities activities : babyActivitiesArrayList){
            if(activities.getActivity() == activity){
                babyActivitiesSelectedArrayList.add(activities);
            }
        }
       return babyActivitiesSelectedArrayList;
    }

    public boolean isBabyCadastred() {
        return babyCadastred;
    }

    public void setBabyCadastred(boolean babyCadastred) {
        this.babyCadastred = babyCadastred;
    }
}
