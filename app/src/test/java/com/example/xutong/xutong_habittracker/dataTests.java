package com.example.xutong.xutong_habittracker;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

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
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.support.v4.Shadows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static org.robolectric.Shadows.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants= BuildConfig.class,manifest = "src/main/AndroidManifest.xml")
public class dataTests {

    @Test
    public void fulfillDateTest() throws InvalidHabitException {

        MainActivity ma = Robolectric.setupActivity(MainActivity.class);
        ma.onOptionsItemSelected(new RoboMenuItem(R.id.action_add_habit));

        ShadowActivity shadowActivity = shadowOf(ma);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);

        

        Assert.assertTrue(shadowIntent.getClass().getName().toString()=="AddEditHabitActivity");
        Assert.assertTrue(false);
//
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
