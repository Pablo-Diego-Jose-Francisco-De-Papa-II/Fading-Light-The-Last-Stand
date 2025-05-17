package game;

import buildings.Building;
import buildings.TownHall;

public class PlayingArea {
    private static final int ROWS = 72;
    private static final int COLS = 128;

    private Tile[][] playingArea = new Tile[ROWS][COLS];
    private Tile[][] originalVillage;
    private Tile[][] currentWaveMap;

    private BuildingManager buildingManager;

    public PlayingArea() {
        buildingManager = new BuildingManager();
        initializeTiles();

        // Place the Town Hall in the center
        int centerX = (COLS - 6) / 2 - 3;
        int centerY = (ROWS - 6) / 2 - 3;
        TownHall townHall = new TownHall(this, centerX, centerY);
        boolean placed = placeBuilding(townHall);

        if (!placed) {
            System.err.println("Failed to place Town Hall in the center of the map.");
        }
    }


    // Initialize all tiles with their coordinates
    private void initializeTiles() {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                playingArea[y][x] = new Tile(x, y);
            }
        }
    }

    // Get tile at position (x, y) or null if invalid
    public Tile getTile(int x, int y) {
        if (!isValidCoordinate(x, y)) return null;
        return playingArea[y][x];
    }

    // Return all tiles array
    public Tile[][] getAllTiles() {
        return playingArea;
    }

    // Attempt to place a building on the map; returns true if successful
    public boolean placeBuilding(Building building) {
        int x = building.getX();
        int y = building.getY();
        int size = building.getSize();

        // Check if all tiles in the building area are walkable and valid
        for (int dy = 0; dy < size; dy++) {
            for (int dx = 0; dx < size; dx++) {
                int tileX = x + dx;
                int tileY = y + dy;
                if (!isValidCoordinate(tileX, tileY) || !playingArea[tileY][tileX].isWalkable()) {
                    System.out.println("Cannot place building: space is occupied or invalid.");
                    return false;
                }
            }
        }

        // Place the building on all tiles in the area
        for (int dy = 0; dy < size; dy++) {
            for (int dx = 0; dx < size; dx++) {
                playingArea[y + dy][x + dx].setBuilding(building);
            }
        }

        System.out.println("Building placed at " + x + "," + y);
        return true;
    }

    // Check if coordinates are inside the map boundaries
    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && y >= 0 && x < COLS && y < ROWS;
    }

    // Save the current playing area as the original village state
    public void saveVillage() {
        originalVillage = deepCopyTiles(playingArea);
    }

    // Reset playing area to the original saved village state
    public void resetToVillage() {
        if (originalVillage != null) {
            playingArea = deepCopyTiles(originalVillage);
        }
    }

    // Deep copy of tiles array
    private Tile[][] deepCopyTiles(Tile[][] source) {
        Tile[][] copy = new Tile[source.length][source[0].length];
        for (int y = 0; y < source.length; y++) {
            for (int x = 0; x < source[0].length; x++) {
                copy[y][x] = new Tile(source[y][x]);
            }
        }
        return copy;
    }

    public boolean isWithinBounds(int tileX, int tileY) {
        return isValidCoordinate(tileX, tileY);
    }

    public BuildingManager getBuildingManager() {
        return buildingManager;
    }

    // Vylepšenie budovy na daných súradniciach
    public boolean upgradeBuilding(int x, int y) {
        Tile tile = getTile(x, y);
        if (tile != null && tile.hasBuilding()) {
            Building building = tile.getBuilding();
            int upgradeCost = building.getUpgradeCost();
            if (GameState.spendScrap(upgradeCost)) {
                building.upgrade();
                return true;
            }
        }
        return false;
    }

    // Odstránenie budovy na daných súradniciach
    public boolean removeBuilding(int x, int y) {
        Tile tile = getTile(x, y);
        if (tile == null || !tile.hasBuilding()) return false;

        Building building = tile.getBuilding();
        int startX = building.getX();
        int startY = building.getY();
        int size = building.getSize();

        for (int dy = 0; dy < size; dy++) {
            for (int dx = 0; dx < size; dx++) {
                Tile t = getTile(startX + dx, startY + dy);
                if (t != null && t.getBuilding() == building) {
                    t.removeBuilding();
                }
            }
        }

        System.out.println("Removed building at: " + startX + "," + startY);
        return true;
    }

    public Building getBuildingAt(int x, int y) {
        return getTile(x, y).getBuilding();
    }



    public boolean deleteBuilding(int x, int y) {
        Building b = getBuildingAt(x, y);
        if (b == null) return false;

        getTile(x, y).removeBuilding();
        return true;
    }

}
