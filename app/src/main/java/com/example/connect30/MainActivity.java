package com.example.connect30;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.gridlayout.widget.GridLayout;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.MessageFormat;


public class MainActivity extends AppCompatActivity {
    // 0 = yellow and 1 = red
    int activePlayer = 0;
    boolean gameIsActive = true;

    // 2 means unplayed

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};


    @SuppressLint("SetTextI18n")
    public void DropIn(View View) {

        ImageView Counter = (ImageView) View;

        int tappedCounter = Integer.parseInt(Counter.getTag().toString());
        if (gameState[tappedCounter] == 2 && gameIsActive) {
            gameState[tappedCounter] = activePlayer;

            Counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                Counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                Counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            Counter.animate().translationYBy(1000f).rotation(360).setDuration(600);

            for (int[] winningposition : winningPositions) {

                if (gameState[winningposition[0]] == gameState[winningposition[1]] &&
                        gameState[winningposition[1]] == gameState[winningposition[2]] &&
                        gameState[winningposition[0]] != 2) {


                    //someone has won
                    gameIsActive = false;

                    String winner = "Red";

                    if (gameState[winningposition[0]] == 0) {
                        winner = "Yellow";
                    }


                    //someone has won
                    TextView winnerMessage = findViewById(R.id.winnerMessage);
                    winnerMessage.setText(MessageFormat.format("{0} HAS WON!", winner));
                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    layout.setVisibility(android.view.View.VISIBLE);

                } else {
                    boolean gameIsOver = true;
                    for (int counterState : gameState) {
                        if (counterState == 2) {
                            gameIsOver = false;
                            break;
                        }
                    }
                    if (gameIsOver) {
                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                        winnerMessage.setText("IT IS A DRAW!");
                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        layout.setVisibility(android.view.View.VISIBLE);

                    }

                }

            }
        }
    }

    public void playAgain(View View) {
        gameIsActive = true;
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(android.view.View.INVISIBLE);
        activePlayer = 0;


        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {

            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
