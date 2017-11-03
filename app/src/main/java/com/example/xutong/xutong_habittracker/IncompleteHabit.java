package com.example.xutong.xutong_habittracker;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by TongTong on 30/09/2016.
 */
public class IncompleteHabit extends Habit {

    /**
     * Constructor.
     * @param habitName habit's name.
     * @param habitDate habit's adding date.
     * @param occurDays habit's occurring days of a week.
     * @throws InvalidHabitException if habitName is not specified properly.
     */
    public IncompleteHabit(String habitName, Calendar habitDate, ArrayList<String> occurDays) throws InvalidHabitException {
        super(habitName, habitDate, occurDays);
    }
}
