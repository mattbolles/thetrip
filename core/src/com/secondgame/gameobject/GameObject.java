package com.secondgame.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.secondgame.map.GameMap;
import com.secondgame.map.TileType;

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
        if (gameMap.checkIfCollidesWithTiles(position.x, newYPosition, getWidth(), getHeight()) == 1) {
            // if hit ground
            if (velocityY < 0) {
                // put to nearest bottom of floor
                this.position.y = (float) Math.floor(position.y);
                onGround = true;
            }
            // reset velocity
            this.velocityY = 0;

            // check if damages player
            if ((gameMap.getTileTypeByLocation(1, position.x, newYPosition)) != null) {
                TileType collidingTileType = gameMap.getTileTypeByLocation(1, position.x, newYPosition);
                if (collidingTileType.isPortal()) {
                    if (this instanceof  Player) {
                        ((Player) this).setLevelBeat(true);
                    }
                }
                if (collidingTileType.doesKill()) {
                    //System.out.println("from GameObject: colliding with deadly tile of type " + collidingTileType);
                    if (this instanceof Player) {
                        ((Player) this).damagePlayer(20);
                    }
                }
            }


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
        if (gameMap.checkIfCollidesWithTiles(newXPosition, position.y, getWidth(), getWidth()) != 1) {
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

    public void setX(float xToSet) {
        this.position.x = xToSet;
    }

    public float getY() {
        return position.y;
    }

    public void setY(float yToSet) {
        this.position.y = yToSet;
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



  /*  public abstract boolean isCompletelyDead();

    public abstract boolean killEnemy();*/
}
