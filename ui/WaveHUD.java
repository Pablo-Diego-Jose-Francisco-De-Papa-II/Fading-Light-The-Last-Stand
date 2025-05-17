package ui;

import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WaveHUD extends AbstractHUD {

    private final JButton pauseButton;
    private final JButton fastForwardButton;
    private final JButton fastFastForwardButton;
    private final JButton giveUpButton;
    private final JLabel timeLabel;
    private final JLabel enemiesLeftLabel;

    public WaveHUD(Game game) {
        super();

        ImageIcon enemiesIcon = new ImageIcon("resources/slime_bg.png");

        this.enemiesLeftLabel = new JLabel("Points left: 0");
        this.enemiesLeftLabel.setBounds(90, -10, 250, 90);
        this.enemiesLeftLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(this.enemiesLeftLabel);

        JLabel enemiesLeftLabel = new JLabel(new ImageIcon("resources/slime_bg.png"));
        enemiesLeftLabel.setBounds(10, -10, 250, 90);
        add(enemiesLeftLabel);

        // Time label centered at top middle
        this.timeLabel = new JLabel("00:00");
        this.timeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        // center at top: x = (frameWidth/2 - labelWidth/2)
        this.timeLabel.setBounds(590, 10, 100, 50);
        add(this.timeLabel);

        // Pause button bottom right-ish
        this.pauseButton = new JButton(">");
        this.pauseButton.setBounds(1070, 660, 55, 50);
        this.pauseButton.setFont(new Font("Arial", Font.BOLD, 11));
        add(this.pauseButton);

        // Fast forward buttons next to pause
        this.fastForwardButton = new JButton(">>");
        this.fastForwardButton.setBounds(1135, 660, 55, 50);
        this.fastForwardButton.setFont(new Font("Arial", Font.BOLD, 11));
        add(this.fastForwardButton);

        this.fastFastForwardButton = new JButton(">>>");
        this.fastFastForwardButton.setBounds(1200, 660, 55, 50);
        this.fastFastForwardButton.setFont(new Font("Arial", Font.BOLD, 11));
        add(this.fastFastForwardButton);

        // Give up button bottom left
        this.giveUpButton = new JButton("GIVE UP");
        this.giveUpButton.setBounds(10, 660, 150, 50);
        this.giveUpButton.setFont(new Font("Arial", Font.BOLD, 27));
        add(this.giveUpButton);

        giveUpButton.addActionListener(e -> {
            game.switchHUD("build");
        });
    }

    // Method to update time
    public void updateTime(String time) {
        this.timeLabel.setText(time);
    }

    public void updateEnemiesLeft(int count) {
        this.enemiesLeftLabel.setText("Points left: " + count);
    }

    // Getters for buttons to allow adding listeners externally
    public JButton getPauseButton() {
        return pauseButton;
    }

    public JButton getFastForwardButton() {
        return fastForwardButton;
    }

    public JButton getFastFastForwardButton() {
        return fastFastForwardButton;
    }

    public JButton getGiveUpButton() {
        return giveUpButton;
    }

    // Example method to add action listener for give up button (to switch HUD)
    public void addGiveUpListener(ActionListener listener) {
        giveUpButton.addActionListener(listener);
    }
}
