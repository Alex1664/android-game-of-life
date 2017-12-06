package com.alex.gameoflife.presenters;

import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.gameoflife.fragments.GameFragment;

public class GameFragmentPresenter {

    private GameFragment fragment;

    private static double PROBA = 0.3;
    private static int TIME = 500;
    private static boolean CONTINUE = true;

    private boolean[][] mainTab;
    private int[][] neighborTab;
    private GradientDrawable[][] backgroundTab;
    private int size;

    private Handler handler;

    public GameFragmentPresenter(GameFragment fragment) {
        this.fragment = fragment;
    }

    public void initTabs(LinearLayout linearLayout, int size, String type) {
        Log.i("#####", "GameFragmentPresenter initTabs debut / " + linearLayout + " / " + size + " / " + type);

        this.handler = new Handler();
        this.size = size;
        mainTab = new boolean[this.size][this.size];
        neighborTab = new int[this.size][this.size];
        backgroundTab = new GradientDrawable[this.size][this.size];

        if (type.equals("RANDOM")) {
            for (int i = 0; i < this.size; i++)
                for (int j = 0; j < this.size; j++)
                    mainTab[i][j] = Math.random() < PROBA;
        }

        DisplayMetrics metrics = new DisplayMetrics();
        fragment.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        final int width = metrics.widthPixels;
        final int widthCell = width / (this.size - (this.size * 5 / 100));

        for (int i = 0; i < size; i++) {
            Log.i("#####", "GameFragmentPresenter creation layout : " + i);
            LinearLayout linearLayoutH = new LinearLayout(fragment.getActivity());
            linearLayoutH.setOrientation(LinearLayout.HORIZONTAL);

            for (int j = 0; j < size; j++) {
                final TextView tvTmp = new TextView(fragment.getActivity());
                GradientDrawable gd = new GradientDrawable();
                if (!mainTab[i][j])
                    gd.setColor(0xff000000);
                gd.setStroke(1, 0xFF000000);
                gd.setSize(widthCell, widthCell);
                backgroundTab[i][j] = gd;
                tvTmp.setBackground(gd);
                tvTmp.setTag(i + "," + j);
                tvTmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateTV(tvTmp);
                    }
                });
                linearLayoutH.addView(tvTmp);
            }
            linearLayout.addView(linearLayoutH);
        }

        Log.i("#####", "GameFragmentPresenter fin initTabs");
    }

    private void updateTV(TextView textView) {
        String tag = (String) textView.getTag();
        int i = Integer.parseInt(tag.substring(0, tag.indexOf(",")));
        int j = Integer.parseInt(tag.substring(tag.indexOf(",") + 1, tag.length()));

        mainTab[i][j] = !mainTab[i][j];
        if (mainTab[i][j])
            backgroundTab[i][j].setColor(0x00000000);
        else backgroundTab[i][j].setColor(0xff000000);
    }

    public void startGame() {
        changeIteration.run();
    }

    private Runnable changeIteration = new Runnable() {
        @Override
        public void run() {
            if (!CONTINUE) {
                Toast.makeText(fragment.getActivity(), "Pause", Toast.LENGTH_SHORT).show();
            } else {

                updateTab();

                // Save neighbors
                for (int i = 0; i < size; i++)
                    for (int j = 0; j < size; j++)
                        neighborTab[i][j] = nbNeighbor(i, j);

                // Do gameOfLife magic
                for (int i = 0; i < size; i++)
                    for (int j = 0; j < size; j++)
                        doMagic(i, j, neighborTab[i][j]);

                handler.postDelayed(changeIteration, TIME);
            }
        }
    };

    private void doMagic(int x, int y, int nbN) {
        if (mainTab[x][y] && nbN < 2)
            mainTab[x][y] = false;
        if (mainTab[x][y] && nbN > 3)
            mainTab[x][y] = false;
        if (!mainTab[x][y] && nbN == 3)
            mainTab[x][y] = true;
    }

    private int nbNeighbor(int x, int y) {
        int nbNeighbors = 0;

        for (int i = x - 1; i < x + 2; i++)
            for (int j = y - 1; j < y + 2; j++)
                if (i >= 0 && i < size && j >= 0 && j < size && mainTab[i][j])
                    nbNeighbors++;

        if (mainTab[x][y])
            return nbNeighbors - 1;
        return nbNeighbors;
    }

    private void updateTab() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mainTab[i][j])
                    backgroundTab[i][j].setColor(0x00000000);
                else backgroundTab[i][j].setColor(0xff000000);
            }
        }
    }

    public void playPause() {
        CONTINUE = !CONTINUE;
        if (CONTINUE)
            Toast.makeText(fragment.getActivity(), "Play", Toast.LENGTH_SHORT / 2).show();
        changeIteration.run();
    }

    public void setSpeed(int speed) {
        TIME = (2000) - (speed * 20);
    }
}
