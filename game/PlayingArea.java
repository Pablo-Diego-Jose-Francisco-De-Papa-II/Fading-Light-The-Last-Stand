package game;

import buildings.Building;

public class PlayingArea {
    private Tile[][] playingArea = new Tile[128][72];

    private Tile[][] originalVillage;
    private Tile[][] currentWaveMap;


    public PlayingArea() {
        for (int y = 0; y < 128; y++) {
            for (int x = 0; x < 72; x++) {
                this.playingArea[y][x] = new Tile(x, y);
            }
        }
    }

    public Tile getTile(int x, int y) {
        return this.playingArea[x][y];
    }

    public Tile[][] getAllTiles() {
        return this.playingArea;
    }

    public boolean placeBuilding(Building building) {
        int x = building.getX();
        int y = building.getY();
        int size = building.getSize();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!isValidCoordinate(x + i, y + j) || !playingArea[x + i][y + j].isWalkable()) {
                    System.out.println("Cannot place building: space is occupied or invalid.");
                    return false;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                playingArea[x + i][y + j].setBuilding(building);
            }
        }

        System.out.println("Building placed at " + x + "," + y);
        return true;
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && y >= 0 && x < 128 && y < 72;
    }

    public void saveVillage() {
        originalVillage = deepCopyTiles(playingArea);
    }

    public void resetToVillage() {
        playingArea = deepCopyTiles(originalVillage);
    }

    private Tile[][] deepCopyTiles(Tile[][] source) {
        Tile[][] copy = new Tile[source.length][source[0].length];
        for (int y = 0; y < source.length; y++) {
            for (int x = 0; x < source[0].length; x++) {
                copy[y][x] = new Tile(source[y][x]);
            }
        }
        return copy;
    }

}