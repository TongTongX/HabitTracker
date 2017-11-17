package com.example.xutong.xutong_habittracker;

import android.app.Application;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.util.Log;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class AddEditHabitActivityTest extends ActivityInstrumentationTestCase2<AddEditHabitActivity> {

    private Solo solo;

    public AddEditHabitActivityTest() {
        super(com.example.xutong.xutong_habittracker.AddEditHabitActivity.class);
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

    /**
     * Test add a valid habit.
     */
    public void testAddValidHabit() {
        solo.assertCurrentActivity("Wrong Activity", AddEditHabitActivity.class);
        solo.enterText((EditText) solo.getView(R.id.edit_habit_name), "test01");
        solo.enterText((EditText) solo.getView(R.id.edit_date), "2017-11-17");
        solo.enterText((EditText) solo.getView(R.id.edit_days), "Friday");
        solo.clickOnButton("Add Habit");
    }

    /**
     * Test add a habit with an empty name.
     */
    public void testAddHabitEmptyName() {
        solo.assertCurrentActivity("Wrong Activity", AddEditHabitActivity.class);
        solo.enterText((EditText) solo.getView(R.id.edit_habit_name), " ");
        solo.enterText((EditText) solo.getView(R.id.edit_date), "2017-11-17");
        solo.enterText((EditText) solo.getView(R.id.edit_days), "Friday");
        solo.clickOnButton("Add Habit");
//        assertTrue("Cannot find toast", solo.waitForText("Please enter a Habit name."));
    }

    /**
     * Test add a habit with an empty name.
     */
    public void testAddHabitEmptyOccurDays() {
        solo.assertCurrentActivity("Wrong Activity", AddEditHabitActivity.class);
        solo.enterText((EditText) solo.getView(R.id.edit_habit_name), "test02");
        solo.enterText((EditText) solo.getView(R.id.edit_date), "2017-11-17");
        solo.clickOnButton("Add Habit");
//        assertTrue("Cannot find toast", solo.searchText("specify day(s) of week"));
    }

    /**
     * Test add a habit with an empty name.
     */
    public void testAddHabitEmptyNameOccurDays() {
        solo.assertCurrentActivity("Wrong Activity", AddEditHabitActivity.class);
        solo.enterText((EditText) solo.getView(R.id.edit_habit_name), " ");
        solo.enterText((EditText) solo.getView(R.id.edit_date), "2017-11-17");
        solo.clickOnButton("Add Habit");
//        assertTrue("Cannot find toast", solo.searchText("enter a Habit name"));
    }
}