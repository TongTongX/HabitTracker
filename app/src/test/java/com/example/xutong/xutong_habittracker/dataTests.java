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

    @Test
    public void habitCreationAndFrequencyDates() throws InvalidHabitException {
        //(1)future creation date - should not show up on main screen
        MainActivity ma = Robolectric.setupActivity(MainActivity.class);
        InputOutputGSON io = new InputOutputGSON(ma);

        ArrayList<String> freq = new ArrayList<String>();
        freq.add("Monday");

        Calendar c = Calendar.getInstance();
        c.set(2018,1,29);

        Habit habit = new Habit("h",c, freq);
        io.saveInFile(habit);

        ma.loadAllHabit();
        ArrayList<Habit> allH = ma.getHabits();
//        System.out.println(allH.get(0).getHabitName());
//        System.out.println(allH.get(0).getHabitDate().getTime().toString());
//        System.out.println(allH.get(0).getOccurDays().get(0).toString());

        Assert.assertTrue(allH.size()==1);
        Assert.assertEquals("h",allH.get(0).getHabitName());
        Assert.assertTrue(allH.get(0).getHabitDate().after(Calendar.getInstance()));
        Assert.assertEquals("Monday",allH.get(0).getOccurDays().get(0).toString());

        ma.addToHabits(habit,Calendar.getInstance());

        ArrayList<Habit> fH = ma.getFulfilledHabits();
        ArrayList<Habit> ufH = ma.getUnfulfilledHabits();

        Assert.assertTrue(allH.size()==1);
        Assert.assertEquals("h",allH.get(0).getHabitName());
        Assert.assertTrue(allH.get(0).getHabitDate().after(Calendar.getInstance()));
        Assert.assertEquals("Monday",allH.get(0).getOccurDays().get(0).toString());

        Assert.assertTrue(fH.size()==0);
        Assert.assertTrue(ufH.size()==0);


    }

    @Test
    public void confirmTodayUnfulfilled() throws InvalidHabitException {
        String[] daysOfWeek = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        MainActivity ma = Robolectric.setupActivity(MainActivity.class);
        InputOutputGSON io = new InputOutputGSON(ma);


        Calendar c = Calendar.getInstance();
        ArrayList<String> freq = new ArrayList<String>();
        int weekday = c.get(Calendar.DAY_OF_WEEK)-1;
       // System.out.println(weekday);
       // System.out.println(daysOfWeek[weekday].toString());
        freq.add(daysOfWeek[weekday].toString());

        Habit habit = new Habit("h",c, freq);
        io.saveInFile(habit);

        ma.loadAllHabit(); //calls addtoHabits();
        ArrayList<Habit> allH = ma.getHabits();

      //  ma.addToHabits(habit,c);

        ArrayList<Habit> fH = ma.getFulfilledHabits();
        ArrayList<Habit> ufH = ma.getUnfulfilledHabits();

        Assert.assertTrue(fH.size()==0);
        Assert.assertTrue(ufH.size()==1 );

        //(2) move from unfullfilled to fulfilled section after fullfilment

        ma.addHabitFulfilDate("h"); //updates with time of method call via calendar class

        Assert.assertTrue(fH.size()==1);
        Assert.assertTrue(ufH.size()==0 );

    }

    @Test
    public void confirmPastCreationDate() throws InvalidHabitException {
        String[] daysOfWeek = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        MainActivity ma = Robolectric.setupActivity(MainActivity.class);
        InputOutputGSON io = new InputOutputGSON(ma);

        Calendar c = Calendar.getInstance();
        ArrayList<String> freq = new ArrayList<String>();
        int weekday = c.get(Calendar.DAY_OF_WEEK)-1;
         System.out.println(weekday);
         System.out.println(daysOfWeek[weekday].toString());
        freq.add(daysOfWeek[weekday].toString());

        Calendar cPast = Calendar.getInstance();
        cPast.set(2017,1,12);
        Habit habit = new Habit("h",cPast, freq);
        Assert.assertTrue(habit.getHabitDate().before(c));
        io.saveInFile(habit);

        ma.loadAllHabit();
        ArrayList<Habit> allH = ma.getHabits();
        System.out.println("allh "+allH.size());

        ArrayList<Habit> fH = ma.getFulfilledHabits();
        ArrayList<Habit> ufH = ma.getUnfulfilledHabits();
        System.out.println("ufh " + ufH.size());
        System.out.println("fh " + fH.size());

//        ma.addToHabits(allH.get(0),Calendar.getInstance());
//        System.out.println("AFter adding to habits: "+daysOfWeek[allH.get(0).getHabitDate().get(Calendar.DAY_OF_WEEK)]);
//        Assert.assertEquals(daysOfWeek[weekday].toString(),daysOfWeek[allH.get(0).getHabitDate().get(Calendar.DAY_OF_WEEK)].toString());

//        fH = ma.getFulfilledHabits();
//        ufH = ma.getUnfulfilledHabits();
//
//        System.out.println("ufh " + ufH.size());
//        System.out.println("fh " + fH.size());

//        for(Habit h:ufH){
//            System.out.println(h.getHabitName().toString());
//        }

        Assert.assertTrue(fH.size()==0);
        Assert.assertTrue(ufH.size()==1);

        ma.addHabitFulfilDate(allH.get(0).getHabitName().toString());

        Assert.assertTrue(fH.size()==1);
        Assert.assertTrue(ufH.size()==0 );

    }

    @Test
    public void doesNotOccurToday() throws InvalidHabitException {
        String[] daysOfWeek = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        MainActivity ma = Robolectric.setupActivity(MainActivity.class);
        InputOutputGSON io = new InputOutputGSON(ma);

        Calendar c = Calendar.getInstance();

        ArrayList<String> freq = new ArrayList<String>();
        int weekday = c.get(Calendar.DAY_OF_WEEK); //need to subtract 1 for correct weekday so this will give wrong weekday
//        System.out.println(weekday);
//        System.out.println(daysOfWeek[weekday].toString());
        freq.add(daysOfWeek[weekday].toString());

        Calendar cPast = Calendar.getInstance();
        cPast.set(2017,1,12);

        Habit habitPast = new Habit("h",cPast, freq);
        Assert.assertTrue(habitPast.getHabitDate().before(c));
        io.saveInFile(habitPast);

        Habit habitToday = new Habit("h",c, freq);
        io.saveInFile(habitToday);

        ma.loadAllHabit();
        ArrayList<Habit> allH = ma.getHabits();
        Assert.assertTrue(allH.size()==2);

        for(Habit h : allH){
            ma.addToHabits(h,c);
        }

        ArrayList<Habit> fH = ma.getFulfilledHabits();
        ArrayList<Habit> ufH = ma.getUnfulfilledHabits();

        Assert.assertTrue(fH.size()==0);
        Assert.assertTrue(ufH.size()==0);


    }

}//end

