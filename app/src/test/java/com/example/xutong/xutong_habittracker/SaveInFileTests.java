package com.example.xutong.xutong_habittracker;

import android.content.Context;

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
public class SaveInFileTests {
    private String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void saveInFileTest() throws InvalidHabitException, FileNotFoundException {
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

        ioGson.saveInFile(habit);

        // assert file created and matches
        Assert.assertEquals(1, getFiles(context));
        Assert.assertEquals(1, ioGson.loadFromAllFiles().size());
        Assert.assertEquals(ioGson.jsonFileName(habit), context.fileList()[0].toString());
    }

    private int getFiles(Context context) {
        return context.fileList().length;
    }
}