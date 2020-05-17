package com.secondgame;

import java.util.HashMap;

public enum TileType {

    // change IDs later when fixed, got messed up in Tiled somehow
    GRASS(1, true, "grass"),
    DIRT(2, true, "dirt"),
    BLOCK_PURPLE(3, true, "blockpurple"),
    SKY_PURPLE(0, true, "skypurple");

    public static final int TILE_SIZE = 32;

    private int id;
    private boolean collidable;
    private String name;
    private float damage;
    // come back later and combine tiles into one image to save space
    private TileType(int id, boolean collidable, String name) {
        // make tile with no damage if damage not specified
        this(id, collidable, name, 0);
    }

    private TileType(int id, boolean collidable, String name, float damage) {
        this.id = id;
        this.collidable = collidable;
        this.name = name;
        this.damage = damage;
    }

    //don't implement setters... no need to change any of this
    public int getId() {
        return id;
    }

    public boolean isCollidable() {
        return collidable;
    }
    

    public String getName() {
        return name;
    }

    public float getDamage() {
        return damage;
    }

    private static HashMap<Integer, TileType> tileMap;

    //add each tile type to hashmap
    static {
        tileMap = new HashMap<Integer, TileType>();
        for (TileType tileType : TileType.values()) {
            tileMap.put(tileType.getId(), tileType);
        }
    }

    // get tile via tile id
    public static TileType getTileType(int id) {
        return tileMap.get(id);
    }
    
}
