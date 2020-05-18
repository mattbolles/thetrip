package com.secondgame.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.secondgame.GameInfo;
import com.secondgame.GameMap;

public class Player extends GameObject {

    private static int speed = 180;
    private static int jumpVelocity = 6;

    Texture playerImage;
    Animation<TextureRegion> rightAnimation;
    Animation<TextureRegion> leftAnimation;
    Texture moveAnimationSheet;
    public static final float ANIMATION_SPEED = 0.5f; // every half a second
    int movePosition;
    public static final int FRAME_COLS = 4;
    public static final int FRAME_ROWS = 2;
    float stateTime; // for tracking animation time
    String direction; // for keeping track of direction player is facing

    /*public Player(float x, float y, GameMap gameMap) {
        super(x, y, GameObjectType.PLAYER, gameMap);
        playerImage = new Texture("images/player.png");
    }*/

    @Override
    public void create(GameObjectState gameObjectState, GameObjectType gameObjectType, GameMap gameMap) {
        super.create(gameObjectState, gameObjectType, gameMap);
        direction = "right";
        playerImage = new Texture("images/player.png");
        moveAnimationSheet = new Texture(Gdx.files.internal("images/playerspritesheet.png"));
        System.out.println("moveAnimationSheet width: " + moveAnimationSheet.getWidth() + " height: " + moveAnimationSheet.getHeight());
        TextureRegion[][] tmp = TextureRegion.split(moveAnimationSheet,moveAnimationSheet.getWidth() / FRAME_COLS,
                        moveAnimationSheet.getHeight() / FRAME_ROWS); // 4 col, 2 row
        TextureRegion[] moveRightFrames = new TextureRegion[FRAME_COLS]; // top row
        TextureRegion[] moveLeftFrames = new TextureRegion[FRAME_COLS]; // bottom row
        for (int i = 0; i < FRAME_COLS; i++) {
            moveRightFrames[i] = tmp[0][i];
            moveLeftFrames[i] = tmp[1][i];
        }

        rightAnimation = new Animation<TextureRegion>(GameInfo.ANIMATION_SPEED, moveRightFrames);
        leftAnimation = new Animation<TextureRegion>(GameInfo.ANIMATION_SPEED, moveLeftFrames);

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
            direction = "left";
        }

        // move right
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveX(speed * deltaTime);
            direction = "right";
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime(); // accumulate time
        TextureRegion currentFrame = leftAnimation.getKeyFrame(stateTime, true);
        if ("left".equals(direction)) {
            currentFrame = leftAnimation.getKeyFrame(stateTime, true);
        }

        else if ("right".equals(direction)) {
            currentFrame = rightAnimation.getKeyFrame(stateTime, true);
        }
        // last two fields scale image if not correct size
        batch.draw(currentFrame, position.x, position.y, getWidth(), getHeight());
    }

    @Override
    public GameObjectState getSaveGameObjectState() {
        GameObjectState gameObjectState = super.getSaveGameObjectState();
        //update extra data here if needed
        return gameObjectState;
    }
}
