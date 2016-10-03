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
     * @param habitName user specify habit habitName
     * @param habitDate
     *@param occurDays user specify event occurDays  @throws InvalidHabitException if habitName and occurDays of the habit is not specify
     */
    public Habit(String habitName,  Calendar habitDate, ArrayList<String> occurDays) throws InvalidHabitException{
        if (habitName.isEmpty() || habitName.trim().isEmpty()) {
            throw new InvalidHabitException();
        }
        this.habitName = habitName;
        this.habitDate = habitDate;
        this.occurDays = occurDays;
    }

    /**
     * @param habitName user specify habit habitName
     * @param habitDate
     *@param occurDays user specify event occurDays
     * @param fulfilDate a list of timestamps that user complete the habit   @throws InvalidHabitException if habitName and occurDays of the habit is not specify
     */
    public Habit(String habitName, Calendar habitDate, ArrayList<String> occurDays, ArrayList<Calendar> fulfilDate )
            throws InvalidHabitException{
        if (habitName.isEmpty() || habitName.trim().isEmpty()) {
            throw new InvalidHabitException();
        }
        this.habitName = habitName;
        this.habitDate = habitDate;
        this.occurDays = occurDays;
        this.fulfilDate = fulfilDate;
    }

    /**
     * A method to determine if the habit has been completed or not
     *
     * @return TRUE or FALSE
     */
    protected boolean ifComplete() {
        if (this.fulfilDate.isEmpty()) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    public void fulfilDate(Calendar time) {
        this.fulfilDate.add(time);
    }

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


    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public void setHabitDate(Calendar habitDate) {
        this.habitDate = habitDate;
    }

    public void setoccurDays(ArrayList<String> occurDays) {
        this.occurDays = occurDays;
    }

    public void setFulfilDate(ArrayList<Calendar> fulfilDate) {
        this.fulfilDate = fulfilDate;
    }
}