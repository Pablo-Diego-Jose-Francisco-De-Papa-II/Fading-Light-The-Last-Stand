package ui;

import buildings.Building;
import game.Game;
import game.PlayingArea;
import game.Shop;

import javax.swing.JButton;
import java.awt.Font;

/**
 * Trieda BuildHUD reprezentuje používateľské rozhranie počas fázy budovania v hre.
 * Umožňuje hráčovi nakupovať budovy, začať obrannú vlnu, ako aj upgradovať alebo odstrániť vybranú budovu.
 */
public class BuildHUD extends AbstractHUD {

    private final JButton shopButton;
    private final JButton startWaveButton;
    private final Shop shop;

    private final JButton upgradeButton;
    private final JButton removeButton;
    private Building selectedBuilding;

    /**
     * Konštruktor vytvorí a inicializuje HUD pre fázu budovania.
     *
     * @param playingArea herná mapa
     * @param game objekt hlavnej hry
     */
    public BuildHUD(PlayingArea playingArea, Game game) {
        super();

        // Shop button
        this.shopButton = new JButton("SHOP");
        this.shopButton.setBounds(10, 660, 150, 50);
        this.shopButton.setFont(new Font("Arial", Font.BOLD, 27));
        add(this.shopButton);

        // Start Wave button
        this.startWaveButton = new JButton("DEFEND");
        this.startWaveButton.setBounds(1120, 660, 150, 50);
        this.startWaveButton.setFont(new Font("Arial", Font.BOLD, 27));
        add(this.startWaveButton);

        this.startWaveButton.addActionListener(e -> {
            game.startWave();
            game.switchHUD("wave");
        });

        // Shop panel
        this.shop = new Shop(playingArea, 500);
        this.shop.setVisible(false);
        this.shop.setBounds(10, 400, 150, 250);
        add(this.shop);

        this.shopButton.addActionListener(e -> this.shop.setVisible(!this.shop.isVisible()));

        // Upgrade Button
        this.upgradeButton = new JButton("Upgrade");
        this.upgradeButton.setBounds(200, 600, 120, 40);
        this.upgradeButton.setVisible(false);
        add(this.upgradeButton);

        // Remove Button
        this.removeButton = new JButton("Remove");
        this.removeButton.setBounds(330, 600, 120, 40);
        this.removeButton.setVisible(false);
        add(this.removeButton);

        this.upgradeButton.addActionListener(e -> {
            if (this.selectedBuilding != null) {
                this.selectedBuilding.upgrade();
                repaint();
            }
        });

        this.removeButton.addActionListener(e -> {
            if (this.selectedBuilding != null) {
                this.selectedBuilding.removeBuidingFromMap();
                this.selectedBuilding = null;
                this.hideUpgradeRemoveButtons();
                repaint();
            }
        });
    }

    /**
     * Vracia inštanciu obchodu.
     *
     * @return Shop komponent s ponukou budov
     */
    public Shop getShop() {
        return this.shop;
    }

    /**
     * Zobrazí tlačidlá na upgrade a odstránenie pre vybranú budovu.
     *
     * @param b vybraná budova
     * @param screenX X súradnica
     * @param screenY Y súradnica
     */
    public void showUpgradeRemoveButtons(Building b, int screenX, int screenY) {
        this.selectedBuilding = b;
        this.upgradeButton.setBounds(screenX - 125, screenY + 20, 120, 40);
        this.removeButton.setBounds(screenX + 5, screenY + 20, 120, 40);
        this.upgradeButton.setVisible(true);
        this.removeButton.setVisible(true);
    }

    /**
     * Skryje tlačidlá pre úpravu budovy a zruší výber budovy.
     */
    public void hideUpgradeRemoveButtons() {
        this.upgradeButton.setVisible(false);
        this.removeButton.setVisible(false);
        this.selectedBuilding = null;
    }

}
