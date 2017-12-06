package com.alex.gameoflife.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.alex.gameoflife.R;
import com.alex.gameoflife.presenters.GameFragmentPresenter;
import com.alex.gameoflife.presenters.MenuFragmentPresenter;

public class GameFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private GameFragmentPresenter presenter;

    private int size;
    private String type;

    public GameFragment() {
        this.presenter = new GameFragmentPresenter(this);
    }

    public static GameFragment newInstance(int size, String type) {
        GameFragment fragment = newInstance();

        Bundle args = new Bundle();
        args.putInt(MenuFragmentPresenter.SIZE, size);
        args.putString(MenuFragmentPresenter.TYPE, type);
        fragment.setArguments(args);

        return fragment;
    }

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        if(getArguments() == null)
            return view;

        this.size = getArguments().getInt(MenuFragmentPresenter.SIZE);
        this.type = getArguments().getString(MenuFragmentPresenter.TYPE);

        SeekBar seekBar = view.findViewById(R.id.speedBar);
        seekBar.setOnSeekBarChangeListener(this);

        ToggleButton toogleButton = view.findViewById(R.id.onOffButton);
        toogleButton.setOnClickListener(this);
        if(type.equals("CUSTOM")) {
            presenter.playPause();
            toogleButton.setChecked(false);
        }

        Log.i("#####", "GameFragment create / " + size + " / " + type);

        LinearLayout linearLayout = view.findViewById(R.id.layoutGame);
        presenter.initTabs(linearLayout, size, type);

        presenter.startGame();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.onOffButton:
                Log.i("#####", "GameFragment OnOff");
                presenter.playPause();
                break;
            default:
                Toast.makeText(getActivity(), "Onclick ID not recognized", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        presenter.setSpeed(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
