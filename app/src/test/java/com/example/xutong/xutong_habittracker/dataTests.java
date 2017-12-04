package com.example.xutong.xutong_habittracker;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.maven.model.Build;
import org.apache.tools.ant.Main;
import org.apache.tools.ant.taskdefs.Input;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowHandler;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.ShadowListView;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.shadows.support.v4.Shadows;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.robolectric.Shadows.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants= BuildConfig.class,manifest = "app/src/main/AndroidManifest.xml")
public class dataTests {

    @Test
    public void testCancel(){
        AddEditHabitActivity aeh = Robolectric.setupActivity(AddEditHabitActivity.class);
        ShadowActivity shadowActivity = shadowOf(aeh);

        EditText hName = (EditText) aeh.findViewById(R.id.edit_habit_name);
        EditText hCreationDate = (EditText) aeh.findViewById(R.id.edit_date);
        EditText hOccurances = (EditText) aeh.findViewById(R.id.edit_days);
        Button addHabitButton = (Button) aeh.findViewById(R.id.add_habit_button);

        //(1) Null Habit Name; Null daysof Week, habitDate

        Assert.assertTrue(addHabitButton.performClick());
        ShadowHandler.idleMainLooper();
        Assert.assertEquals("Please specify day(s) of week it should occur on." ,ShadowToast.getTextOfLatestToast());

        //(3) invalid occurances
        hName.setText("name");

        Assert.assertTrue(addHabitButton.performClick());
        ShadowHandler.idleMainLooper();
        Assert.assertEquals("Please specify day(s) of week it should occur on." ,ShadowToast.getTextOfLatestToast());

        //(2) Null Habit Name; daysof Week, habitDate
        aeh.setOccurDays();
        hName.setText(null);
        Assert.assertTrue(addHabitButton.performClick());
        ShadowHandler.idleMainLooper();
        Assert.assertEquals("Please enter a Habit name." ,ShadowToast.getTextOfLatestToast());

        //invalid creation date -- unreachable (automatically created)

        //cancel button finishes this activity and brings user back to main activity
        Button cancel = (Button) aeh.findViewById(R.id.cancel_add_button);
        boolean clickedCancel = cancel.performClick();

        Assert.assertTrue(clickedCancel);
        Assert.assertTrue(shadowActivity.isFinishing());

       //finish() does not create another activity, the aehActivity was created ontop of MainActivity

    }


    /*
    @Test
    public void fulfillDateTestUI() throws InvalidHabitException {

        MainActivity ma = Robolectric.setupActivity(MainActivity.class);
        ma.onOptionsItemSelected(new RoboMenuItem(R.id.action_add_habit));

        ShadowActivity shadowActivity = shadowOf(ma);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);

//        System.out.println(shadowIntent.getIntentClass().getName().toString());

        Assert.assertEquals("com.example.xutong.xutong_habittracker.AddEditHabitActivity",shadowIntent.getIntentClass().getName().toString());

        AddEditHabitActivity aeh = Robolectric.buildActivity(AddEditHabitActivity.class).create().get();

        EditText hName = (EditText) aeh.findViewById(R.id.edit_habit_name);
        EditText hCreationDate = (EditText) aeh.findViewById(R.id.edit_date);
        EditText hOccurances = (EditText) aeh.findViewById(R.id.edit_days);
        Button addHabitButton = (Button) aeh.findViewById(R.id.add_habit_button);

        hName.setText("hName");
        aeh.setOccurDays();
        boolean clicked = addHabitButton.performClick();

        Assert.assertTrue(clicked);

        String[] files = aeh.fileList();
        Assert.assertTrue(files.length==1);
        String[] files2 = ma.fileList();
        Assert.assertTrue(files2.length==1);



//        InputOutputGSON io = new InputOutputGSON(ma);
//
//        ArrayList<String> freq = new ArrayList<String>();
//        freq.add("Monday");
//
//        Habit habit = new Habit("h", Calendar.getInstance(),freq);
//        io.saveInFile(habit);

//        ma.loadAllHabit();
//        ArrayList<Habit> currHabits = ma.getHabits();
//
//        Assert.assertFalse(currHabits.isEmpty());
//        Assert.assertTrue(currHabits.get(0).getHabitName()=="h");

//        ma.addHabitFulfilDate();

    }
    */

