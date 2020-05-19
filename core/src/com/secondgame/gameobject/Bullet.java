package com.secondgame.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

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


    public boolean needToRemove = false;

    public Bullet(float x, float y, String direction, OrthographicCamera camera) {
        this.x = x;
        this.y = y;
        this.absBulletSpawnX = x;
        this.absBulletSpawnY = y;
        this.direction = direction;
        this.camera = camera;
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
            x += SPEED * deltaTime;
        }

        if ("left".equals(direction)) {
            x -= SPEED * deltaTime;
        }
        // if goes out of bounds of screen
        if ((x > absBulletSpawnX + (Gdx.graphics.getWidth() - relativeBulletSpawnX)) || (x < absBulletSpawnX - (Gdx.graphics.getWidth() - relativeBulletSpawnX))) {
            needToRemove = true;
        }
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(bulletTexture, x, y,12, 12);
    }


}
