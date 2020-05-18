package com.secondgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.secondgame.gameobject.GameObject;
import com.secondgame.gameobject.Player;

public class TheTrip extends ApplicationAdapter {
    SpriteBatch spriteBatch;
    OrthographicCamera camera;
    GameMap gameMap;
    float deltaX;
    float deltaY;
    SecondGame game;
    int selectedLevel;

    public TheTrip(SecondGame game) {
        this.game = game;
    }

    @Override
    public void create () {
        spriteBatch = new SpriteBatch();
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        selectedLevel = game.getOptions().getStartingLevel();
        gameMap = new TiledGameMap(selectedLevel);
    }

    @Override
    public void render () {

        Gdx.gl.glClearColor(0, 256, 100, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // if clicked
        if (Gdx.input.isTouched()) {
            // drag gameMap
            camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
            camera.update();
        }

        // if clicked - after click finished
        if (Gdx.input.justTouched()) {
            Vector3 clickPositionOnScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            Vector3 clickPositionOnMap = camera.unproject(clickPositionOnScreen);
            TileType currentTileType = gameMap.getTileTypeByLocation(1, clickPositionOnMap.x, clickPositionOnMap.y);
            System.out.println(currentTileType == null);
            if (currentTileType != null) {
                System.out.println("Click on tile id: " + currentTileType.getId() + " name: " + currentTileType.getName() + " is collidable: " + currentTileType.isCollidable() + " damage: " + currentTileType.getDamage());


            }
        }
        for (GameObject gameObject : gameMap.gameObjects) {
            if (gameObject.isPlayer()) {
                //System.out.println("player pos: " + gameObject.getPosition());
                //System.out.println("new cam pos:" + newCameraPosition);
                //System.out.println("old cam pos:" + camera.position);
                if (gameObject.getPosition().x > camera.position.x) {
                    camera.position.x = gameObject.getPosition().x;
                }

                // reach left side
                if (gameObject.getPosition().x < camera.position.x && (gameObject.getPosition().x > GameInfo.SCREEN_WIDTH / 2)) {
                    camera.position.x = gameObject.getPosition().x;
                }

                //reach right side
                if (camera.position.x > GameInfo.WORLD_WIDTH - GameInfo.SCREEN_WIDTH / 2) {
                    camera.position.x = GameInfo.WORLD_WIDTH - GameInfo.SCREEN_WIDTH / 2;
                }

                //System.out.println(getNewCameraPosition());
            }
        };
        camera.update();
        // deltatime is time between last update and now
        gameMap.update(Gdx.graphics.getDeltaTime());
        gameMap.render(camera, spriteBatch);
    }


    @Override
    public void dispose () {
        spriteBatch.dispose();
        gameMap.dispose();
    }
}
