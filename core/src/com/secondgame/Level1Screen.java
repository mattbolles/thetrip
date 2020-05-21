package com.secondgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.secondgame.map.TileType;
import com.secondgame.map.TiledGameMap;

public class Level1Screen extends ScreenAdapter {

    private SecondGame game;
    private TiledGameMap map;
    SpriteBatch spriteBatch;
    OrthographicCamera camera;
    float deltaX;
    float deltaY;
    float width;
    float height;
    private Stage stage;


    public Level1Screen(SecondGame game) {
        this.game = game;
        map = new TiledGameMap(1);
        spriteBatch = new SpriteBatch();
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        /*camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);*/
        stage = new Stage(new ScreenViewport());
    }



    // this will be called whens screen called for first time
    @Override
    public void show() {
        stage.clear();
        //camera.update();
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 256, 100, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();


        // if clicked - after click finished
        if (Gdx.input.justTouched()) {
            Vector3 clickPositionOnScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            Vector3 clickPositionOnMap = camera.unproject(clickPositionOnScreen);
            TileType currentTileType = map.getTileTypeByLocation(1, clickPositionOnMap.x, clickPositionOnMap.y);
            System.out.println(currentTileType == null);
            if (currentTileType != null) {
                System.out.println("Click on tile id: " + currentTileType.getId() + " name: " + currentTileType.getName() + " is collidable: " + currentTileType.isCollidable() + " damage: " + currentTileType.getDamage());


            }
        }
        camera.update();
        // deltatime is time between last update and now
        map.update(Gdx.graphics.getDeltaTime());
        map.render(camera, spriteBatch);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}