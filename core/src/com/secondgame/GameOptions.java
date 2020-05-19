package com.secondgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameOptions {
    private static final String STARTING_LEVEL = "startingLevel";
    private static final String MUSIC_VOLUME = "volume";
    private static final String MUSIC_ENABLED = "music.enabled";
    private static final String OPTIONS_NAME = "theTripOptions";
    private static final String PLAYER_LIVES = "lives";
    private static final String CURRENT_LEVEL = "currentLevel";

    protected Preferences getOptions() {
        return Gdx.app.getPreferences(OPTIONS_NAME);
    }

    // on by default
    public boolean isMusicEnabled() {
        return getOptions().getBoolean(MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getOptions().putBoolean(MUSIC_ENABLED, musicEnabled);
        getOptions().flush(); // make sure options stay as put
    }

    public float getMusicVolume() {
        return getOptions().getFloat(MUSIC_VOLUME, 1f);
    }

    public void setMusicVolume(float volumeToSet) {
        getOptions().putFloat(MUSIC_VOLUME, volumeToSet);
        getOptions().flush();
    }

    public int getStartingLevel() {
        return getOptions().getInteger(STARTING_LEVEL, 1);
    }

    public void setStartingLevel(int levelToStartOn) {
        getOptions().putInteger(STARTING_LEVEL, levelToStartOn);
        getOptions().flush();
    }

    public int getPlayerLives() {
        return getOptions().getInteger(PLAYER_LIVES, 3);
    }

    public void setPlayerLives(int lives) {
        getOptions().putInteger(PLAYER_LIVES, lives);
        getOptions().flush();
    }

    public int getCurrentLevel() {
        return getOptions().getInteger(CURRENT_LEVEL, 1);
    }

    public void setCurrentLevel(int level) {
        getOptions().putInteger(CURRENT_LEVEL, level);
    }



}
