package com.alex.gameoflife.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alex.gameoflife.R;
import com.alex.gameoflife.fragments.GameFragment;
import com.alex.gameoflife.presenters.MenuFragmentPresenter;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        int size = intent.getIntExtra(MenuFragmentPresenter.SIZE, 0);
        String type = intent.getStringExtra(MenuFragmentPresenter.TYPE);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.game, GameFragment.newInstance(size, type))
                .commit();
    }

    @Override
    public void onBackPressed(){
        finish();
    }

}
