package com.example.xutong.xutong_habittracker;

import android.content.Context;
import android.content.pm.PackageManager;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;


@RunWith(RobolectricTestRunner.class)
public class LoadFromFileTest {
    InputOutputGSON ioGson;
    private String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testSaveInFile() throws InvalidHabitException, FileNotFoundException {
        // get context and make a new instance of ioGson
        Context context = RuntimeEnvironment.application;
        InputOutputGSON ioGson = new InputOutputGSON(context);

        // setup a dummy habit
        String habitName = "new habit";
        ArrayList<String> occurDays = new ArrayList<>();
        occurDays.add(week[3]);
        Habit habit = new Habit(habitName, Calendar.getInstance(), occurDays);

        // assert 0 files
        Assert.assertEquals(0, getFiles(context));
        Assert.assertEquals(0, ioGson.loadFromAllFiles().size());

        // add dummy habit and assert 1 file
        ioGson.saveInFile(habit);
        Assert.assertEquals(1, getFiles(context));
        Assert.assertEquals(1, ioGson.loadFromAllFiles().size());

        // get files from ioGson and compare with expected.
        Habit returnHabit = ioGson.loadFromAllFiles().get(0);
        Assert.assertTrue(habitName.equals(returnHabit.toString()));
        System.out.println(returnHabit);
    }

    @Test
    public void testMultipleFiles() throws InvalidHabitException, FileNotFoundException{
        Context context = RuntimeEnvironment.application;
        InputOutputGSON ioGson = new InputOutputGSON(context);

        String[] fileList = {"name1", "name2", "name3"};
        ArrayList<String> occurDays = new ArrayList<>();
        occurDays.add(week[4]);

        // assert 0 files
        Assert.assertEquals(0, getFiles(context));
        Assert.assertEquals(0, ioGson.loadFromAllFiles().size());

        for (int i = 0; i < fileList.length; i++) {
            Calendar cal = Calendar.getInstance();

            cal.add(Calendar.DATE, -i);

            Habit habit = new Habit(fileList[i], cal, occurDays);
            ioGson.saveInFile(habit);
            System.out.println("added habit");
        }

        System.out.println(context.fileList()[0].toString());
        System.out.println(context.fileList()[1].toString());
        System.out.println(context.fileList()[2].toString());
        ArrayList<Habit> habits = ioGson.loadFromAllFiles();
        Assert.assertEquals(3, habits.size());

        for (int i = 0; i < habits.size(); i++) {
            Assert.assertEquals(fileList[i], habits.get(i).toString());
        }
    }

    private int getFiles(Context context) {
        return context.fileList().length;
    }
}