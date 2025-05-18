package game;

import buildings.Building;
import buildings.TownHall;

public class PlayingArea {
    private static final int ROWS = 72;
    private static final int COLS = 128;

    private Tile[][] playingArea = new Tile[ROWS][COLS];
    private Tile[][] originalVillage;

    private BuildingManager buildingManager;

    private final TownHall townHall;

    public PlayingArea() {
        this.buildingManager = new BuildingManager();
        this.initializeTiles();

        // Place the Town Hall in the center
        int centerX = (COLS - 10) / 2;
        int centerY = (ROWS - 10) / 2;
        this.townHall = new TownHall(this, centerX, centerY);
        this.buildingManager.addBuilding(this.townHall);
        boolean placed = this.placeBuilding(this.townHall);

        if (!placed) {
            System.err.println("Failed to place Town Hall in the center of the map.");
        }
    }

    // Initialize all tiles with their coordinates
    private void initializeTiles() {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                this.playingArea[y][x] = new Tile(x, y);
            }
        }
    }

    // Get tile at position (x, y) or null if invalid
    public Tile getTile(int x, int y) {
        if (!this.isValidCoordinate(x, y)) {
            return null;
        }
        return this.playingArea[y][x];
    }

    public boolean placeBuilding(Building building) {
        int x = building.getX();
        int y = building.getY();
        int size = building.getSize();

        // Kontrola priestoru
        for (int dy = 0; dy < size; dy++) {
            for (int dx = 0; dx < size; dx++) {
                int tileX = x + dx;
                int tileY = y + dy;
                if (!this.isValidCoordinate(tileX, tileY) || !this.playingArea[tileY][tileX].isWalkable()) {
                    System.out.println("Cannot place building: space is occupied or invalid.");
                    return false;
                }
            }
        }

        // Položenie budovy
        for (int dy = 0; dy < size; dy++) {
            for (int dx = 0; dx < size; dx++) {
                this.playingArea[y + dy][x + dx].setBuilding(building);
            }
        }

        // 💡 Pridanie budovy do buildingManagera, ak tam ešte nie je
        if (!this.buildingManager.getBuildings().contains(building)) {
            this.buildingManager.addBuilding(building);
        }

        System.out.println("Building placed at " + x + "," + y);
        return true;
    }

    // Check if coordinates are inside the map boundaries
    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && y >= 0 && x < COLS && y < ROWS;
    }

    // Save the current playing area as the original village state
    public void saveVillage() {
        this.originalVillage = this.deepCopyTiles(this.playingArea);
    }

    // Reset playing area to the original saved village state
    public void resetToVillage() {
        if (this.originalVillage != null) {
            this.playingArea = this.deepCopyTiles(this.originalVillage);
        }
    }

    // Restore village state fully after wave failure
    public void restoreVillageState() {
        this.resetToVillage();

        // Clear and rebuild building list from restored tiles
        this.buildingManager.getBuildings().clear();

        for (int y = 0; y < this.playingArea.length; y++) {
            for (int x = 0; x < this.playingArea[0].length; x++) {
                Tile tile = this.playingArea[y][x];
                if (tile.hasBuilding()) {
                    Building b = tile.getBuilding();
                    if (!this.buildingManager.getBuildings().contains(b)) {
                        this.buildingManager.addBuilding(b);
                    }
                }
            }
        }

        // Clear all slimes, since wave failed
        this.buildingManager.getSlimes().clear();

        System.out.println("Village state restored after wave failure.");
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

    public BuildingManager getBuildingManager() {
        return this.buildingManager;
    }

    public Building getBuildingAt(int x, int y) {
        return this.getTile(x, y).getBuilding();
    }

    // Find nearest building to given coords
    public int[] findNearestBuilding(int fromX, int fromY) {
        int minDistance = Integer.MAX_VALUE;
        int[] nearest = null;

        for (int y = 0; y < this.playingArea.length; y++) {
            for (int x = 0; x < this.playingArea[0].length; x++) {
                Tile tile = this.playingArea[y][x];
                if (tile.hasBuilding()) {
                    int distance = Math.abs(fromX - x) + Math.abs(fromY - y);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearest = new int[]{x, y};
                    }
                }
            }
        }

        return nearest;
    }

    public void printTownHallHP() {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                Tile tile = this.playingArea[y][x];
                if (tile.hasBuilding()) {
                    Building b = tile.getBuilding();
                    if ("Town Hall".equals(b.getName())) {
                        //System.out.println("Town Hall HP: " + b.getHealth());
                        return;
                    }
                }
            }
        }
        System.out.println("Town Hall nebola nájdená.");
    }
}
