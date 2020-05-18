package com.secondgame.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.secondgame.GameMap;

public class Player extends GameObject {

    private static int speed = 180;
    private static int jumpVelocity = 6;

    Texture playerImage;


    /*public Player(float x, float y, GameMap gameMap) {
        super(x, y, GameObjectType.PLAYER, gameMap);
        playerImage = new Texture("images/player.png");
    }*/

    @Override
    public void create(GameObjectState gameObjectState, GameObjectType gameObjectType, GameMap gameMap) {
        super.create(gameObjectState, gameObjectType, gameMap);
        playerImage = new Texture("images/player.png");
        // add extra data... spawnradius etc
    }

    @Override
    public void update(float deltaTime, float gravity) {
        // going to need seperate control data structure - single jump button, etc
        // unify it basically - controller support - maybe implement customizable controls


        // hold shift to run
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            speed = 240;
        }


        // reset speed after run
        if (!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && onGround) {
            speed = 180;
        }

        //initial jump from ground
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && onGround) {
            this.velocityY += jumpVelocity * getWeight();
        }

        //continue jumping if key is still pressed in air (hence velocityY > 0)
        else if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !onGround && this.velocityY > 0) {
            this.velocityY += jumpVelocity * getWeight() * deltaTime;
        }

        // applies gravity
        super.update(deltaTime, gravity);

        //move left
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveX(-speed * deltaTime);
        }

        // move right
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveX(speed * deltaTime);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        // last two fields scale image if not correct size
        batch.draw(playerImage, position.x, position.y, getWidth(), getHeight());
    }

    @Override
    public GameObjectState getSaveGameObjectState() {
        GameObjectState gameObjectState = super.getSaveGameObjectState();
        //update extra data here if needed
        return gameObjectState;
    }
}
