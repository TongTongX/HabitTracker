package com.example.xutong.xutong_habittracker;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class HabitUnitTest  {

    private String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    @Test(expected = InvalidHabitException.class)
    public void testEmptyHabitName() throws InvalidHabitException{
        String habitName = " ";
        Calendar habitDate = Calendar.getInstance();
        ArrayList<String> occurDays = new ArrayList<>();
        occurDays.add(week[0]);
        Habit habit = new Habit(habitName, habitDate, occurDays);
    }

    @Test
    public void testValidHabitName() throws InvalidHabitException{
        String habitName = "test001";
        Calendar habitDate = Calendar.getInstance();
        ArrayList<String> occurDays = new ArrayList<>();
        occurDays.add(week[0]);
        Habit habit = new Habit(habitName, habitDate, occurDays);

        assertEquals(habit.getHabitName(), habitName);
        assertEquals(habit.getHabitDate(), habitDate);
        assertEquals(habit.getOccurDays(), occurDays);
    }

    @Test(expected = InvalidHabitException.class)
    public void testEmptyOccurDays() throws InvalidHabitException{
        String habitName = "test002";
        Calendar habitDate = Calendar.getInstance();
        ArrayList<String> occurDays = new ArrayList<>();
        Habit habit = new Habit(habitName, habitDate, occurDays);
    }

    @Test
    public void testValidOccurDays() throws InvalidHabitException{
        String habitName = "test002";
        Calendar habitDate = Calendar.getInstance();
        ArrayList<String> occurDays = new ArrayList<>();
        occurDays.add(week[1]);
        Habit habit = new Habit(habitName, habitDate, occurDays);

        assertEquals(habit.getHabitName(), habitName);
        assertEquals(habit.getHabitDate(), habitDate);
        assertEquals(habit.getOccurDays(), occurDays);
    }
}