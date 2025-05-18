package game;

import buildings.Building;
import buildings.TownHall;

/**
 * Trieda PlayingArea reprezentuje herné pole so štvorcovými dlaždicami a umožňuje
 * umiestňovanie budov, prácu s manažérom budov a obnovovanie pôvodného stavu dediny.
 */
public class PlayingArea {
    private static final int ROWS = 72;
    private static final int COLS = 128;

    private Tile[][] playingArea = new Tile[ROWS][COLS];
    private Tile[][] originalVillage;

    private BuildingManager buildingManager;

    private final TownHall townHall;

    /**
     * Vytvorí novú hraciu plochu, inicializuje dlaždice a umiestni radnicu do stredu mapy.
     */
    public PlayingArea() {
        this.buildingManager = new BuildingManager();
        this.initializeTiles();

        // Umiestnenie Town hall na stred
        int centerX = (COLS - 10) / 2;
        int centerY = (ROWS - 10) / 2;
        this.townHall = new TownHall(this, centerX, centerY);
        this.buildingManager.addBuilding(this.townHall);
        boolean placed = this.placeBuilding(this.townHall);

        if (!placed) {
            System.err.println("Failed to place Town Hall in the center of the map.");
        }
    }

    /**
     * Inicializuje každú dlaždicu v hracej ploche s jej súradnicami.
     */
    private void initializeTiles() {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                this.playingArea[y][x] = new Tile(x, y);
            }
        }
    }

    /**
     * Vráti Tile na pozícii (x, y).
     *
     * @param x súradnica x
     * @param y súradnica y
     * @return dlaždica na zadaných súradniciach alebo {@code null}
     */
    public Tile getTile(int x, int y) {
        if (!this.isValidCoordinate(x, y)) {
            return null;
        }
        return this.playingArea[y][x];
    }

    /**
     * Pokúsi sa umiestniť budovu na hernú plochu.
     *
     * @param building budova, ktorú sa pokúšame umiestniť
     * @return true, ak sa budovu podarilo umiestniť, inak false
     */
    public boolean placeBuilding(Building building) {
        int x = building.getX();
        int y = building.getY();
        int size = building.getSize();

        // Overenie voľného priestoru
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

        // Nastavenie budovy na pozíciu
        for (int dy = 0; dy < size; dy++) {
            for (int dx = 0; dx < size; dx++) {
                this.playingArea[y + dy][x + dx].setBuilding(building);
            }
        }

        // Pridanie budovy do manažéra budov, ak tam ešte nie je
        if (!this.buildingManager.getBuildings().contains(building)) {
            this.buildingManager.addBuilding(building);
        }

        System.out.println("Building placed at " + x + "," + y);
        return true;
    }

    /**
     * Overí, či sú dané súradnice v rámci rozmerov hracej plochy.
     *
     * @param x súradnica X
     * @param y súradnica Y
     * @return true, ak sú súradnice platné, inak false
     */
    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && y >= 0 && x < COLS && y < ROWS;
    }

    /**
     * Uloží aktuálny stav dediny do poľa originalVillage.
     */
    public void saveVillage() {
        this.originalVillage = this.deepCopyTiles(this.playingArea);
    }

    /**
     * Obnoví hernú plochu do uloženého stavu dediny.
     */
    public void resetToVillage() {
        if (this.originalVillage != null) {
            this.playingArea = this.deepCopyTiles(this.originalVillage);
        }
    }

    /**
     * Obnoví celý stav dediny (dlaždice, budovy a slizy) po neúspešnej vlne.
     */
    public void restoreVillageState() {
        this.resetToVillage();

        // Obnoví zoznam budov podľa aktuálnych dlaždíc
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

        // Vymaže všetkých slimov
        this.buildingManager.getSlimes().clear();

        System.out.println("Village state restored after wave failure.");
    }

    /**
     * Vytvorí hlbokú kópiu poľa dlaždíc.
     *
     * @param source zdrojové pole
     * @return nová hlboká kópia poľa
     */
    private Tile[][] deepCopyTiles(Tile[][] source) {
        Tile[][] copy = new Tile[source.length][source[0].length];
        for (int y = 0; y < source.length; y++) {
            for (int x = 0; x < source[0].length; x++) {
                copy[y][x] = new Tile(source[y][x]);
            }
        }
        return copy;
    }

    /**
     * Vráti manažéra budov.
     *
     * @return inštancia BuildingManager
     */
    public BuildingManager getBuildingManager() {
        return this.buildingManager;
    }

    /**
     * Nájde najbližšiu budovu k zadaným súradniciam.
     *
     * @param fromX súradnica X
     * @param fromY súradnica Y
     * @return pole s pozíciou [x, y]
     */
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

    /**
     * Vypíše životy radnice do konzoly (ak sa nájde).
     */
    public void printTownHallHP() {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                Tile tile = this.playingArea[y][x];
                if (tile.hasBuilding()) {
                    Building b = tile.getBuilding();
                    if ("Town Hall".equals(b.getName())) {
                        System.out.println("Town Hall HP: " + b.getHealth());
                        return;
                    }
                }
            }
        }
        System.out.println("Town Hall nebola nájdená.");
    }

}
