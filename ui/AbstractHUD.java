package ui;

import javax.swing.*;

public abstract class AbstractHUD extends JPanel {

    private final JLabel dayLabel;
    private final JLabel scrapLabel;
    private final JLabel enemiesLeftLabel;

    AbstractHUD() {
        this.dayLabel = new JLabel("Day: 1");
        dayLabel.setBounds(0, 0, 100, 50);
        add(this.dayLabel);

        this.scrapLabel = new JLabel("Scrap: 0");
        scrapLabel.setBounds(0, 0, 100, 50);
        add(this.scrapLabel);

        this.enemiesLeftLabel = new JLabel("Enemies Left: 0");
        enemiesLeftLabel.setBounds(0, 0, 100, 50);
        add(this.enemiesLeftLabel);
    }

    void updateDay(int day) {
        this.dayLabel.setText("Day: " + day);
    }

    void updateScrap(int scrap) {
        this.scrapLabel.setText("Scrap: " + scrap);
    }

    void updateEnemiesLeft(int enemiesLeft) {
        this.enemiesLeftLabel.setText("Enemies Left: " + enemiesLeft);
    }

}
