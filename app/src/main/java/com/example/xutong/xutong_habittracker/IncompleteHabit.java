package com.example.xutong.xutong_habittracker;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by TongTong on 30/09/2016.
 */
public class IncompleteHabit extends Habit {
    public IncompleteHabit(String habitName, Calendar habitDate, ArrayList<String> occurDays) throws InvalidHabitException {
        super(habitName, habitDate, occurDays);
    }
}
