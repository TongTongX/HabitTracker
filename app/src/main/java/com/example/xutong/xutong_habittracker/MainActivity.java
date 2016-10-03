package com.example.xutong.xutong_habittracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Habit> habits = new ArrayList<>();
    private ArrayList<Habit> fulfilledHabits = new ArrayList<>();
    private ArrayList<Habit> unfulfilledHabits = new ArrayList<>();
    private ArrayAdapter<Habit> fulfilledAdapter;
    private ArrayAdapter<Habit> unfulfilledAdapter;
    private ListView fulfilledListView;
    private ListView unfulfilledListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fulfilledListView = (ListView) findViewById(R.id.fulfilled_habits_list);
        unfulfilledListView = (ListView) findViewById(R.id.unfulfilled_habits_list);

        fulfilledListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String habitName = parent.getItemAtPosition(position).toString();
                openAddFulfilDialog(habitName);
            }
        });

        unfulfilledListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String v = parent.getItemAtPosition(position).toString();
                openAddFulfilDialog(v);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadAllHabit();
        fulfilledAdapter = new ArrayAdapter<>(this, R.layout.all_habits_list_item, fulfilledHabits);
        unfulfilledAdapter = new ArrayAdapter<>(this, R.layout.all_habits_list_item, unfulfilledHabits);
        fulfilledListView.setAdapter(fulfilledAdapter);
        unfulfilledListView.setAdapter(unfulfilledAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        habits.clear();
        fulfilledHabits.clear();
        unfulfilledHabits.clear();
        loadAllHabit();

        fulfilledAdapter.notifyDataSetChanged();
        unfulfilledAdapter.notifyDataSetChanged();
    }
    

    // Code taken from https://developer.android.com/guide/topics/ui/menus.html
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_view_habit_menu, menu);
        return true;
    }

    // code taken from https://developer.android.com/training/basics/firstapp/starting-activity.html#BuildIntent
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        // Handle item selection
        switch (itemId) {
            case R.id.action_view_habits:
                Intent intentView = new Intent(this, AllHabitsActivity.class);
                startActivity(intentView);
                break;
            case R.id.action_add_habit:
                Intent intentAdd = new Intent(this, AddEditHabitActivity.class);
                startActivity(intentAdd);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadAllHabit() {
        InputOutputGSON IOGson = new InputOutputGSON(this);
        habits = IOGson.loadFromAllFiles();
        // check if there is any file exist
        if (!habits.isEmpty()) {
            // consider the habit which is finished within today
            // as recent completed
            Calendar today = Calendar.getInstance();
            for (Habit habit : habits) {
                // if the habit should be done today
                addTohabits(habit, today);
            }
        }
    }

    private void addTohabits(Habit habit, Calendar today) {
        boolean notPast = false;
        boolean isToday = false;
        boolean shouldOccur = false;
        Calendar habitDate = habit.getHabitDate();
        // https://www.tutorialspoint.com/java/util/calendar_getdisplaynames.htm
        Locale locale = Locale.CANADA;
        String todayOfWeek = today.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MMM dd");

        if (today.after(habitDate)) {
            notPast = true;
        }
        if (dateFormat.format(today.getTime()).equals(habitDate)) {
            isToday = true;
        }
        if (habit.getOccurDays().contains(todayOfWeek)) {
            shouldOccur = true;
        }

        if ((notPast || isToday) && shouldOccur) {
            if (habit.getFulfilDate().isEmpty()) {
                unfulfilledHabits.add(habit);
            } else {
                for (Calendar date : habit.getFulfilDate()) {
                    // if the habit has been fullfiled today
                    if (dateFormat.format(date.getTime()).equals(dateFormat.format(today.getTime()))) {
                        fulfilledHabits.add(habit);
                        break;
                    }
                }
            }
        }
    }

    // https://developer.android.com/guide/topics/ui/dialogs.html
    private void openAddFulfilDialog(final String habitName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("This Habit Is Fulfilled.")
        // Add the buttons
                // User clicked OK button
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addHabitFulfilDate(habitName);
                    }
                })
                // User cancelled the dialog
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }

                });
        // Create & Show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addHabitFulfilDate (String habitName) {

        Calendar today = Calendar.getInstance();

        for (Habit habit : habits) {
            // select habit based on its name
            if (habit.getHabitName().equals(habitName)) {

                habit.addFulfilDate(today);

                InputOutputGSON file = new InputOutputGSON(this);
                file.saveInFile(habit);

                // Clean
                habits.clear();
                fulfilledHabits.clear();
                unfulfilledHabits.clear();

                // Load
                loadAllHabit();

                // Adapt
                fulfilledAdapter.notifyDataSetChanged();
                unfulfilledAdapter.notifyDataSetChanged();

                break;
            }
        }
    }



}
