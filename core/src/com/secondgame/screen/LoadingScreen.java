package com.secondgame.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.secondgame.TheTrip;
import com.secondgame.resource.GameState;

public class LoadingScreen extends ScreenAdapter {
    private TheTrip game;

    public LoadingScreen(TheTrip game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        System.out.println("loading...");
        game.loadScreen(GameState.INTRO);
    }
}
