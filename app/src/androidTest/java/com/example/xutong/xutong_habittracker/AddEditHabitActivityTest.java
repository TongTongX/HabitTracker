package com.example.xutong.xutong_habittracker;

import android.app.Instrumentation;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import com.robotium.solo.Solo;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Kelly on 2017-12-06.
 */
public class AddEditHabitActivityTest extends ActivityInstrumentationTestCase2<AddEditHabitActivity> {

    private Solo solo;
    AddEditHabitActivity aeh;
    Instrumentation i;
    Context c;
    public AddEditHabitActivityTest() {
        super(com.example.xutong.xutong_habittracker.AddEditHabitActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        Log.d("TAG1", "setUp()");
        aeh = getActivity();
        i=getInstrumentation();
        solo = new Solo(i,aeh);
        c = i.getTargetContext();

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    /**
     * Test add a valid habit.
     */
    public void testAddValidHabitUI() {
        solo.assertCurrentActivity("Wrong Activity", AddEditHabitActivity.class);
        solo.enterText((EditText) solo.getView(R.id.edit_habit_name), "test01");
        solo.enterText((EditText) solo.getView(R.id.edit_date), "2017-11-17");
        solo.enterText((EditText) solo.getView(R.id.edit_days), "Friday");
//        solo.clickOnButton("Add Habit");
        solo.clickOnView(solo.getView(R.id.add_habit_button));
        solo.waitForActivity(MainActivity.class);
        Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
        String[] files = context.fileList();
        assertEquals(1,files.length);
//        assertEquals("test01",files[0].toString());
    }

    /**
     * Test add a habit with an empty name.
     */
    public void testAddHabitEmptyNameUI() {
        solo.assertCurrentActivity("Wrong Activity", AddEditHabitActivity.class);
        solo.enterText((EditText) solo.getView(R.id.edit_habit_name), " ");
        solo.enterText((EditText) solo.getView(R.id.edit_date), "2017-11-17");
        solo.enterText((EditText) solo.getView(R.id.edit_days), "Friday");
        solo.clickOnView(solo.getView(R.id.add_habit_button));
        solo.waitForText("Please enter a Habit name.");
//        assertTrue("Cannot find toast", solo.searchText("Please enter a Habit name."));
    }

    /**
     * Test add a habit with empty occur days.
     */
    public void testAddHabitEmptyOccurDaysUI() {
        solo.assertCurrentActivity("Wrong Activity", AddEditHabitActivity.class);
        solo.enterText((EditText) solo.getView(R.id.edit_habit_name), "test02");
//        solo.enterText((EditText) solo.getView(R.id.edit_date), "2017-11-17");
//        solo.clickOnView(solo.getView(R.id.add_habit_button));
//        Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
//        solo.clickOnButton(context.getResources().getString(R.string.add_habit_button));
        solo.clickOnButton(0);
        assertTrue("Cannot find toast", solo.searchText("Please specify day(s) of week it should occur on."));
    }


}

//    Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
//    InputOutputGSON io = new InputOutputGSON( solo.getCurrentActivity());
//    ArrayList<Habit> h = io.loadFromAllFiles();
//    ArrayList<Habit> h =((MainActivity) solo.getCurrentActivity()).getHabits();
//    assertEquals("test01",h.get(0).getHabitName().toString());