package com.example.xutong.xutong_habittracker;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

public class AddEditHabitActivity extends AppCompatActivity implements View.OnClickListener{
    private Habit newHabit;
    private Calendar habitDate;
    private EditText habitNameText;
    private EditText habitDateText;
    private EditText habitDaysText;
    private ArrayList<String> daysOfWeek = new ArrayList<String>();
    private Button addHabitButton;
    private Button cancelAddButton;
    private int year;
    private int month;
    private int day;
    private String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_habit);

        habitNameText = (EditText) findViewById(R.id.edit_habit_name);

        habitDateText = (EditText) findViewById(R.id.edit_date);
        habitDateText.setOnClickListener(this);

        habitDaysText = (EditText) findViewById(R.id.edit_days);
        habitDaysText.setOnClickListener(this);

        addHabitButton = (Button) findViewById(R.id.add_habit_button);
        addHabitButton.setOnClickListener(this);

        cancelAddButton = (Button) findViewById(R.id.cancel_add_button);
        cancelAddButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == habitDateText ) {
            openDatePickerDialog();
        } else if (view == habitDaysText) {
            openDaysPickerDialog();
        } else if (view == addHabitButton) {
            addHabit();
        } else if (view == cancelAddButton) {
            finish();
        }
    }

    protected void addHabit() {
        boolean validHabit = true;

        String habitName = habitNameText.getText().toString();
        if (habitName.isEmpty() || habitName.trim().isEmpty()) {
            validHabit = false;
            Toast.makeText(this, "Please enter a Habit name.", Toast.LENGTH_SHORT).show();
        }

        habitDate = Calendar.getInstance();
        habitDate.set(year, month - 1, day, 0, 0);

        if (daysOfWeek.isEmpty()) {
            validHabit = false;
            Toast.makeText(this, "Please specify day(s) of week it should occur on.", Toast.LENGTH_SHORT).show();
        }

        if (validHabit) {
            try {
                newHabit = new IncompleteHabit(habitName, habitDate, daysOfWeek);
                InputOutputGSON IOGson = new InputOutputGSON(this);
                IOGson.saveInFile(newHabit);
                // http://stackoverflow.com/questions/14848590/return-back-to-mainactivity-from-another-activity
                finish();
                Log.d("list_file", fileList().toString());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }

    private void openDatePickerDialog() {
        // http://stackoverflow.com/questions/9474121/i-want-to-get-year-month-day-etc-from-java-date-to-compare-with-gregorian-calen
        final Calendar today = Calendar.getInstance();
        year = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        day = today.get(Calendar.DAY_OF_MONTH);

        habitDateText.setHint(year + "-" + month + "-" + day);
        // https://developer.android.com/reference/android/app/DatePickerDialog.html
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            // https://developer.android.com/reference/android/app/DatePickerDialog.OnDateSetListener.html
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                habitDateText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    // https://developer.android.com/guide/topics/ui/dialogs.html
    private void openDaysPickerDialog() {
        daysOfWeek = new ArrayList();   // Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(AddEditHabitActivity.this);
        builder.setTitle(R.string.days_dialog)  // Set the dialog title
        // Specify the list array, the items to be selected by default (null for none),
        // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(R.array.days_of_week, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            daysOfWeek.add(week[which]);
                        } else if (daysOfWeek.contains(week[which])) {
                            daysOfWeek.remove(week[which]);
                        }
                    }
                })
        // Set the action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, print habitDaysText
                        habitDaysText.setText(daysOfWeek.toString().replace("[", "").replace("]", ""));
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }

                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}


