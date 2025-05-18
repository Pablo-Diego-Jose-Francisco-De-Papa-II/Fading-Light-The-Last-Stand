package game;

import buildings.Building;
import slimes.Slime;

import javax.swing.JPanel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Trieda reprezentujúca panel hry, ktorý zobrazuje hraciu plochu, budovy a slimov.
 * Panel zároveň spracováva kliknutia myšou pre umiestňovanie budov a interakciu s nimi.
 */
public class GamePanel extends JPanel {
    private final PlayingArea playingArea;
    private final Game game;
    private static final int TILE_SIZE = 10;
    private static final int WIDTH = 128;
    private static final int HEIGHT = 72;

    /**
     * Vytvorí nový herný panel so zadanou hernou plochou a hrou.
     *
     * @param playingArea herná plocha s times a budovami
     * @param game        inštancia hry pre prístup k ďalším komponentom
     */
    public GamePanel(PlayingArea playingArea, Game game) {
        this.playingArea = playingArea;
        this.game = game;
        setPreferredSize(new Dimension(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int tileX = e.getX() / TILE_SIZE;
                int tileY = e.getY() / TILE_SIZE;

                // Kontrola, či klik nie je mimo hernej plochy
                if (tileX < 0 || tileX >= WIDTH || tileY < 0 || tileY >= HEIGHT) {
                    return;
                }

                Tile tile = playingArea.getTile(tileX, tileY);
                if (tile == null) {
                    return;
                }

                Building clickedBuilding = tile.getBuilding();
                if (clickedBuilding != null) {
                    // Ak ide o budovu, ktorá nie je Town Hall, zobraz možnosti upgradu/odstránenia
                    if (!"Town Hall".equals(clickedBuilding.getName())) {
                        game.getBuildHUD().showUpgradeRemoveButtons(clickedBuilding, e.getX(), e.getY());
                    } else {
                        game.getBuildHUD().hideUpgradeRemoveButtons();
                    }
                    return;
                }

                // Ak nie je na dlaždici budova, over vybranú budovu v obchode a pokus sa ju postaviť
                Shop shop = game.getBuildHUD().getShop();
                if (shop == null) {
                    return;
                }

                String selected = shop.getSelectedBuilding();

                if (selected != null) {
                    int cost = shop.getCostForBuilding(selected);

                    if (shop.getScrap() < cost) {
                        JOptionPane.showMessageDialog(GamePanel.this, "Not enough scrap.");
                        return;
                    }

                    Building building = Building.createBuilding(selected, playingArea, tileX, tileY);
                    if (building != null && playingArea.placeBuilding(building)) {
                        shop.subtractScrap(cost);
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

    /**
     * Vykreslí komponent vrátane mriežky, budov a nepriateľov.
     *
     * @param g grafický kontext na kreslenie
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawGrid(g);
        this.drawBuildings(g);
        this.drawSlimes(g);
    }

    /**
     * Vykreslí mriežku dlaždíc na hernej ploche.
     *
     * @param g grafický kontext na kreslenie
     */
    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                g.drawRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    /**
     * Vykreslí všetky budovy na hernej ploche.
     *
     * @param g grafický kontext na kreslenie
     */
    private void drawBuildings(Graphics g) {
        java.util.HashSet<Building> drawn = new java.util.HashSet<>();

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = this.playingArea.getTile(x, y);
                if (tile == null) {
                    continue;
                }

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

    /**
     * Vykreslí všetky nepriateľské slime na hernej ploche.
     *
     * @param g grafický kontext na kreslenie
     */
    private void drawSlimes(Graphics g) {
        java.util.List<Slime> slimes = this.game.getWaveManager().getSlimes();
        //this.playingArea.printTownHallHP();

        for (Slime slime : slimes) {
            slime.draw(g, TILE_SIZE);
        }
    }

}
