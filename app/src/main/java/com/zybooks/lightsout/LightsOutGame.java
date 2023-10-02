package com.zybooks.lightsout;

import java.util.Random;

public class LightsOutGame {
    public static final int GRID_SIZE = 3;

    // Lights that make up the grid
    private final boolean[][] mLightsGrid;

    public LightsOutGame() {
        mLightsGrid = new boolean[GRID_SIZE][GRID_SIZE];
    }

    public void newGame() {
        Random randomNumGenerator = new Random();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                mLightsGrid[row][col] = randomNumGenerator.nextBoolean();
            }
        }
    }

    public boolean isLightOn(int row, int col) {
        return mLightsGrid[row][col];
    }

    public void selectLight(int row, int col) {
        mLightsGrid[row][col] = !mLightsGrid[row][col];
        if (row > 0) {
            mLightsGrid[row - 1][col] = !mLightsGrid[row - 1][col];
        }
        if (row < GRID_SIZE - 1) {
            mLightsGrid[row + 1][col] = !mLightsGrid[row + 1][col];
        }
        if (col > 0) {
            mLightsGrid[row][col - 1] = !mLightsGrid[row][col - 1];
        }
        if (col < GRID_SIZE - 1) {
            mLightsGrid[row][col + 1] = !mLightsGrid[row][col + 1];
        }
    }

    public boolean isGameOver() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (mLightsGrid[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    // getState() returns a string representing the on/off state of the light grid
    public String getState() {
        StringBuilder boardString = new StringBuilder();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                char value = mLightsGrid[row][col] ? 'T' : 'F';
                boardString.append(value);
            }
        }

        return boardString.toString();
    }

    // setState() returns the light grid back to the same on/off state as before
    public void setState(String gameState) {
        int index = 0;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                mLightsGrid[row][col] = gameState.charAt(index) == 'T';
                index++;
            }
        }
    }

    public void trick()
    {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                mLightsGrid[row][col] = false;
            }
        }
    }

}