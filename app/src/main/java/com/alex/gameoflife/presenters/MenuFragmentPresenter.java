package com.alex.gameoflife.presenters;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;

import com.alex.gameoflife.activities.GameActivity;
import com.alex.gameoflife.activities.MainActivity;
import com.alex.gameoflife.fragments.MenuFragment;

public class MenuFragmentPresenter {

    public static final String SIZE = MainActivity.APP_NAME + "SIZE";
    public static final String TYPE = MainActivity.APP_NAME + "TYPE";

    private MenuFragment fragment;

    public MenuFragmentPresenter(MenuFragment fragment) {
        this.fragment = fragment;
    }

    public void launch(int size, String type) {
        Log.i("#####", "MenuFragmentPresenter launch / " + size + " / " +type);

        Intent intent = new Intent(fragment.getActivity(), GameActivity.class);
        intent.putExtra(SIZE, size);
        intent.putExtra(TYPE, type);
        fragment.startActivity(intent);
        if (fragment.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            fragment.getActivity().finish();
    }

}
