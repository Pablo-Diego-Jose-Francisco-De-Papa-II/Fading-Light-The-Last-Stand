package ui;

import game.Game;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Font;

/**
 * Trieda WaveHUD reprezentuje herné HUD počas vĺn nepriateľov.
 * Obsahuje tlačidlá na ovládanie hry a zobrazovanie informácií
 * ako čas a počet zostávajúcich nepriateľov.
 */
public class WaveHUD extends AbstractHUD {

    private final JButton normalSpeedButton;
    private final JButton fastForwardButton;
    private final JButton fastFastForwardButton;
    private final JButton giveUpButton;
    private final JLabel timeLabel;
    private final JLabel enemiesLeftLabel;

    /**
     * Konštruktor vytvára WaveHUD a inicializuje všetky komponenty.
     * Nastavuje pozície, štýly a pridáva akcie buttons.
     *
     * @param game referencia na hernú logiku pre ovládanie rýchlosti a prepínanie HUD
     */
    public WaveHUD(Game game) {
        super();

        this.enemiesLeftLabel = new JLabel("Points left: 0");
        this.enemiesLeftLabel.setBounds(90, -10, 250, 90);
        this.enemiesLeftLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(this.enemiesLeftLabel);

        JLabel enemiesLeftLabel1 = new JLabel(new ImageIcon("resources/slime_bg.png"));
        enemiesLeftLabel1.setBounds(10, -10, 250, 90);
        add(enemiesLeftLabel1);

        // Časovač
        this.timeLabel = new JLabel("00:00");
        this.timeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        this.timeLabel.setBounds(590, 10, 100, 50);
        add(this.timeLabel);

        // Normálna rýchlosť
        this.normalSpeedButton = new JButton(">");
        this.normalSpeedButton.setBounds(1070, 660, 55, 50);
        this.normalSpeedButton.setFont(new Font("Arial", Font.BOLD, 11));
        add(this.normalSpeedButton);

        // Rýchlejšie
        this.fastForwardButton = new JButton(">>");
        this.fastForwardButton.setBounds(1135, 660, 55, 50);
        this.fastForwardButton.setFont(new Font("Arial", Font.BOLD, 11));
        add(this.fastForwardButton);

        // Najrýchlejšie
        this.fastFastForwardButton = new JButton(">>>");
        this.fastFastForwardButton.setBounds(1200, 660, 55, 50);
        this.fastFastForwardButton.setFont(new Font("Arial", Font.BOLD, 11));
        add(this.fastFastForwardButton);

        this.normalSpeedButton.addActionListener(e -> game.setGameLoopDelay(500));
        this.fastForwardButton.addActionListener(e -> game.setGameLoopDelay(200));
        this.fastFastForwardButton.addActionListener(e -> game.setGameLoopDelay(50));

        // Give up button
        this.giveUpButton = new JButton("GIVE UP");
        this.giveUpButton.setBounds(10, 660, 150, 50);
        this.giveUpButton.setFont(new Font("Arial", Font.BOLD, 27));
        add(this.giveUpButton);

        this.giveUpButton.addActionListener(e -> {
            game.switchHUD("build");
        });
    }

    /**
     * Aktualizuje zobrazovaný čas v HUD.
     *
     * @param time reťazec predstavujúci aktuálny čas
     */
    public void updateTime(String time) {
        this.timeLabel.setText(time);
    }

    /**
     * Aktualizuje počet zostávajúcich nepriateľov (bodov).
     *
     * @param count počet zostávajúcich nepriateľov
     */
    public void updateEnemiesLeft(int count) {
        this.enemiesLeftLabel.setText("Points left: " + count);
    }

}
