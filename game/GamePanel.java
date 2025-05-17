package game;

import buildings.Building;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
    private final PlayingArea playingArea;
    private final Game game;
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

                Building clickedBuilding = playingArea.getTile(tileX, tileY).getBuilding();
                if (clickedBuilding != null) {
                    // Ak to nie je Town Hall, zobraz upgrade/remove tlačidlá
                    if (!"Town Hall".equals(clickedBuilding.getName())) {
                        game.getBuildHUD().showUpgradeRemoveButtons(clickedBuilding, e.getX(), e.getY());
                    } else {
                        // Ak je to Town Hall, skry tlačidlá (alebo nič)
                        game.getBuildHUD().hideUpgradeRemoveButtons();
                    }
                    return;
                }

                // ... zvyšok kódu bez zmeny ...
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
                        game.getBuildHUD().hideUpgradeRemoveButtons(); // skry staré tlačidlá
                        repaint();
                    } else {
                        JOptionPane.showMessageDialog(GamePanel.this, "Cannot place " + selected + " here.");
                    }
                } else {
                    game.getBuildHUD().hideUpgradeRemoveButtons();
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
        // Aby sme nevykresľovali tú istú budovu viackrát
        java.util.HashSet<Building> drawn = new java.util.HashSet<>();

        for (int y = 0; y < 72; y++) {
            for (int x = 0; x < 128; x++) {
                Building building = playingArea.getTile(x, y).getBuilding();
                if (building != null && !drawn.contains(building)) {
                    BufferedImage img = building.getImage();
                    int drawX = building.getX() * TILE_SIZE;
                    int drawY = building.getY() * TILE_SIZE;
                    int size = building.getSize() * TILE_SIZE;

                    if (img != null) {
                        g.drawImage(img, drawX, drawY, size, size, null);
                    } else {
                        g.setColor(Color.RED);
                        g.fillRect(drawX, drawY, size, size);
                    }

                    drawn.add(building);
                }
            }
        }
    }

}
