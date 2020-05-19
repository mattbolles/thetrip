package com.secondgame;

//heads up display, displaying health and lives

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

    Label livesNumLabel;
    Label healthNumLabel;
    Label livesLabel;
    Label healthLabel;
    //private ShapeRenderer shapeRenderer;


    public Hud(SpriteBatch spriteBatch) {
        health = 100;
        lives = 3;
        //shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameInfo.SCREEN_WIDTH, GameInfo.SCREEN_HEIGHT);
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport, spriteBatch);

        Table table = new Table();
        table.top(); // put in top of stage
        table.setFillParent(true); // table is size of stage

        livesNumLabel = new Label(String.format("%01d", lives), GameInfo.LABEL_STYLE_18);
        healthNumLabel = new Label(String.format("%.0f", health), GameInfo.LABEL_STYLE_18);
        livesLabel = new Label("LIVES ", GameInfo.LABEL_STYLE_18);
        healthLabel = new Label("HEALTH ", GameInfo.LABEL_STYLE_18);
        table.row().pad(50, 50, 0, 0);
        table.add(livesLabel).right();
        table.add(livesNumLabel).left();
        table.row().pad(20, 50 , 0, 0);
        table.add(healthLabel).right();
        table.add(healthNumLabel).left();

        stage.addActor(table);
        table.left();
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setHealth(float health) {
        this.health = health;
    }

}
