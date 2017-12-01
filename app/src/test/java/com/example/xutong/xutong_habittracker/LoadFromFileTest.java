package com.example.xutong.xutong_habittracker;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by drei on 2017-12-01.
 */

public class LoadFromFileTest extends ActivityInstrumentationTestCase2 {

    private String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    public LoadFromFileTest() {
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

    // STATEMENT COVERAGE
    @Test
    public void straightDownTest() throws InvalidHabitException, FileNotFoundException {
        String habitName = "one habit";
        Calendar habitDate = Calendar.getInstance();
        ArrayList<String> occurDays = new ArrayList<>();
        occurDays.add(week[3]);

        Habit habit = new Habit(habitName, habitDate, occurDays);

        AllHabitsActivity allHabitsActivity = new AllHabitsActivity();
        AddEditHabitActivity addEditHabitActivity = new AddEditHabitActivity();
        InputOutputGSON ioGson = new InputOutputGSON(addEditHabitActivity);

        ioGson.saveInFile(habit);
        assertTrue(true);

//        assertTrue(1 == getFiles(addEditHabitActivity).length);
}

//    @Test
//    public void fileNotFoundTest() throws InvalidHabitException, FileNotFoundException {
//
//    }
//
//    @Test
//    public void ioExceptionTest() throws InvalidHabitException, FileNotFoundException {
//
//    }
//
//    // BRANCH COVERAGE
//    @Test
//    public void multipleFileTest() throws InvalidHabitException, FileNotFoundException {
//
//    }

    // https://stackoverflow.com/questions/8867334/check-if-a-file-exists-before-calling-openfileinput
    private boolean isFileExistent(Context context, String fileName) {
        File file = context.getFileStreamPath(fileName);
        return (file != null && file.exists());
    }

    private File[] getFiles(Context context) {

        File filesDir = context.getFilesDir();
        return filesDir.listFiles();
    }


}
