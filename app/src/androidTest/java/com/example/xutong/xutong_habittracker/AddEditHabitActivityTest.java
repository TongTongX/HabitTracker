package com.example.xutong.xutong_habittracker;

import android.app.Instrumentation;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import com.robotium.solo.Solo;

import java.io.File;
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
    public void testAddValidHabit() {
        solo.assertCurrentActivity("Wrong Activity", AddEditHabitActivity.class);
        solo.enterText((EditText) solo.getView(R.id.edit_habit_name), "test01");
        solo.enterText((EditText) solo.getView(R.id.edit_date), "2017-11-17");
        solo.enterText((EditText) solo.getView(R.id.edit_days), "Friday");
//        solo.clickOnButton("Add Habit");
        solo.clickOnView(solo.getView(R.id.add_habit_button));
        solo.waitForActivity(MainActivity.class);
//        solo.assertCurrentActivity("back to main",MainActivity.class);
        Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
        InputOutputGSON io = new InputOutputGSON(c);
        ArrayList<Habit> h = io.loadFromAllFiles();
        assertEquals("test01",h.get(0).getHabitName().toString());
        fail();
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
