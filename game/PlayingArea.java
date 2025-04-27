package game;

public class PlayingArea {
    Tile[][] playingArea = new Tile[128][72];

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
}