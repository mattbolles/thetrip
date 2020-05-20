package com.secondgame.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.secondgame.GameMap;

public class Bullet {

    public static final int SPEED = 500;
    private static Texture bulletTexture;

    float x;
    float y;
    String direction;
    OrthographicCamera camera;
    Vector3 bulletPos;
    float relativeBulletSpawnX;
    float relativeBulletSpawnY;
    float absBulletSpawnX;
    float absBulletSpawnY;
    protected GameMap gameMap;
    int height;
    int width;
    float newXPosition;



    public boolean needToRemove = false;

    public Bullet(float x, float y, String direction, OrthographicCamera camera, GameMap gameMap) {
        this.x = x;
        this.y = y;
        this.height = 12;
        this.width = 12;
        this.newXPosition = x;
        this.absBulletSpawnX = x;
        this.absBulletSpawnY = y;
        this.direction = direction;
        this.camera = camera;
        this.gameMap = gameMap;
        bulletPos = new Vector3(x, y, 0);
        camera.unproject(bulletPos);
        relativeBulletSpawnX = bulletPos.x;
        relativeBulletSpawnY = bulletPos.y;
        if (bulletTexture == null) {
            bulletTexture = new Texture("images/bullet.png");
        }
    }

    public void update(float deltaTime, float gravity) {
        System.out.println("from bullet, bulletpos: " + bulletPos.x + ", " + bulletPos.y);
        System.out.println("pos : " + x + ", " + y);


        if ("right".equals(direction)) {
            newXPosition = x + SPEED * deltaTime;
            //x += SPEED * deltaTime;
        }

        if ("left".equals(direction)) {
            newXPosition = x - SPEED * deltaTime;
            //x -= SPEED * deltaTime;
        }

        // if does not collide, move to new position
        if (!gameMap.checkIfCollidesWithTiles(newXPosition, y, width, height)) {
            x = newXPosition;
        }

        // if collides, remove it

        if (gameMap.checkIfCollidesWithTiles(newXPosition, y, width, height)) {
            needToRemove = true;
        }
        // if goes out of bounds of screen
        /*if ((x > absBulletSpawnX + (Gdx.graphics.getWidth() - relativeBulletSpawnX)) || (x < absBulletSpawnX - (relativeBulletSpawnX))) {
            needToRemove = true;
        }*/

    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(bulletTexture, x, y,12, 12);
    }


}
