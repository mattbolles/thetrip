package com.secondgame.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.secondgame.GameMap;

import java.util.HashMap;

public abstract class GameObject {
    protected Vector2 position;
    protected GameObjectType gameObjectType;
    protected float velocityY; // pos = up, neg = down
    protected GameMap gameMap;
    protected boolean onGround = false;

    /*public GameObject(float x, float y, GameObjectType gameObjectType, GameMap gameMap) {
        this.position = new Vector2(x, y);
        this.gameObjectType = gameObjectType;
        this.gameMap = gameMap;
    }*/

    public void create(GameObjectState gameObjectState, GameObjectType gameObjectType, GameMap gameMap) {
        this.position = new Vector2(gameObjectState.getX(), gameObjectState.getY());
        this.gameObjectType = gameObjectType;
        this.gameMap = gameMap;
    }

    public void update(float deltaTime, float gravity) {
        //float newYPosition = position.y;

        // gravity affects object by updatetime and weight
        this.velocityY += gravity * deltaTime * getWeight();
        float newYPosition = position.y + this.velocityY * deltaTime;

        // if collision happens with map
        if (gameMap.checkIfCollides(position.x, newYPosition, getWidth(), getHeight())) {
            // if hit ground
            if (velocityY < 0) {
                // put to nearest bottom of floor
                this.position.y = (float) Math.floor(position.y);
                onGround = true;
            }
            // reset velocity
            this.velocityY = 0;
        }

        // if no collision with map
        else {
            this.position.y = newYPosition;
            onGround = false;
        }
    }

    public abstract void render(SpriteBatch batch);

    // check collision for each object
    protected void moveX(float amountToMove) {
        float newXPosition = this.position.x + amountToMove;
        // only move if no collision
        if (!gameMap.checkIfCollides(newXPosition, position.y, getWidth(), getWidth())) {
            this.position.x = newXPosition;
        }
    }

    //save states of object
    public GameObjectState getSaveGameObjectState() {
        return new GameObjectState(gameObjectType.getId(), position.x, position.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public GameObjectType getGameObjectType() {
        return gameObjectType;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public int getWidth() {
        return gameObjectType.getWidth();
    }

    public int getHeight() {
        return gameObjectType.getHeight();
    }

    public float getWeight() {
        return gameObjectType.getWeight();
    }

    public boolean isPlayer() {
        //System.out.println("from isPlayer():" + this.gameObjectType.equals(GameObjectType.PLAYER));
        return this.gameObjectType.equals(GameObjectType.PLAYER);
    }
}
