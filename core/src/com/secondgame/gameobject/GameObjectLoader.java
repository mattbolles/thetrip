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
        Gdx.files.internal(id + ".json");
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
            //saveGameObjects(id, currentGameObjects);
            Gdx.app.error("GameObjectLoader", "Could not load game objects :(");
            //return currentGameObjects;
            return null;
        }

    }

    public static void saveGameObjects(String id, ArrayList<GameObject> gameObjects) {
        ArrayList<GameObjectState> gameObjectStates = new ArrayList<GameObjectState>();
        for (GameObject gameObject : gameObjects) {
            gameObjectStates.add(gameObject.getSaveGameObjectState());
        }

        Gdx.files.local("maps/").file().mkdirs();
        FileHandle file = Gdx.files.local("maps/" + id + ".gameObjects");
        file.writeString(json.prettyPrint(gameObjectStates), false);
    }
}
