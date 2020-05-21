package com.secondgame.weapon;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.secondgame.map.GameMap;
import com.secondgame.resource.Hitbox;

public class Bullet {

    public static final int SPEED = 500;
    private static Texture bulletTexture;
    public boolean needToRemove = false;
    protected GameMap gameMap;
    float x;
    float y;
    String direction;
    OrthographicCamera camera;
    Vector3 bulletPos;
    float relativeBulletSpawnX;
    float relativeBulletSpawnY;
    float absBulletSpawnX;
    float absBulletSpawnY;
    int height;
    int width;
    float newXPosition;
    Hitbox hitbox;

    public Bullet(float x, float y, String direction, OrthographicCamera camera, GameMap gameMap) {

        this.x = x;
        this.y = y;
        this.height = 12;
        this.width = 12;
        this.hitbox = new Hitbox(x, y, width, height);
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

        if ("right".equals(direction)) {
            newXPosition = x + SPEED * deltaTime;
        }

        if ("left".equals(direction)) {
            newXPosition = x - SPEED * deltaTime;
        }

        // if does not collide, move to new position
        if (gameMap.checkIfCollidesWithTiles(newXPosition, y, width, height) != 1) {
            x = newXPosition;
        }

        //update hitbox
        this.hitbox.move(x, y);

        // if collides, remove it
        if (gameMap.checkIfCollidesWithTiles(newXPosition, y, width, height) == 1) {
            needToRemove = true;
        }

    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(bulletTexture, x, y, 12, 12);
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public void setNeedToRemove(boolean needToRemove) {
        this.needToRemove = needToRemove;
    }

}
