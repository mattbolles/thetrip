package com.secondgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class GameInfo {

    public static final BitmapFont FONT18;
    public static final Label.LabelStyle LABEL_STYLE_18;
    public static final int WORLD_WIDTH = 3840; // 120 tiles (32x32)
    public static final int WORLD_HEIGHT = 960; // 30 tiles
    /*public static final int SCREEN_WIDTH = 1280; // 40 tiles
    public static final int SCREEN_HEIGHT = 960; // 30 tiles*/
    public static final int SCREEN_WIDTH = 800; // 40 tiles
    public static final int SCREEN_HEIGHT = 600; // 30 tiles
    public static final int offsetMaxX = WORLD_WIDTH - (SCREEN_WIDTH / 2);
    public static final int offsetMaxY = WORLD_HEIGHT - SCREEN_HEIGHT;
    public static final int offsetMinX = 0;
    public static final int offsetMinY = 0;
    public static final int PLAYER_WIDTH = 30;
    public static final int PLAYER_HEIGHT = 64;
    public static final float ANIMATION_SPEED = 0.5f;


    static {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/MAIN.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        FONT18 = generator.generateFont(parameter); // font size 18 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        LABEL_STYLE_18 = new Label.LabelStyle();
        LABEL_STYLE_18.font = FONT18;
    }
}
