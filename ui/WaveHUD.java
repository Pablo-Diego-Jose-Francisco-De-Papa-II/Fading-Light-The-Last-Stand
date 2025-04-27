package ui;

import javax.swing.*;
import java.awt.*;

public class WaveHUD extends AbstractHUD {

    private final JButton pauseButton;
    private final JButton fastForwardButton;
    private final JButton fastFastForwardButton;
    private final JButton giveUpButton;
    private final JLabel timeLabel;

    public WaveHUD() {
        super();

        this.pauseButton = new JButton("||");
        this.pauseButton.setBounds(1070, 620, 55, 35);
        this.pauseButton.setFont(new Font("Arial", Font.BOLD, 11));
        add(this.pauseButton);

        this.fastForwardButton = new JButton(">>");
        this.fastForwardButton.setBounds(1135, 620, 55, 35);
        this.fastForwardButton.setFont(new Font("Arial", Font.BOLD, 11));
        add(this.fastForwardButton);

        this.fastFastForwardButton = new JButton(">>>");
        this.fastFastForwardButton.setBounds(1200, 620, 55, 35);
        this.fastFastForwardButton.setFont(new Font("Arial", Font.BOLD, 11));
        add(this.fastFastForwardButton);

        this.giveUpButton = new JButton("Give-Up");
        this.giveUpButton.setBounds(10, 600, 200, 75);
        this.giveUpButton.setFont(new Font("Arial", Font.BOLD, 30));
        add(this.giveUpButton);

        this.timeLabel = new JLabel("00:00");
        this.timeLabel.setBounds(575, 0, 100, 75);
        this.timeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        add(this.timeLabel);

    }

    public JButton getPauseButton() {
        return this.pauseButton;
    }

    public JButton getFastForwardButton() {
        return this.fastForwardButton;
    }

    public JButton getGiveUpButton() {
        return this.giveUpButton;
    }
}
