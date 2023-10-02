package com.zybooks.lightsout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private LightsOutGame mGame;
    private GridLayout mLightGrid;
    private int mLightOnColor;
    private int mLightOffColor;
    private final String GAME_STATE = "gameState";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLightGrid = findViewById(R.id.light_grid);

        // Add the same click handler to all grid buttons
        for (int buttonIndex = 0; buttonIndex < mLightGrid.getChildCount(); buttonIndex++) {
            Button gridButton = (Button) mLightGrid.getChildAt(buttonIndex);
            gridButton.setOnLongClickListener(view -> {
                int r = mLightGrid.indexOfChild(view) / LightsOutGame.GRID_SIZE;
                int c = mLightGrid.indexOfChild(view) % LightsOutGame.GRID_SIZE;
                if (r == 0 && c == 0)
                {
                    mGame.trick();
                    setButtonColors();
                    Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show();
                    return true;
                }
                else
                    return false;
            });
            gridButton.setOnClickListener(this::onLightButtonClick);
        }

        mLightOnColor = ContextCompat.getColor(this, R.color.yellow);
        mLightOffColor = ContextCompat.getColor(this, R.color.black);

        mGame = new LightsOutGame();
        if (savedInstanceState == null) {
            startGame();
        }
        else {
            // When MainActivity is recreated, the savedInstanceState parameter is assigned the previously
            // saved Bundle containing gameState. The LightsOutGame object is recreated.
            // The light grid state is extracted from savedInstanceState.
            String gameState = savedInstanceState.getString(GAME_STATE);
            mGame.setState(gameState);
            setButtonColors();
        }
    }

    private void startGame() {
        mGame.newGame();
        setButtonColors();
    }

    private void onLightButtonClick(View view) {

        // Find the button's row and col
        int buttonIndex = mLightGrid.indexOfChild(view);
        int row = buttonIndex / LightsOutGame.GRID_SIZE;
        int col = buttonIndex % LightsOutGame.GRID_SIZE;

        mGame.selectLight(row, col);
        setButtonColors();

        // Congratulate the user if the game is over
        if (mGame.isGameOver()) {
            Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show();
        }
    }

    private void setButtonColors() {

        for (int buttonIndex = 0; buttonIndex < mLightGrid.getChildCount(); buttonIndex++) {
            Button gridButton = (Button) mLightGrid.getChildAt(buttonIndex);

            // Find the button's row and col
            int row = buttonIndex / LightsOutGame.GRID_SIZE;
            int col = buttonIndex % LightsOutGame.GRID_SIZE;

            if (mGame.isLightOn(row, col)) {
                gridButton.setBackgroundColor(mLightOnColor);
                gridButton.setContentDescription(getString(R.string.on));
            } else {
                gridButton.setBackgroundColor(mLightOffColor);
                gridButton.setContentDescription(getString(R.string.off));
            }
        }
    }

    public void onNewGameClick(View view) {
        startGame();
    }

    // OnSaveInstanceState() is called when the device is rotated.
    // The LightsOutGame object is destroyed along with MainActivity.
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(GAME_STATE, mGame.getState());
    }

    /*
    Pressing Help calls the click callback onHelpClick(), which creates an intent using the name of the activity to be started.
    startActivity() starts the activity named in the intent. HelpActivity starts and displays on top of MainActivity.
    Pressing the device's Back button destroys HelpActivity and restarts MainActivity.
    */
    public void onHelpClick(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }
}