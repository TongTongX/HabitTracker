package com.example.xutong.xutong_habittracker;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by TongTong on 25/09/2016.
 */

public class Habit {
    private String habitName;
    private Calendar habitDate;
    private ArrayList<String> occurDays = new ArrayList<>();
    private ArrayList<Calendar> fulfilDate = new ArrayList<>();

    /**
     * Constructor.
     * @param habitName habit's name.
     * @param habitDate habit's adding date.
     * @param occurDays habit's occurring days of a week.
     * @throws InvalidHabitException if habitName is not specified properly.
     */
    public Habit(String habitName,  Calendar habitDate, ArrayList<String> occurDays) throws InvalidHabitException{
        if (habitName.trim().isEmpty()) {
            throw new InvalidHabitException();
        }

        this.habitName = habitName;
        this.habitDate = habitDate;
        this.occurDays = occurDays;
    }

    /**
     * Add fulfilment date.
     * @param date the fulfilment data.
     */
    public void addFulfilDate(Calendar date) {
        this.fulfilDate.add(date);
    }

    @Override
    public String toString() {
        return this.habitName;
    }

    public String getHabitName() {
        return habitName;
    }

    public Calendar getHabitDate() {
        return habitDate;
    }

    public ArrayList<String> getOccurDays() {
        return occurDays;
    }

    public ArrayList<Calendar> getFulfilDate() {
        return fulfilDate;
    }

    public void setFulfilDate(ArrayList<Calendar> fulfilDate) {
        this.fulfilDate = fulfilDate;
    }
}