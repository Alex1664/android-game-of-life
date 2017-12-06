package com.alex.gameoflife.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.alex.gameoflife.R;
import com.alex.gameoflife.presenters.MenuFragmentPresenter;

public class MenuFragment extends Fragment implements View.OnClickListener {

    private MenuFragmentPresenter presenter;

    private EditText sizeEditText;

    public MenuFragment() {
        this.presenter = new MenuFragmentPresenter(this);
    }

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        sizeEditText = view.findViewById(R.id.size_et);

        view.findViewById(R.id.random_button).setOnClickListener(this);
        view.findViewById(R.id.custom_button).setOnClickListener(this);

        return view;
    }

    public void launchRandom() {
        String size = sizeEditText.getText().toString();
        presenter.launch(Integer.parseInt(size), "RANDOM");
    }

    public void launchCustom() {
        String size = sizeEditText.getText().toString();
        presenter.launch(Integer.parseInt(size), "CUSTOM");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.random_button:
                Log.i("#####", "MenuFragment Random");
                launchRandom();
                break;
            case R.id.custom_button:
                Log.i("#####", "MenuFragment Custom");
                launchCustom();
                break;
            default:
                Toast.makeText(getActivity(), "Onclick ID not recognized", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
