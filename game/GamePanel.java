package game;

import buildings.Building;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
    private final PlayingArea playingArea;
    private static final int TILE_SIZE = 10;

    public GamePanel(PlayingArea playingArea) {
        this.playingArea = playingArea;
        setPreferredSize(new Dimension(128 * TILE_SIZE, 72 * TILE_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawBuildings(g);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 72; y++) {
                g.drawRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    private void drawBuildings(Graphics g) {
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 72; y++) {
                Building building = playingArea.getTile(x, y).getBuilding();
                if (building != null) {
                    BufferedImage img = building.getImage();
                    if (img != null) {
                        // Draw building image scaled to tile size * building size
                        g.drawImage(img,
                                building.getX() * TILE_SIZE,
                                building.getY() * TILE_SIZE,
                                building.getSize() * TILE_SIZE,
                                building.getSize() * TILE_SIZE,
                                null);
                    } else {
                        // fallback if no image - draw a colored square
                        g.setColor(Color.RED);
                        g.fillRect(building.getX() * TILE_SIZE, building.getY() * TILE_SIZE,
                                building.getSize() * TILE_SIZE, building.getSize() * TILE_SIZE);
                    }
                }
            }
        }
    }
}
