package com.secondgame.gameobject;

public enum GameObjectType {
    // 30 so can fit through cracks maybe? - change later if doesn't work right
    PLAYER("player", 30, 64, 40);

    private String id;
    private int width;
    private int height;
    private float weight;

    private GameObjectType(String id, int width, int height, float weight) {
        this.id = id;
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
}
