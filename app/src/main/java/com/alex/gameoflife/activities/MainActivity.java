package com.alex.gameoflife.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alex.gameoflife.R;
import com.alex.gameoflife.fragments.MenuFragment;

public class MainActivity extends AppCompatActivity {

    public static final String APP_NAME = "com.alex.gameoflife";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.menu, MenuFragment.newInstance())
                .commit();
    }

}
