package com.example.xutong.xutong_habittracker;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Display the list of all added habits.
 */
public class AllHabitsActivity extends AppCompatActivity {
    private ArrayList<Habit> habits = new ArrayList<>();
    private ArrayList<Calendar> fulfilDate = new ArrayList<>();
    private ArrayAdapter<Habit> habitAdapter;
    private ListView allHabitsListView;
    private List<Integer> fulfilForDeleting = new ArrayList<>();

    /**
     * Override onCreate, onStart and onResume.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_habits);

        allHabitsListView = (ListView) findViewById(R.id.all_habits_list);

        allHabitsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String habitName = parent.getItemAtPosition(position).toString();
                // find the habit in habits with the same name as the clickable item
                for (Habit habitObj : habits) {
                    if (habitObj.getHabitName().equals(habitName)) {
                        // has not been fulfilled
                        if (habitObj.getFulfilDate().isEmpty()) {
                            openUnfulfilDialog(habitObj);
                        }
                        // has been fulfilled
                        else {
                            openFulfilDialog(habitObj);
                        }
                    }
                }
            }
        });
        InputOutputGSON IOGson = new InputOutputGSON(this);
        habits = IOGson.loadFromAllFiles();
    }

    @Override
    protected void onStart() {
        super.onStart();
        habitAdapter = new ArrayAdapter<>(this, R.layout.all_habits_list_item, habits);
        allHabitsListView.setAdapter(habitAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        InputOutputGSON IOGson = new InputOutputGSON(this);
        habits = IOGson.loadFromAllFiles();
        habitAdapter.clear();
        habitAdapter.addAll(habits);
        habitAdapter.notifyDataSetChanged();
    }

    /**
     * Open this dialog if the habit has been fulfilled.
     * Code taken from https://developer.android.com/guide/topics/ui/dialogs.html
     * @param habit
     */
    private void openFulfilDialog(final Habit habit) {
        List<CharSequence> fulfilList;
        fulfilList = getFulfilList(habit);
        AlertDialog.Builder builder = new AlertDialog.Builder(AllHabitsActivity.this);
        // Set the dialog title
        builder.setTitle("Fulfilled " + habit.getFulfilDate().size() + " time(s).")
        // Specify the list array, the items to be selected by default (null for none),
        // and the listener through which to receive callbacks when items are selected
        // https://www.tutorialspoint.com/java/util/arraylist_toarray.htm
                .setMultiChoiceItems(fulfilList.toArray(new CharSequence[fulfilList.size()]), null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        // If the user checked the item, add it to the selected items
                        if (isChecked) {
                            fulfilForDeleting.add(which);
                        }
                        // Else, if the item is already in the array, remove it
                        else if (fulfilForDeleting.contains(which)) {
                            fulfilForDeleting.remove(which);
                        }
                    }
                })
        // Set the action buttons
                // delete selected fulfil date(s)
                .setPositiveButton("Delete Fulfilment", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        deleteFulfilment(habit);
                    }
                })
                // delete habit
                .setNeutralButton("Delete Habit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteHabit(habit);
                        habitAdapter.notifyDataSetChanged();
                    }
                })
                // cancel
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        // Create & Show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Open this dialog if the habit has not been fulfilled.
     * @param habit
     */
    private void openUnfulfilDialog(final Habit habit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AllHabitsActivity.this);
        builder.setTitle("The habit has not been fulfilled.")
                // delete habit
                .setPositiveButton("Delete Habit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteHabit(habit);
                        habitAdapter.notifyDataSetChanged();
                    }
                })
                // cancel
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // Create & Show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /**
     * Delete selected fulfil date(s)
     * @param habit
     */
    private void deleteFulfilment(Habit habit) {
        fulfilDate = habit.getFulfilDate();
        // clear the ArrayList in case of IndexOutOfRange Exception
        if (fulfilForDeleting.size() == fulfilDate.size()) {
            fulfilDate.clear();
        }
        // remove dates 1 by 1
        else {
            // https://www.tutorialspoint.com/java/util/arraylist_remove.htm
            for (int index : fulfilForDeleting) {
                fulfilDate.remove(index);
            }
        }

        // reset fulfilDate & make changed to json file
        habit.setFulfilDate(fulfilDate);
        InputOutputGSON IOGson = new InputOutputGSON(this);
        IOGson.saveInFile(habit);
        // reload
        habits.clear();
        fulfilDate.clear();
        fulfilForDeleting.clear();
        habits = IOGson.loadFromAllFiles();
        habitAdapter.notifyDataSetChanged();
    }

    /**
     * Delete habit.
     * @param habit
     */
    private void deleteHabit(Habit habit) {
        // make changes to json file & reload
        InputOutputGSON IOGson = new InputOutputGSON(this);
        IOGson.deleteFile(habit);
        habits.remove(habit);
        habitAdapter.clear();
        habitAdapter.addAll(habits);
        habitAdapter.notifyDataSetChanged();
    }

    /**
     * Get an ArrayList of fulfilDate in String.
     * @param habit
     * @return ArrayList<CharSequence> fulfilList
     */
    private ArrayList<CharSequence> getFulfilList(Habit habit) {
        ArrayList<CharSequence> fulfilList = new ArrayList<>();
        try {
            for (Calendar date : habit.getFulfilDate()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
                String fulfilString = dateFormat.format(date.getTime());
                fulfilList.add(fulfilString);
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
        return fulfilList;
    }



}
