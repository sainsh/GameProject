package Common;

public class Config {   //sets size of map and size of tiles

    private final int tileSize = 64;
    private int mapWidth = 15;
    private int mapHeight = 10;

    public int getTileSize() {
        return tileSize;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }
}
