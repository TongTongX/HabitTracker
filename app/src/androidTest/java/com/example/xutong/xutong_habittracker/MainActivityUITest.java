package com.example.xutong.xutong_habittracker;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;

/**
 * Created by Kelly on 2017-12-06.
 */
public class MainActivityUITest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public MainActivityUITest() {
        super(com.example.xutong.xutong_habittracker.MainActivity.class);
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


}//end
