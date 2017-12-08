package com.example.xutong.xutong_habittracker;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Solo;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivityUITest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public MainActivityUITest() {
        super(com.example.xutong.xutong_habittracker.MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        Log.d("TAG1", "setUp()");
        solo = new Solo(getInstrumentation(), getActivity());

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void testfulfillHabit() throws InvalidHabitException {
        Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
        InputOutputGSON io = new InputOutputGSON(context);

        ArrayList<String> freq = new ArrayList<String>();
        freq.add("Thursday");
        Habit h = new Habit("hName", Calendar.getInstance(),freq);
        io.saveInFile(h);

        String[] files = context.fileList();

//        assertEquals(1,files.length);

        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        ArrayList<ListView> lists = solo.getCurrentViews(ListView.class);
        assertEquals(2,lists.size());
        ArrayList<TextView> fulfilledItems = solo.getCurrentViews(TextView.class,lists.get(0));
        ArrayList<TextView> unfulfilledItems = solo.getCurrentViews(TextView.class,lists.get(1));
        assertEquals(0,fulfilledItems.size());
        assertEquals(files.length,unfulfilledItems.size());


    }

    public void testClickAddHabitButton(){
        solo.clickOnMenuItem("Add A Habit");
        solo.assertCurrentActivity("Wrong",AddEditHabitActivity.class);
    }

    public void testClickViewAllHabitsButton(){
        solo.clickOnMenuItem("View All Habits");
        solo.assertCurrentActivity("Wrong",AllHabitsActivity.class);
    }

    public void testoneMonthHabit() throws InvalidHabitException {
        Context c = this.getInstrumentation().getTargetContext().getApplicationContext();
        MainActivity ma = ((MainActivity) solo.getCurrentActivity());
        InputOutputGSON ioGson = new InputOutputGSON(c);

        ArrayList<String> freq = new ArrayList<String>();
        freq.add("Monday");

        Calendar date = Calendar.getInstance();
        date.set(2017,Calendar.NOVEMBER,1);
        Habit habit = new Habit("h", date, freq);
        ioGson.saveInFile(habit);
//        assertTrue(c.fileList().length==1);

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

        assertEquals(5.25, ma.avgFulfillments(habit),0.001);
    }

    public void testunfulfilledHabit() throws InvalidHabitException {
        Context c = this.getInstrumentation().getTargetContext().getApplicationContext();
        MainActivity ma = ((MainActivity) solo.getCurrentActivity());
        InputOutputGSON ioGson = new InputOutputGSON(c);

        ArrayList<String> freq = new ArrayList<String>();
        freq.add("Monday");

        Calendar date = Calendar.getInstance();
        date.set(2017,Calendar.NOVEMBER,1);
        Habit habit = new Habit("h", date, freq);
        ioGson.saveInFile(habit);
       assertTrue(ma.fileList().length==1);


        assertEquals(0, ma.avgFulfillments(habit),0.001);
    }

    public void test() throws InvalidHabitException {
        Context c = this.getInstrumentation().getTargetContext().getApplicationContext();
        MainActivity ma = ((MainActivity) solo.getCurrentActivity());
        InputOutputGSON ioGson = new InputOutputGSON(c);


        ArrayList<String> freq = new ArrayList<String>();
        freq.add("Monday");

        Calendar date = Calendar.getInstance();
        date.set(2017,Calendar.NOVEMBER,1);
        Habit habit = new Habit("h", date, freq);
        ioGson.saveInFile(habit);
        assertTrue(ma.fileList().length==1);

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

        assertEquals(0.75, ma.avgFulfillments(habit),0.001);
    }




}//end
