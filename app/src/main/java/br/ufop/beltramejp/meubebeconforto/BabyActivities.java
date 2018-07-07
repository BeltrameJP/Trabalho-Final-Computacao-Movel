package br.ufop.beltramejp.meubebeconforto;

import java.io.Serializable;

public class BabyActivities implements Serializable{
    public static final String [] ACTIVITIES =
            {
                    "Mamada",
                    "Mamadeira",
                    "Troca de Fralda",
                    "Soninho",
                    "Medicamentos",
                    "Outros",
                    "--"
            };

    private  int duration;
    private int idActivity, activity;
    private String notes;
    private DateAndHour dateAndHour;
    private int amount;

    public BabyActivities(int activity, String notes, String dateStart,  String hourStart, int duration, int amount) {

        this.idActivity = BabyData.activityNextID();
        this.activity = activity;

        dateAndHour = new DateAndHour(dateStart, hourStart);

        this.duration = duration;

        this.notes = notes;
        this.amount = amount;
    }

    public String getActivityText(){
        return ACTIVITIES[activity];
    }

    public String getDateStart(){
        return dateAndHour.toStringDate();
    }

    public String getHourStart(){
        return dateAndHour.toStringHour();
    }

    public DateAndHour getDateAndHour() {
        return dateAndHour;
    }

    public void setDateAndHour(DateAndHour dateAndHour) {
        this.dateAndHour = dateAndHour;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(int idActivity) {
        this.idActivity = idActivity;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


}
