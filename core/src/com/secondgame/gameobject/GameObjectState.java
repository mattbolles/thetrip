// keeps track of game object at a point in time

package com.secondgame.gameobject;

import java.util.HashMap;

public class GameObjectState {

    // add saving states later if necessary

    public String gameObjectType;
    public float x, y;
    public HashMap<String, String> gameObjectData;

    public GameObjectState() {

    }

    public GameObjectState(String gameObjectType, float x, float y) {
        this.gameObjectType = gameObjectType;
        this.x = x;
        this.y = y;
    }

    public String getGameObjectType() {
        return gameObjectType;
    }

    public void setGameObjectType(String gameObjectType) {
        this.gameObjectType = gameObjectType;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void addFloatToHashMap(String key, float valueToAdd) {
        gameObjectData.put(key, "" + valueToAdd);
    }

    public void addIntToHashMap(String key, int valueToAdd) {
        gameObjectData.put(key, "" + valueToAdd);
    }

    public void addBooleanToHashMap(String key, boolean valueToAdd) {
        gameObjectData.put(key, "" + valueToAdd);
    }

    public void addStringToHashMap(String key, String valueToAdd) {
        gameObjectData.put(key, "" + valueToAdd);
    }

    public float getFloatFromHashMap(String key, float defaultValue) {
        if (gameObjectData.containsKey(key)) {
            try {
                return Float.parseFloat(gameObjectData.get(key));
            }
            // if wrong type of data return default value
            catch (Exception exception) {
                return defaultValue;
            }
        }
        else {
            return defaultValue;
        }
    }

    public int getIntFromHashMap(String key, int defaultValue) {
        if (gameObjectData.containsKey(key)) {
            try {
                return Integer.parseInt(gameObjectData.get(key));
            }
            // if wrong type of data return default value
            catch (Exception exception) {
                return defaultValue;
            }
        }
        else {
            return defaultValue;
        }
    }

    public boolean getBooleanFromHashMap(String key, boolean defaultValue) {
        if (gameObjectData.containsKey(key)) {
            try {
                return Boolean.parseBoolean(gameObjectData.get(key));
            }
            // if wrong type of data return default value
            catch (Exception exception) {
                return defaultValue;
            }
        }
        else {
            return defaultValue;
        }
    }

    public String getStringFromHashMap(String key, String defaultValue) {
        if (gameObjectData.containsKey(key)) {
            return gameObjectData.get(key);
        }

        else {
            return defaultValue;
        }
    }
}
