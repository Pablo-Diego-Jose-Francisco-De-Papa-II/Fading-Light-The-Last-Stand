package game;

import buildings.Building;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
    private final PlayingArea playingArea;
    private final Game game; // added reference to Game
    private static final int TILE_SIZE = 10;

    public GamePanel(PlayingArea playingArea, Game game) {
        this.playingArea = playingArea;
        this.game = game;
        setPreferredSize(new Dimension(128 * TILE_SIZE, 72 * TILE_SIZE));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int tileX = e.getX() / TILE_SIZE;
                int tileY = e.getY() / TILE_SIZE;

                Shop shop = game.getBuildHUD().getShop();
                String selected = shop.getSelectedBuilding();

                if (selected != null) {
                    int cost = shop.getCostForBuilding(selected);

                    if (shop.getMoney() < cost) {
                        JOptionPane.showMessageDialog(GamePanel.this, "Not enough scrap.");
                        return;
                    }

                    Building building = Building.createBuilding(selected, playingArea, tileX, tileY);
                    if (building != null && playingArea.placeBuilding(building)) {
                        shop.subtractMoney(cost);
                        shop.clearSelectedBuilding();
                        repaint();
                    } else {
                        JOptionPane.showMessageDialog(GamePanel.this, "Cannot place " + selected + " here.");
                    }
                }
            }
        });
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
                        g.drawImage(img,
                                building.getX() * TILE_SIZE,
                                building.getY() * TILE_SIZE,
                                building.getSize() * TILE_SIZE,
                                building.getSize() * TILE_SIZE,
                                null);
                    } else {
                        g.setColor(Color.RED);
                        g.fillRect(building.getX() * TILE_SIZE, building.getY() * TILE_SIZE,
                                building.getSize() * TILE_SIZE, building.getSize() * TILE_SIZE);
                    }
                }
            }
        }
    }
}
