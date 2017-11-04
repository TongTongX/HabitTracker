package com.example.xutong.xutong_habittracker;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class InputOutputGSONUnitTest extends ActivityInstrumentationTestCase2 {

    private String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    public InputOutputGSONUnitTest(){
        super(com.example.xutong.xutong_habittracker.MainActivity.class);
    }

    @Override
    public void setUp() throws Exception{

    }

    @Override
    public void tearDown() throws Exception {

    }

    @Override
    public void runTest() throws Exception {

    }

    @Test
    public void testSaveInFile() throws InvalidHabitException, FileNotFoundException{
        String habitName = "test001";
        Calendar habitDate = Calendar.getInstance();
        ArrayList<String> occurDays = new ArrayList<>();
        occurDays.add(week[0]);
        Habit habit = new Habit(habitName, habitDate, occurDays);

        InputOutputGSON ioGson = new InputOutputGSON(new AddEditHabitActivity());
        ioGson.saveInFile(habit);
    }
}