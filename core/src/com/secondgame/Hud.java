package com.secondgame;

//heads up display, displaying health and lives

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud {
    public Stage stage;
    private Viewport viewport;

    private float health;
    private Integer lives;

    Label livesLabel;

    public Hud(SpriteBatch spriteBatch) {
        health = 100;
        lives = 3;

        viewport = new FitViewport(GameInfo.SCREEN_WIDTH, GameInfo.SCREEN_HEIGHT);
    }

}
