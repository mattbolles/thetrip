package com.secondgame;

//heads up display, displaying health and lives

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud {
    public Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;

    private float health;
    private Integer lives;

    Label livesLabel;
    Label healthLabel;

    public Hud(SpriteBatch spriteBatch) {
        health = 100;
        lives = 3;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameInfo.SCREEN_WIDTH, GameInfo.SCREEN_HEIGHT);
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport, spriteBatch);

        Table table = new Table();
        table.top(); // put in top of stage
        table.setFillParent(true); // table is size of stage

        livesLabel = new Label(String.format("%01d", lives), GameInfo.LABEL_STYLE_18);
        healthLabel = new Label(String.format("%06f", health), GameInfo.LABEL_STYLE_18);
        table.add(livesLabel);
        table.row().pad(20, 0 , 0, 0);
        table.add(healthLabel);

        stage.addActor(table);
    }

}
