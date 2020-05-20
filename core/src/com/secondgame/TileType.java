package com.secondgame;

import java.util.HashMap;

public enum TileType {

    // change IDs later when fixed, got messed up in Tiled somehow
    /*GRASS(1, true, "grass"),
    DIRT(2, true, "dirt"),
    BLOCK_PURPLE(3, true, "blockpurple"),
    SKY_PURPLE(0, true, "skypurple");*/
    // IDs are one more than they are in tiled map... idk why
    LEVEL_1_BACKGROUND(1, false, false, "level1bg"),
    LEVEL_2_BACKGROUND(2, false, false, "level2bg"),
    LEVEL_3_BACKGROUND(3, false, false, "level3bg"),
    BLACK_BLOCK(4, true, false, "blackblock"),
    SPIKE(5, true, true, "spike"),
    PORTAL(6, true, false, "portal");

    public static final int TILE_SIZE = 32;

    private int id;
    private boolean collidable;
    private boolean kills; // kills player on contact
    private String name;
    private float damage;
    // come back later and combine tiles into one image to save space
    private TileType(int id, boolean collidable, boolean kills, String name) {
        // make tile with no damage if damage not specified
        this(id, collidable, kills, name, 0);
    }

    private TileType(int id, boolean collidable, boolean kills, String name, float damage) {
        this.id = id;
        this.collidable = collidable;
        this.kills = kills;
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

    public boolean isPortal() {
        return this.id == 6;
    }

    public boolean doesKill() {
        return kills;
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
            /*System.out.println("from TileType, processing tile: " + tileType.getId() + ", " + tileType.getName() +
                    ", is collidable: " + tileType.isCollidable());*/
            tileMap.put(tileType.getId(), tileType);
        }
    }

    // get tile via tile id
    public static TileType getTileType(int id) {
        return tileMap.get(id);
    }

    
}
