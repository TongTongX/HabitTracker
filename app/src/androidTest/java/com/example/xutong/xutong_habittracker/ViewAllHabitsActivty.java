package com.example.xutong.xutong_habittracker;


import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Solo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class ViewAllHabitsActivty extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public ViewAllHabitsActivty() {
        super(com.example.xutong.xutong_habittracker.AllHabitsActivity.class);
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

    public void testAmountHabits() throws InvalidHabitException {
        solo.assertCurrentActivity("wrong",AllHabitsActivity.class);
        Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
        InputOutputGSON io = new InputOutputGSON(context);

        ArrayList<String> freq = new ArrayList<String>();
        freq.add("Thursday");
        Habit h = new Habit("hName", Calendar.getInstance(),freq);
        io.saveInFile(h);

        String[] files = context.fileList();
//        assertEquals(1,files.length);

        ArrayList<ListView> lists = solo.getCurrentViews(ListView.class);
        assertEquals(1, lists.size());
        ListView l = lists.get(0);
        ArrayList<TextView> habitItems = solo.getCurrentViews(TextView.class,l);
        assertEquals(files.length,habitItems.size());

    }

    public void testdeleteHabit() throws InvalidHabitException {

        Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
        InputOutputGSON io = new InputOutputGSON(context);

        ArrayList<String> freq = new ArrayList<String>();
        freq.add("Thursday");
        Habit h = new Habit("hName", Calendar.getInstance(),freq);
        io.saveInFile(h);

        solo.assertCurrentActivity("wrong",AllHabitsActivity.class);

        String[] files = context.fileList();
        int beforeDelete = files.length;

        ArrayList<ListView> lists = solo.getCurrentViews(ListView.class);
        assertEquals(1, lists.size());

        solo.clickInList(0);
        solo.waitForDialogToOpen();
        solo.clickOnButton("Delete Habit");

        Context context2 = this.getInstrumentation().getTargetContext().getApplicationContext();
        String[] files2 = context2.fileList();
        assertEquals(beforeDelete-1,files2.length);

    }

}//end
