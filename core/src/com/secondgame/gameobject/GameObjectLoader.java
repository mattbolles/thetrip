package com.secondgame.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.secondgame.GameMap;

import java.util.ArrayList;

public class GameObjectLoader {

    private static Json json = new Json();

    public static ArrayList<GameObject> loadGameObjectsIntoMap(String id, GameMap gameMap) {
        Gdx.files.internal(id + "json");
        FileHandle file = Gdx.files.internal(id + ".json");

        if (file.exists()) {
            GameObjectState[] gameObjectStates = json.fromJson(GameObjectState[].class, file.readString());
            ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
            for (GameObjectState gameObjectState : gameObjectStates) {
                gameObjects.add(GameObjectType.createObjectFromState(gameObjectState, gameMap));

            }
            return gameObjects;
        }

        else {
            Gdx.app.error("GameObjectLoader", "Could not load game objects :(");
            return null;
        }

    }
}
