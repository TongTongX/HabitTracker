package com.example.xutong.xutong_habittracker;

import android.content.Context;
import android.content.pm.PackageManager;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

@RunWith(RobolectricTestRunner.class)
public class LoadingTest {
    InputOutputGSON ioGson;
    private String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testSaveInFile() throws InvalidHabitException, FileNotFoundException {
        // get context and make a new isntance of ioGson
        Context context = RuntimeEnvironment.application;
        InputOutputGSON ioGson = new InputOutputGSON(context);

        // setup a dummy habit
        ArrayList<String> occurDays = new ArrayList<>();
        occurDays.add(week[3]);
        Habit habit = new Habit("new habit", Calendar.getInstance(), occurDays);

        // assert 0 files
        Assert.assertEquals(0, context.fileList().length);

        // add dummy habit and assert 1 file
        ioGson.saveInFile(habit);
        Assert.assertEquals(1, context.fileList().length);
    }

    private Integer getFiles(Context context) {
        File filesDir = context.getFilesDir();
        System.out.println("Number of files: "+ Integer.toString(filesDir.listFiles().length));
        return filesDir.listFiles().length;
    }

}//end
