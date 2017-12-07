package com.example.xutong.xutong_habittracker;

import android.content.Context;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Calendar;


@RunWith(RobolectricTestRunner.class)
@Config(constants= BuildConfig.class,manifest = "app/src/main/AndroidManifest.xml")
public class regressionTests {

    @Test
    public void oneMonthHabit() throws InvalidHabitException {
        MainActivity ma = Robolectric.setupActivity(MainActivity.class);
        InputOutputGSON ioGson = new InputOutputGSON(ma);

        ArrayList<String> freq = new ArrayList<String>();
        freq.add("Monday");

        Calendar date = Calendar.getInstance();
        date.set(2017,Calendar.NOVEMBER,1);
        Habit habit = new Habit("h", date, freq);
        ioGson.saveInFile(habit);
        Assert.assertTrue(ma.fileList().length==1);

        ArrayList<Calendar> fulfilDates = new ArrayList<>();

        Calendar date1 = Calendar.getInstance();
        date1.set(2017,Calendar.NOVEMBER,6);
        for(int i=0;i<4;i++){
            fulfilDates.add(date1);
        }

        Calendar date2 = Calendar.getInstance();
        date2.set(2017,Calendar.NOVEMBER,13);
        for(int i=0;i<6;i++){
            fulfilDates.add(date2);
        }


        Calendar date3 = Calendar.getInstance();
        date3.set(2017,Calendar.NOVEMBER,20);
        for(int i=0; i < 8; i++){
            fulfilDates.add(date3);
        }

        Calendar date4 = Calendar.getInstance();
        date4.set(2017,Calendar.NOVEMBER,27);
        for(int i=0; i < 3; i++){
            fulfilDates.add(date4);
        }

        habit.setFulfilDate(fulfilDates);

        Assert.assertEquals(5.25, ma.avgFulfillments(habit),0.001);
    }

    @Test
    public void unfulfilledHabit() throws InvalidHabitException {
        MainActivity ma = Robolectric.setupActivity(MainActivity.class);
        InputOutputGSON ioGson = new InputOutputGSON(ma);

        ArrayList<String> freq = new ArrayList<String>();
        freq.add("Monday");

        Calendar date = Calendar.getInstance();
        date.set(2017,Calendar.NOVEMBER,1);
        Habit habit = new Habit("h", date, freq);
        ioGson.saveInFile(habit);
        Assert.assertTrue(ma.fileList().length==1);


        Assert.assertEquals(0, ma.avgFulfillments(habit),0.001);
    }

    @Test
    public void oneEmptyDay() throws InvalidHabitException {
        MainActivity ma = Robolectric.setupActivity(MainActivity.class);
        InputOutputGSON ioGson = new InputOutputGSON(ma);

        ArrayList<String> freq = new ArrayList<String>();
        freq.add("Monday");

        Calendar date = Calendar.getInstance();
        date.set(2017,Calendar.NOVEMBER,1);
        Habit habit = new Habit("h", date, freq);
        ioGson.saveInFile(habit);
        Assert.assertTrue(ma.fileList().length==1);

        ArrayList<Calendar> fulfilDates = new ArrayList<>();

        Calendar date1 = Calendar.getInstance();
        date1.set(2017,Calendar.NOVEMBER,6);
        for(int i=0;i<1;i++){
            fulfilDates.add(date1);
        }

        Calendar date2 = Calendar.getInstance();
        date2.set(2017,Calendar.NOVEMBER,13);
        for(int i=0;i<1;i++){
            fulfilDates.add(date2);
        }


        Calendar date3 = Calendar.getInstance();
        date3.set(2017,Calendar.NOVEMBER,20);
        for(int i=0; i < 1; i++){
            fulfilDates.add(date3);
        }


        habit.setFulfilDate(fulfilDates);

        Assert.assertEquals(0.75, ma.avgFulfillments(habit),0.001);
    }

}
