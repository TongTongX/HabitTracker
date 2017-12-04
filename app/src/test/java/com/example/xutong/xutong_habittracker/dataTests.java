package com.example.xutong.xutong_habittracker;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

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
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowHandler;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.shadows.support.v4.Shadows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static org.robolectric.Shadows.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants= BuildConfig.class,manifest = "src/main/AndroidManifest.xml")
public class dataTests {

    @Test
    public void testCancel(){
        AddEditHabitActivity aeh = Robolectric.setupActivity(AddEditHabitActivity.class);

        EditText hName = (EditText) aeh.findViewById(R.id.edit_habit_name);
        EditText hCreationDate = (EditText) aeh.findViewById(R.id.edit_date);
        EditText hOccurances = (EditText) aeh.findViewById(R.id.edit_days);
        Button addHabitButton = (Button) aeh.findViewById(R.id.add_habit_button);

        hName.setText("hName");
        hCreationDate.setText("2017-12-1");
        aeh.setOccurDays();

//        if (occurClick){
//            AlertDialog d = (AlertDialog)
//        }
//
//        AlertDialog alert =
//                ShadowAlertDialog.getLatestAlertDialog();
//        ShadowAlertDialog sAlert = shadowOf(alert);
//        assertThat(sAlert.getTitle().toString(),
//                equalTo(activity.getString(R.string.all_fields_required_)));

        boolean clicked = addHabitButton.performClick();

        Assert.assertTrue(clicked);
        ShadowHandler.idleMainLooper();
        Assert.assertEquals("Please enter a Habit name." ,ShadowToast.getTextOfLatestToast());

        Button cancel = (Button) aeh.findViewById(R.id.cancel_add_button);
        boolean clickedCancel = cancel.performClick();

        Assert.assertTrue(clickedCancel);

        Intent startedIntent = shadowOf(aeh).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        Assert.assertEquals(MainActivity.class, shadowIntent.getIntentClass());


    }


    @Test
    public void fulfillDateTest() throws InvalidHabitException {

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
        hCreationDate.setText("2017-12-1");
//        hOccurances.setText("Monday");
        aeh.setOccurDays();
        boolean clicked = addHabitButton.performClick();

        Assert.assertTrue(clicked); //valid input

        String[] files = aeh.fileList();
        System.out.println(files.length);
        String[] files2 = ma.fileList();
        System.out.println(files2.length);

        Assert.fail();
//        Intent startedIntent2 = shadowOf(aeh).getNextStartedActivity();
//        ShadowIntent shadowIntent2 = shadowOf(startedIntent);
//        Assert.assertEquals(MainActivity.class, shadowIntent2.getIntentClass());




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

}//end
