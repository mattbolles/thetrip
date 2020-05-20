package com.secondgame;

import com.badlogic.gdx.math.Vector2;
import com.secondgame.gameobject.GameObject;

public class Hitbox {
    float x;
    float y;
    int width;
    int height;

    public Hitbox(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean checkCollision(Hitbox hitboxToCheck) {
        if ((x < hitboxToCheck.x + hitboxToCheck.width) && (y < hitboxToCheck.y + hitboxToCheck.height) && (x + width > hitboxToCheck.x) && (y + height > hitboxToCheck.y)) {
            return true;
        }

        else {
            return false;
        }
    }
}