    @Test
    public void fulfillmentTest() throws InvalidHabitException {
        //(1) fulfill previously unfulfilled habit 1 time

        MainActivity ma = Robolectric.setupActivity(MainActivity.class);
        InputOutputGSON io = new InputOutputGSON(ma);

        ArrayList<String> freq = new ArrayList<String>();
        freq.add("Monday");

        Habit habit = new Habit("h", Calendar.getInstance(),freq);
        io.saveInFile(habit);

        Assert.assertTrue(ma.fileList().length==1);

        ma.loadAllHabit();
        ArrayList<Habit> currHabits = ma.getHabits();

        Assert.assertFalse(currHabits.isEmpty());
        Assert.assertEquals("h",currHabits.get(0).getHabitName().toString());
        Assert.assertEquals(0,currHabits.get(0).getFulfilDate().size());

        ma.addHabitFulfilDate("h"); //updates with time of method call via calendar class

        ArrayList<Habit> updated = io.loadFromAllFiles();
        ArrayList<Calendar> fDates = updated.get(0).getFulfilDate();
        Assert.assertEquals(1,fDates.size());

    }

    @Test
    public void deleteFulfillments() throws InvalidHabitException {
        MainActivity ma = Robolectric.setupActivity(MainActivity.class);
        InputOutputGSON io = new InputOutputGSON(ma);

        ArrayList<String> freq = new ArrayList<String>();
        freq.add("Monday");

        Habit habit = new Habit("h", Calendar.getInstance(),freq);
        io.saveInFile(habit);

        Assert.assertTrue(ma.fileList().length==1);

        ma.loadAllHabit();

        ma.addHabitFulfilDate("h"); //updates with time of method call via calendar class
        ma.addHabitFulfilDate("h");
        ma.addHabitFulfilDate("h");

        ArrayList<Habit> updated = io.loadFromAllFiles();
        ArrayList<Calendar> fDates = updated.get(0).getFulfilDate();
        Assert.assertEquals(3,fDates.size());

        AllHabitsActivity ah = Robolectric.buildActivity(AllHabitsActivity.class).create().start().get();
        ArrayList<Habit> currHabits = ah.getHabits();

        ArrayList<Calendar> fDates2 = currHabits.get(0).getFulfilDate();
        Assert.assertEquals(3,fDates2.size());

        List<Integer> toDelete = new ArrayList<Integer>();
        toDelete.add(0);

        ah.setfulfilForDeleting(toDelete);
        ah.deleteFulfilment(currHabits.get(0));

        InputOutputGSON io2 = new InputOutputGSON(ah);
        ArrayList<Habit> updatedHabits = io2.loadFromAllFiles();
        Assert.assertEquals(2,updatedHabits.get(0).getFulfilDate().size());

    }
    @Test
    public void deleteAllFulfillments() throws InvalidHabitException {
        MainActivity ma = Robolectric.setupActivity(MainActivity.class);
        InputOutputGSON io = new InputOutputGSON(ma);

        ArrayList<String> freq = new ArrayList<String>();
        freq.add("Monday");

        Habit habit = new Habit("h", Calendar.getInstance(),freq);
        io.saveInFile(habit);

        Assert.assertTrue(ma.fileList().length==1);

        ma.loadAllHabit();

        ma.addHabitFulfilDate("h"); //updates with time of method call via calendar class
        ma.addHabitFulfilDate("h");
        ma.addHabitFulfilDate("h");

        ArrayList<Habit> updated = io.loadFromAllFiles();
        ArrayList<Calendar> fDates = updated.get(0).getFulfilDate();
        Assert.assertEquals(3,fDates.size());

        AllHabitsActivity ah = Robolectric.buildActivity(AllHabitsActivity.class).create().start().get();
        ArrayList<Habit> currHabits = ah.getHabits();

        ArrayList<Calendar> fDates2 = currHabits.get(0).getFulfilDate();
        Assert.assertEquals(3,fDates2.size());

        List<Integer> toDelete = new ArrayList<Integer>();
        toDelete.add(0);
        toDelete.add(1);
        toDelete.add(2);

        Assert.assertTrue(toDelete.size()==fDates2.size());

        ah.setfulfilForDeleting(toDelete);
        ah.deleteFulfilment(currHabits.get(0));

        InputOutputGSON io2 = new InputOutputGSON(ah);
        ArrayList<Habit> updatedHabits = io2.loadFromAllFiles();
        Assert.assertEquals(0,updatedHabits.get(0).getFulfilDate().size());

    }

}//end

