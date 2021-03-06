package com.secondgame.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.secondgame.TheTrip;

public class SoundPlayer {

    public static AssetManager assetManager = new AssetManager();
    private static Music backgroundMusic = null;
    public float volume;
    TheTrip game;


    public SoundPlayer(TheTrip game) {
        this.game = game;
    }


    public static void init() {
        // load music files
        for (int i = 1; i < 2; i++) {
            assetManager.load(Gdx.files.internal("music/level" + i + ".mp3").path(), Sound.class);
        }
        assetManager.finishLoading();
    }

    public static void disposeSounds() {
        assetManager.dispose();
    }

    public void playMusic(int level) {
        boolean musicEnabled = game.getOptions().isMusicEnabled();
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/level" + level + ".mp3"));
        backgroundMusic.setLooping(true);
        volume = game.getOptions().getMusicVolume();
        backgroundMusic.setVolume(volume);
        if (musicEnabled) {
            backgroundMusic.play();
        }
    }

    public void stopMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic = null;
        }
    }
}
