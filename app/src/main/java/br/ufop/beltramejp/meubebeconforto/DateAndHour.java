package br.ufop.beltramejp.meubebeconforto;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;

public class DateAndHour implements Comparable<DateAndHour>, Serializable {
    private int day, mouth, year;
    private int hour, minute;

    public DateAndHour(int day, int mouth, int year, int hour, int minute) {
        this.day = day;
        this.mouth = mouth;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    public DateAndHour(String date, String hour) {
        String[] date_data = date.split("/");
        String[] hour_data = hour.split(":");

        this.day = Integer.parseInt(date_data[0]);
        this.mouth = Integer.parseInt(date_data[1]);
        this.year = Integer.parseInt(date_data[2]);

        this.hour = Integer.parseInt(hour_data[0]);
        this.minute = Integer.parseInt(hour_data[1]);
    }

    public String toStringDate() {
        return String.format("%02d/%02d/%d", day, mouth, year);
    }

    public String toStringHour() {
        return String.format("%02d:%02d", hour, minute);
    }


    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMouth() {
        return mouth;
    }

    public void setMouth(int mouth) {
        this.mouth = mouth;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public int compareTo(@NonNull DateAndHour o) {
        if(year > o.getYear()) {
            return -1;
        }
        if(mouth > o.getMouth()) {
            return -1;
        }
        if(day > o.getDay()) {
            return -1;
        }
        if(hour > o.getHour()) {
            return -1;
        }
        if(minute > o.getMinute()) {
            return -1;
        }

        if(toString().equals(o.toString())) {
            return 0;
        }

        return 1;
    }

    @Override
    public String toString(){
        return toStringDate() + " " + toStringHour();
    }
}
