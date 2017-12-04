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
public class LoadFromFileTest {
    private String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testMultipleFiles() throws InvalidHabitException, FileNotFoundException {
        Context context = RuntimeEnvironment.application;
        InputOutputGSON ioGson = new InputOutputGSON(context);

        ArrayList<String> fileList = new ArrayList<>(3);
        fileList.add("name1");
        fileList.add("name2");
        fileList.add("name3");

        ArrayList<String> occurDays = new ArrayList<>();
        occurDays.add(week[4]);

        // assert 0 files
        Assert.assertEquals(0, getFiles(context));
        Assert.assertEquals(0, ioGson.loadFromAllFiles().size());

        for (int i = 0; i < fileList.size(); i++) {
            Calendar cal = Calendar.getInstance();

            cal.add(Calendar.DATE, -i);

            Habit habit = new Habit(fileList.get(i), cal, occurDays);
            ioGson.saveInFile(habit);
            System.out.println("added habit");
        }

        System.out.println(context.fileList()[0]);
        System.out.println(context.fileList()[1]);
        System.out.println(context.fileList()[2]);
        ArrayList<Habit> habits = ioGson.loadFromAllFiles();
        Assert.assertEquals(3, habits.size());

        for (int i = 0; i < fileList.size(); i++) {
            fileList.contains(habits.get(i).toString());
        }
    }

    private int getFiles(Context context) {
        return context.fileList().length;
    }
}