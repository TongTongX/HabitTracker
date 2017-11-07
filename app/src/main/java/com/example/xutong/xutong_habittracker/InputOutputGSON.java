package com.example.xutong.xutong_habittracker;

import android.content.Context;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by TongTong on 25/09/2016.
 */
public class InputOutputGSON {
    // Code taken from http://stackoverflow.com/questions/30929838/cannot-resolve-method-openfileoutputjava-lang-string-int
    private Context context;

    /**
     * Constructor.
     * @param context Context for managing files.
     */
    public InputOutputGSON(Context context) {
        this.context = context;
    }

    /**
     * Load Habit objects from all saved json files.
     * @return list of Habit objects.
     */
    protected ArrayList<Habit> loadFromAllFiles() {
        ArrayList<Habit> habitList = new ArrayList<Habit>();
        Habit habitObj;
        // https://developer.android.com/reference/android/content/Context.html#fileList()
        String[] fileList = this.context.fileList();
        for (int i = 1; i < fileList.length; ++i) {
            try {
                FileInputStream fis = this.context.openFileInput(fileList[i]);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                Gson gson = new Gson();
                habitObj = gson.fromJson(in, Habit.class);
                habitList.add(habitObj);
            } catch (FileNotFoundException e) {
                System.out.println("Error " + e.getMessage());
                throw new RuntimeException();
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
                throw new RuntimeException();
            }
        }

        return habitList;
    }

    /**
     * Get the json file's name based on a habit's datetime.
     * @param habit Habit object.
     * @return json file name corresponding to the habit.
     */
    protected String jsonFileName(Habit habit) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return dateFormat.format(habit.getHabitDate().getTime()) + ".json";
    }

    /**
     * Delete the json file corresponding to the habit.
     * @param habit Habit object.
     */
    protected void deleteFile(Habit habit) {
        String fileName = jsonFileName(habit);
        this.context.deleteFile(fileName);
    }

    /**
     * Save a Habit object to its corresponding json file.
     * @param habit Habit object.
     */
    protected void saveInFile(Habit habit) {
        String fileName = jsonFileName(habit);
        try {
            FileOutputStream fos = this.context.openFileOutput(fileName, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(habit, writer);
            writer.flush();
        } catch (FileNotFoundException e) {
            // Code taken from http://stackoverflow.com/questions/22248311/how-to-handle-try-catch-exception-android
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException();
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException();
        }
    }
}

