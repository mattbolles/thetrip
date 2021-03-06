package com.secondgame.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.secondgame.map.GameMap;

import java.util.HashMap;

@SuppressWarnings("rawtypes")
public enum GameObjectType {

    PLAYER("player", Player.class, 30, 64, 40),
    ENEMY("enemy", Enemy.class, 64, 54, 100),
    BOSS("boss", Boss.class, 120, 256, 1000);


    private String id;
    private Class loaderClass;
    private int width;
    private int height;
    private float weight;

    private GameObjectType(String id, Class loaderClass, int width, int height, float weight) {
        this.id = id;
        this.loaderClass = loaderClass;
        this.width = width;
        this.height = height;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    // load object from state
    public static GameObject createObjectFromState(GameObjectState gameObjectState, GameMap gameMap) {
        GameObjectType gameObjectType = gameObjectTypes.get(gameObjectState.gameObjectType);
        try {
            @SuppressWarnings("unchecked")
            GameObject newGameObject = (GameObject) ClassReflection.newInstance(gameObjectType.loaderClass);
            newGameObject.create(gameObjectState, gameObjectType, gameMap);
            return newGameObject;
        }

        catch (ReflectionException exception) {
            Gdx.app.error("TheTrip Object Loader", "Could not load game object of type " + gameObjectType.id);
            return null;
        }
    }

    private static HashMap<String, GameObjectType> gameObjectTypes;

    static {
        gameObjectTypes = new HashMap<String, GameObjectType>();
        for (GameObjectType gameObjectType : GameObjectType.values()) {
            gameObjectTypes.put(gameObjectType.id, gameObjectType);
        }
    }
}
