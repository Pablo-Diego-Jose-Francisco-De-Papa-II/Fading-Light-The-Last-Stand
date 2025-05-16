package game;

import buildings.Building;
import buildings.WatchTower;

import javax.swing.*;
import java.awt.*;

public class PlayingAreaPanel extends JPanel {

    private static final int TILE_SIZE = 10; // Adjust to match your graphic tile size
    private final PlayingArea map;

    public PlayingAreaPanel(PlayingArea map) {
        this.map = map;
        setPreferredSize(new Dimension(128 * TILE_SIZE, 72 * TILE_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw tiles (optional - for grid debug)
        for (int y = 0; y < 72; y++) {
            for (int x = 0; x < 128; x++) {
                g.setColor(Color.DARK_GRAY);
                g.drawRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        // Draw buildings
        for (int y = 0; y < 128; y++) {
            for (int x = 0; x < 72; x++) {
                Building b = map.getTile(y, x).getBuilding();
                if (b instanceof WatchTower wt && wt.getImage() != null) {
                    g.drawImage(wt.getImage(),
                            wt.getX() * TILE_SIZE,
                            wt.getY() * TILE_SIZE,
                            wt.getSize() * TILE_SIZE,
                            wt.getSize() * TILE_SIZE,
                            null);
                }
            }
        }
    }
}

