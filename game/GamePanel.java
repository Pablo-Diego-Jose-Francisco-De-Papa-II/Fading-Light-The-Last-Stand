package game;

import buildings.Building;
import buildings.TownHall;
import slimes.Slime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
    private final PlayingArea playingArea;
    private final Game game;
    private static final int TILE_SIZE = 10;
    private static final int WIDTH = 128;
    private static final int HEIGHT = 72;

    public GamePanel(PlayingArea playingArea, Game game) {
        this.playingArea = playingArea;
        this.game = game;
        setPreferredSize(new Dimension(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int tileX = e.getX() / TILE_SIZE;
                int tileY = e.getY() / TILE_SIZE;

                // Kontrola hraníc
                if (tileX < 0 || tileX >= WIDTH || tileY < 0 || tileY >= HEIGHT) {
                    return; // Klik mimo hracej plochy
                }

                Tile tile = playingArea.getTile(tileX, tileY);
                if (tile == null) return;

                Building clickedBuilding = tile.getBuilding();
                if (clickedBuilding != null) {
                    if (!"Town Hall".equals(clickedBuilding.getName())) {
                        game.getBuildHUD().showUpgradeRemoveButtons(clickedBuilding, e.getX(), e.getY());
                    } else {
                        game.getBuildHUD().hideUpgradeRemoveButtons();
                    }
                    return;
                }

                Shop shop = game.getBuildHUD().getShop();
                if (shop == null) return;

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
                        game.getBuildHUD().hideUpgradeRemoveButtons();
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
        drawZombies(g);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                g.drawRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    private void drawBuildings(Graphics g) {
        java.util.HashSet<Building> drawn = new java.util.HashSet<>();

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = playingArea.getTile(x, y);
                if (tile == null) continue;

                Building building = tile.getBuilding();
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

    private void drawZombies(Graphics g) {
        java.util.List<Slime> zombies = game.getWaveManager().getZombies();
        playingArea.printTownHallHP();

        for (Slime slime : zombies) {
            slime.draw(g, TILE_SIZE);
        }
    }

}
