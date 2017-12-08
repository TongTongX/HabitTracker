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



}//end
