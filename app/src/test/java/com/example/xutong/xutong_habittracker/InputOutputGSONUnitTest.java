package com.example.xutong.xutong_habittracker;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import static junit.framework.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants= BuildConfig.class,manifest = "app/src/main/AndroidManifest.xml")
public class InputOutputGSONUnitTest {

    private String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    @Test
    public void testSaveInFile() throws InvalidHabitException, FileNotFoundException{
        // setup a dummy habit
        String habitName = "test001";
        Calendar habitDate = Calendar.getInstance();
        ArrayList<String> occurDays = new ArrayList<>();
        occurDays.add(week[0]);
        Habit habit = new Habit(habitName, habitDate, occurDays);

        // get context and make a new isntance of ioGson
        Context context = RuntimeEnvironment.application;
        InputOutputGSON ioGson = new InputOutputGSON(context);

        // assert 0 files
        Assert.assertEquals(0, context.fileList().length);

        // add dummy habit and assert 1 file
        ioGson.saveInFile(habit);
        Assert.assertEquals(1, context.fileList().length);
    }

    @Test
    public void testDeleteFile() throws InvalidHabitException, FileNotFoundException{
        // setup a dummy habit
        String habitName = "test002";
        Calendar habitDate = Calendar.getInstance();
        ArrayList<String> occurDays = new ArrayList<>();
        occurDays.add(week[0]);
        Habit habit = new Habit(habitName, habitDate, occurDays);

        // get context and make a new instance of InputOutputGSON
        Context context = RuntimeEnvironment.application;
        InputOutputGSON ioGson = new InputOutputGSON(context);

        // assert 0 file
        Assert.assertEquals(0, context.fileList().length);

        // add dummy habit and assert 1 file
        ioGson.saveInFile(habit);
        Assert.assertEquals(1, context.fileList().length);

        // delete dummy habit and assert 0 file
        ioGson.deleteFile(habit);
        Assert.assertEquals(0, context.fileList().length);
    }

    @Test
    public void testLoadNonexistentFile() throws InvalidHabitException, FileNotFoundException{
        AllHabitsActivity allHabitsActivity = new AllHabitsActivity();
        InputOutputGSON ioGson = new InputOutputGSON(allHabitsActivity);

        ArrayList<Habit> habitList = ioGson.loadFromAllFiles();
        assertEquals(habitList.size(), 0);
    }

    @Test
    public void testLoadExistentFile() throws InvalidHabitException, FileNotFoundException{
        String habitName = "test003";
        Calendar habitDate = Calendar.getInstance();
        ArrayList<String> occurDays = new ArrayList<>();
        occurDays.add(week[0]);
        Habit habit = new Habit(habitName, habitDate, occurDays);

        AllHabitsActivity allHabitsActivity = new AllHabitsActivity();
        InputOutputGSON ioGson = new InputOutputGSON(allHabitsActivity);

        ioGson.saveInFile(habit);
        assertTrue(isFileExistent(allHabitsActivity, ioGson.jsonFileName(habit)));

        ArrayList<Habit> habitList = ioGson.loadFromAllFiles();
        assertEquals(habitList.size(), 1);
        assertEquals(habitList.get(0), habit);
    }

    // https://stackoverflow.com/questions/8867334/check-if-a-file-exists-before-calling-openfileinput
    private boolean isFileExistent(Context context, String fileName) {
        File file = context.getFileStreamPath(fileName);
        return (file != null && file.exists());
    }
}