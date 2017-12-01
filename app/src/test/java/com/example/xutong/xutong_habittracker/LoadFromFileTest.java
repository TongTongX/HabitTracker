package com.example.xutong.xutong_habittracker;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

public class LoadFromFileTest extends ActivityInstrumentationTestCase2 {

    private String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    public LoadFromFileTest(){
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
    public void testSaveInFile() throws InvalidHabitException, FileNotFoundException {
        String habitName = "new habit";
        Calendar habitDate = Calendar.getInstance();
        ArrayList<String> occurDays = new ArrayList<>();
        occurDays.add(week[3]);
        Habit habit = new Habit(habitName, habitDate, occurDays);

        AllHabitsActivity allHabitsActivity = new AllHabitsActivity();
        InputOutputGSON ioGson = new InputOutputGSON(allHabitsActivity);
        ioGson.saveInFile(habit);
        System.out.println("Number of files: ");
        assertTrue(1 == getFiles(allHabitsActivity));
    }

//    @Test
//    public void testDeleteFile() throws InvalidHabitException, FileNotFoundException{
//        String habitName = "test002";
//        Calendar habitDate = Calendar.getInstance();
//        ArrayList<String> occurDays = new ArrayList<>();
//        occurDays.add(week[0]);
//        Habit habit = new Habit(habitName, habitDate, occurDays);
//
//        AllHabitsActivity allHabitsActivity = new AllHabitsActivity();
//        InputOutputGSON ioGson = new InputOutputGSON(allHabitsActivity);
//        ioGson.saveInFile(habit);
//
//        assertTrue(isFileExistent(allHabitsActivity, ioGson.jsonFileName(habit)));
//        ioGson.deleteFile(habit);
//        assertFalse(isFileExistent(allHabitsActivity, ioGson.jsonFileName(habit)));
//    }
//
//    @Test
//    public void testLoadNonexistentFile() throws InvalidHabitException, FileNotFoundException{
//        AllHabitsActivity allHabitsActivity = new AllHabitsActivity();
//        InputOutputGSON ioGson = new InputOutputGSON(allHabitsActivity);
//
//        ArrayList<Habit> habitList = ioGson.loadFromAllFiles();
//        assertEquals(habitList.size(), 0);
//    }
//
//    @Test
//    public void testLoadExistentFile() throws InvalidHabitException, FileNotFoundException{
//        String habitName = "test003";
//        Calendar habitDate = Calendar.getInstance();
//        ArrayList<String> occurDays = new ArrayList<>();
//        occurDays.add(week[0]);
//        Habit habit = new Habit(habitName, habitDate, occurDays);
//
//        AllHabitsActivity allHabitsActivity = new AllHabitsActivity();
//        InputOutputGSON ioGson = new InputOutputGSON(allHabitsActivity);
//
//        ioGson.saveInFile(habit);
//        assertTrue(isFileExistent(allHabitsActivity, ioGson.jsonFileName(habit)));
//
//        ArrayList<Habit> habitList = ioGson.loadFromAllFiles();
//        assertEquals(habitList.size(), 1);
//        assertEquals(habitList.get(0), habit);
//    }

    // https://stackoverflow.com/questions/8867334/check-if-a-file-exists-before-calling-openfileinput
//    private boolean isFileExistent(Context context, String fileName) {
//        File file = context.getFileStreamPath(fileName);
//        return (file != null && file.exists());
//    }

    private Integer getFiles(Context context) {
        File filesDir = context.getFilesDir();
        System.out.println("Number of files: "+ Integer.toString(filesDir.listFiles().length));
        return filesDir.listFiles().length;
    }
}