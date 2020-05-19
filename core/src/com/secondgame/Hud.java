package com.secondgame;

//heads up display, displaying health and lives

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud {
    public Stage hudStage;
    private Viewport hudViewport;
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
        hudViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudStage = new Stage(hudViewport, spriteBatch);

        Table hud = new Table();
        hud.top(); // put in top of stage
        hud.setFillParent(true); // table is size of stage

        livesNumLabel = new Label(String.format("%01d", lives), GameInfo.LABEL_STYLE_18);
        healthNumLabel = new Label(String.format("%.0f", health), GameInfo.LABEL_STYLE_18);
        livesLabel = new Label("LIVES ", GameInfo.LABEL_STYLE_18);
        healthLabel = new Label("HEALTH ", GameInfo.LABEL_STYLE_18);
        hud.row().pad(50, 50, 0, 0);
        hud.add(livesLabel).right();
        hud.add(livesNumLabel).left();
        hud.row().pad(20, 50 , 0, 0);
        hud.add(healthLabel).right();
        //hud.add(healthNumLabel).left();

        hudStage.addActor(hud);
        hud.left();
    }


    public void setLives(int lives) {
        this.lives = lives;
        livesNumLabel.setText(String.format("%01d", lives));
    }

    public void setHealth(float health) {
        this.health = health;
        healthNumLabel.setText(String.format("%.0f", health));
    }

}
